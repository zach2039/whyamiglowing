package com.zach2039.whyamiglowing.core;

import com.google.common.collect.ImmutableList;
import com.zach2039.whyamiglowing.WhyAmIGlowing;
import com.zach2039.whyamiglowing.api.capability.chunkradiation.IChunkRadiation;
import com.zach2039.whyamiglowing.api.capability.radiation.IRadiation;
import com.zach2039.whyamiglowing.api.capability.radiationsource.IRadiationSource;
import com.zach2039.whyamiglowing.capability.chunkradiation.ChunkRadiationCapability;
import com.zach2039.whyamiglowing.capability.radiation.RadiationCapability;
import com.zach2039.whyamiglowing.capability.radiationsource.RadiationSourceCapability;
import com.zach2039.whyamiglowing.init.ModItems;
import com.zach2039.whyamiglowing.init.ModMobEffects;
import com.zach2039.whyamiglowing.init.ModSoundEvents;
import com.zach2039.whyamiglowing.util.CapabilityNotPresentException;
import com.zach2039.whyamiglowing.util.ChunkUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.LazyOptional;

import java.util.ArrayList;
import java.util.List;

public class RadiationManager {
	private static void calculateAndUpdateRadiationResistance(final LivingEntity livingEntity, final IRadiation radiation) {
		float resistance = 0f;

		// Reset resistance
		radiation.setRadiationResistance(0f);

		MobEffectInstance effectInstance = livingEntity.getEffect(ModMobEffects.RAD_X_EFFECT.get());
		if (effectInstance != null) {
			resistance += (RadiationHelper.getRadXExposureResistance() * (effectInstance.getAmplifier() + 1));
		}

		for (ItemStack armorSlotItemStack : livingEntity.getArmorSlots()) {
			if (armorSlotItemStack.isEmpty())
				continue;

			resistance += RadiationHelper.getRadiationResistanceOfEquipment(armorSlotItemStack.getItem());
		}

		radiation.setRadiationResistance(resistance);
	}

	private static void accumulateRadiationFromNearbySources(final Level level, final LivingEntity livingEntity) {

		// Entity sources
		for (Entity entity : level.getEntitiesOfClass(Entity.class, new AABB(livingEntity.blockPosition()).inflate(RadiationHelper.getRadiaitonMaxDistance()))) {
			LazyOptional<IRadiationSource> radiationSourceOptional;
			if (entity instanceof ItemEntity) {
				radiationSourceOptional = ((ItemEntity) entity).getItem().getCapability(RadiationSourceCapability.RADIATION_SOURCE_CAPABILITY);
			} else {
				radiationSourceOptional = entity.getCapability(RadiationSourceCapability.RADIATION_SOURCE_CAPABILITY);
			}

			if (!radiationSourceOptional.isPresent())
				continue;

			// Don't allow entities to irradiate themselves
			if (livingEntity.getId() != entity.getId()) {
				IRadiationSource radiationSource = radiationSourceOptional.orElseThrow(CapabilityNotPresentException::new);
				radiationSource.irradiateLivingEntity(level, entity, livingEntity);
			}
		}

		// Accumulate radiation from local chunks and contained block sources
		for (LevelChunk levelChunk : ChunkUtil.getChunksAround(level, livingEntity.blockPosition(), RadiationHelper.getRadiationSourceBlockChunkMaxRadius())) {
			LazyOptional<IChunkRadiation> chunkRadiationOptional = ChunkRadiationCapability.getChunkRadiation(levelChunk);

			if (chunkRadiationOptional.isPresent()) {
				List<BlockPos> nullBlockPos = new ArrayList<>();
				IChunkRadiation chunkRadiation = chunkRadiationOptional.orElseThrow(CapabilityNotPresentException::new);

				for (BlockPos blockPos : chunkRadiation.getBlockSources().keySet()) {
					IRadiationSource radiationSource = chunkRadiation.getBlockSources().get(blockPos);

					if (radiationSource == null) {
						WhyAmIGlowing.LOGGER.error("Radiation source at blockpos " + blockPos + " was null!");
						nullBlockPos.add(blockPos);
						continue;
					}

					if (blockPos == null) {
						WhyAmIGlowing.LOGGER.error("Tried using null blockpos for radiation source " + radiationSource + "!");
						nullBlockPos.add(blockPos);
						continue;
					}

					// Handle active block sources, like ftbic:reactors
					BlockEntity blockEntity = levelChunk.getBlockEntity(blockPos);
					if (blockEntity != null) {
						WhyAmIGlowing.FTBIC_INTEROP.handleReactorRadiationOutput(blockEntity);
					}

					radiationSource.irradiateLivingEntity(level, blockPos, livingEntity);
				}
				if (!nullBlockPos.isEmpty()) {
					// Clean invalid entries
					nullBlockPos.forEach((e) -> {
						chunkRadiation.getBlockSources().remove(e);
					});
				}
			}
		}


	}

	/**
	 * Increases radiation of players with radioactive material on their person.
	 * @param livingEntity the living entity to irradiate
	 */
	private static void accumulateRadiationFromInventoryItems(final LivingEntity livingEntity) {
		IRadiation radiation = livingEntity.getCapability(RadiationCapability.RADIATION_CAPABILITY).orElseThrow(CapabilityNotPresentException::new);
		float totalRads = 0f;

		if (livingEntity instanceof Player player) {
			if (player.getInventory().hasAnyMatching((s) -> s.getCapability(RadiationSourceCapability.RADIATION_SOURCE_CAPABILITY).isPresent())) {
				for (List<ItemStack> stackList : ImmutableList.of(player.getInventory().items, player.getInventory().armor, player.getInventory().offhand)) {
					for (ItemStack itemStack : stackList.stream().filter((s) -> s.getCapability(RadiationSourceCapability.RADIATION_SOURCE_CAPABILITY).isPresent()).toList()) {
						IRadiationSource radiationSource = itemStack.getCapability(RadiationSourceCapability.RADIATION_SOURCE_CAPABILITY).orElseThrow(CapabilityNotPresentException::new);

						float rads = radiationSource.getEmittedMilliremsPerSecond() * itemStack.getCount();
						totalRads += rads;
						radiation.increaseCurrentExposureMilliremsPerSecond(rads);
					}
				}
			}
		}

		// Irradiate others in area as well... fun!
		LazyOptional<IRadiationSource> radiationSourceOptional = RadiationSourceCapability.getRadiationSource(livingEntity);

		if (radiationSourceOptional.isPresent()) {
			IRadiationSource radiationSource = radiationSourceOptional.orElseThrow(CapabilityNotPresentException::new);

			radiationSource.setEmittedMilliremsPerSecond(totalRads * 0.95f);
		}
	}

	private static void decreaseRadiationPassive(final LivingEntity livingEntity, final IRadiation radiation) {
		float passiveMilliremLossPerHour = RadiationHelper.getPassiveDoseReductionMilliremPerHour();

		// Rad-Away helps on passive rem loss
		MobEffectInstance effectInstance = livingEntity.getEffect(ModMobEffects.RAD_AWAY_EFFECT.get());
		if (effectInstance != null) {
			passiveMilliremLossPerHour *= (RadiationHelper.getRadAwayDoseReductionMilliremPerHour() * (effectInstance.getAmplifier() + 1));
		}

		float passiveMilliremLossPerSecond = RadiationHelper.convertPerHourToPerSecond(passiveMilliremLossPerHour);
		radiation.decreaseAbsorbedDoseMillirems(passiveMilliremLossPerSecond * livingEntity.getRandom().nextFloat());
	}

	private static void playGeigerSound(final Player player, final SoundEvent geigerSoundEvent) {
		player.getLevel().playSound(
				null, player.getX(), player.getY(), player.getZ(),
				geigerSoundEvent, SoundSource.NEUTRAL,
				0.2f, 1.0f);
	}
	private static void doGeigerNoise(final LivingEntity livingEntity, final IRadiation radiation) {
		if (radiation.getCurrentExposureMilliremsPerSecond() == 0f)
			return;

		if (!(livingEntity instanceof Player player))
			return;

		// Only allow geiger noises if player has a Geiger counter
		if (!player.getInventory().hasAnyMatching((e) -> e.getItem() == ModItems.GEIGER_COUNTER.get()))
			return;

		// Only allow geiger noises if at least one Geiger counter has an enabled speaker.
		if (!player.getInventory().hasAnyMatching((e) -> e.getItem() == ModItems.GEIGER_COUNTER.get() && !e.getOrCreateTag().getBoolean("Silent")))
			return;

		/*
		 * Tick check to prevent sound stacking. Also, always play low tick noise so that other sounds aren't so jarring.
		 */
		float receivedDoseMilliremPerHour = RadiationHelper.convertPerSecondToPerHour(radiation.getCurrentExposureMilliremsPerSecond());
		if (receivedDoseMilliremPerHour < 1f) {
			if (player.tickCount % 40 == 0)
				playGeigerSound(player, ModSoundEvents.GEIGER_TICK_LOW.get());
		}

		if (receivedDoseMilliremPerHour >= 1f && receivedDoseMilliremPerHour < 5f) {
			if (player.tickCount % 40 == 0)	playGeigerSound(player, ModSoundEvents.GEIGER_TICK_MED.get());
		} else if (receivedDoseMilliremPerHour >= 5f && receivedDoseMilliremPerHour < 10f) {
			if (player.tickCount % 40 == 0) playGeigerSound(player, ModSoundEvents.GEIGER_TICK_HIGH.get());
		}else if (receivedDoseMilliremPerHour >= 10f && receivedDoseMilliremPerHour < 20f) {
			if (player.tickCount % 40 == 0) playGeigerSound(player, ModSoundEvents.GEIGER_TICK_VERY_HIGH.get());
		} else if (receivedDoseMilliremPerHour >= 20f){
			if (player.tickCount % 40 == 0) playGeigerSound(player, ModSoundEvents.GEIGER_TICK_EXTREME.get());
		}
	}

	public static void handleRadiation(final LivingEntity livingEntity) {
		LazyOptional<IRadiation> radiationOptional = livingEntity.getCapability(RadiationCapability.RADIATION_CAPABILITY);
		final Level level = livingEntity.getLevel();

		if (level.isClientSide)
			return;

		if (!radiationOptional.isPresent())
			return;

		IRadiation radiation = radiationOptional.orElseThrow(CapabilityNotPresentException::new);
		float currentExposure;
		float currentDose;

		// Reset received rads
		radiation.setCurrentExposureMilliremsPerSecond(0f);

		calculateAndUpdateRadiationResistance(livingEntity, radiation);

		// Accumulate via multiple methods into received rads
		accumulateRadiationFromInventoryItems(livingEntity);
		accumulateRadiationFromNearbySources(level, livingEntity);

		// Increase current absorbed dose by current received dose
		currentDose = radiation.getAbsorbedDoseMillirems();
		currentExposure = radiation.getCurrentExposureMilliremsPerSecond();
		radiation.increaseAbsorbedDoseMillirems(RadiationHelper.getAdjustedFloat(currentExposure));

		doGeigerNoise(livingEntity, radiation);

		RadiationSicknessHelper.applyAcuteRadiationSicknessForDoseAndExposureMillirems(livingEntity, currentDose, RadiationHelper.convertPerSecondToPerHour(currentExposure));
		decreaseRadiationPassive(livingEntity, radiation);
	}

	@OnlyIn(Dist.CLIENT)
	public static void handleRadiationEffects(final Level level) {
		// TODO: Make stuff glow here
	}
}
