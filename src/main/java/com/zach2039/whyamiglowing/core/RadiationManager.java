package com.zach2039.whyamiglowing.core;

import com.google.common.collect.ImmutableList;
import com.zach2039.whyamiglowing.WhyAmIGlowing;
import com.zach2039.whyamiglowing.api.capability.chunkradiation.IChunkRadiation;
import com.zach2039.whyamiglowing.api.capability.radiation.IRadiation;
import com.zach2039.whyamiglowing.api.capability.radiationsource.IRadiationSource;
import com.zach2039.whyamiglowing.capability.chunkradiation.ChunkRadiationCapability;
import com.zach2039.whyamiglowing.capability.radiation.RadiationCapability;
import com.zach2039.whyamiglowing.capability.radiationsource.RadiationSourceCapability;
import com.zach2039.whyamiglowing.init.ModMobEffects;
import com.zach2039.whyamiglowing.util.CapabilityNotPresentException;
import com.zach2039.whyamiglowing.util.ChunkUtil;
import com.zach2039.whyamiglowing.util.EntityUtil;
import com.zach2039.whyamiglowing.util.MathHelper;
import net.minecraft.core.BlockPos;
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
import net.minecraftforge.common.util.LazyOptional;

import java.util.ArrayList;
import java.util.List;

public class RadiationManager {
	private static void calculateAndUpdateRadiationResistance(final LivingEntity livingEntity, final IRadiation radiation) {
		float resistance = 0f;
		float internalResistance = 0f;

		// Reset resistances
		radiation.setRadiationResistance(0f);
		radiation.setInternalRadiationResistance(0f);

		MobEffectInstance radXEffectInstance = livingEntity.getEffect(ModMobEffects.RAD_X_EFFECT.get());
		if (radXEffectInstance != null) {
			resistance += (RadiationHelper.getRadXExposureResistance() * (radXEffectInstance.getAmplifier() + 1));
		}

		MobEffectInstance iodineDefenseEffectInstance = livingEntity.getEffect(ModMobEffects.IODINE_DEFENSE_EFFECT.get());
		if (iodineDefenseEffectInstance != null) {
			internalResistance += (RadiationHelper.getIodineInternalExposureResistance() * (iodineDefenseEffectInstance.getAmplifier() + 1));
		}

		for (ItemStack armorSlotItemStack : livingEntity.getArmorSlots()) {
			if (armorSlotItemStack.isEmpty())
				continue;

			resistance += RadiationHelper.getRadiationResistanceOfEquipment(armorSlotItemStack);
		}

		// Get set bonuses for those with full gear on
		resistance += RadiationHelper.getHazmatSetBonus(livingEntity);

		radiation.setRadiationResistance(resistance);
		radiation.setInternalRadiationResistance(internalResistance);
	}

	private static float accumulateRadiationFromNearbySources(final Level level, final LivingEntity livingEntity) {
		float totalDose = 0f;

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
				totalDose += radiationSource.irradiateLivingEntity(level, entity, livingEntity);
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
						WhyAmIGlowing.LOGGER.warn("Radiation source at blockpos " + blockPos + " was null!");
						nullBlockPos.add(blockPos);
						continue;
					}

					if (blockPos == null) {
						WhyAmIGlowing.LOGGER.warn("Tried using null blockpos for radiation source " + radiationSource + "!");
						nullBlockPos.add(blockPos);
						continue;
					}

					if (levelChunk.getBlockState(blockPos).isAir()) {
						WhyAmIGlowing.LOGGER.debug("Radiation source " + radiationSource + " no longer valid at block position " + blockPos + "; removing");
						nullBlockPos.add(blockPos);
						continue;
					}

					// Handle active block sources, like ftbic:reactors
					BlockEntity blockEntity = levelChunk.getBlockEntity(blockPos);
					if (blockEntity != null) {
						WhyAmIGlowing.FTBIC_INTEROP.handleReactorRadiationOutput(blockEntity);
					}

					totalDose += radiationSource.irradiateLivingEntity(level, blockPos, livingEntity);
				}

				if (!nullBlockPos.isEmpty()) {
					// Clean invalid entries
					nullBlockPos.forEach((e) -> {
						chunkRadiation.getBlockSources().remove(e);
					});
				}
			}
		}

		return totalDose;
	}

	/**
	 * Increases radiation of players with radioactive material on their person.
	 * @param livingEntity the living entity to irradiate
	 */
	private static float accumulateRadiationFromInventoryItems(final LivingEntity livingEntity) {
		IRadiation radiation = livingEntity.getCapability(RadiationCapability.RADIATION_CAPABILITY).orElseThrow(CapabilityNotPresentException::new);
		float totalDose = 0f;

		if (livingEntity instanceof Player player) {
			if (player.getInventory().hasAnyMatching((s) -> s.getCapability(RadiationSourceCapability.RADIATION_SOURCE_CAPABILITY).isPresent())) {
				for (List<ItemStack> stackList : ImmutableList.of(player.getInventory().items, player.getInventory().armor, player.getInventory().offhand)) {
					for (ItemStack itemStack : stackList.stream().filter((s) -> s.getCapability(RadiationSourceCapability.RADIATION_SOURCE_CAPABILITY).isPresent()).toList()) {
						IRadiationSource radiationSource = itemStack.getCapability(RadiationSourceCapability.RADIATION_SOURCE_CAPABILITY).orElseThrow(CapabilityNotPresentException::new);

						float dose = radiationSource.getEmittedMilliremsPerSecond() * itemStack.getCount();
						totalDose += MathHelper.tol(dose);
					}
				}
			}
		}



		return totalDose;
	}

	private static void irradiateOthers(final LivingEntity livingEntity, final float currentExposureFromItems, final float currentContamination) {
		// Irradiate others in area as well... fun!
		LazyOptional<IRadiationSource> radiationSourceOptional = RadiationSourceCapability.getRadiationSource(livingEntity);

		if (radiationSourceOptional.isPresent()) {
			IRadiationSource radiationSource = radiationSourceOptional.orElseThrow(CapabilityNotPresentException::new);

			radiationSource.setEmittedMilliremsPerSecond(MathHelper.tol((currentExposureFromItems + currentContamination) * 0.95f));
		}
	}

	private static void decreaseDoseAndContamination(final LivingEntity livingEntity, final IRadiation radiation) {
		float passiveMilliremLossPerHour = RadiationHelper.getPassiveDoseReductionMilliremPerHour();

		// Rad-Away helps on passive rem loss
		MobEffectInstance effectInstance = livingEntity.getEffect(ModMobEffects.RAD_AWAY_EFFECT.get());
		if (effectInstance != null) {
			passiveMilliremLossPerHour *= MathHelper.tol(RadiationHelper.getRadAwayDoseReductionMilliremPerHour() * (effectInstance.getAmplifier() + 1));
		}

		float passiveMilliremLossPerSecond = RadiationHelper.convertPerHourToPerSecond(passiveMilliremLossPerHour);
		radiation.decreaseAbsorbedDoseMillirems(MathHelper.tol(passiveMilliremLossPerSecond * livingEntity.getRandom().nextFloat()));

		// Decontaminate as well if certain criteria is met
		if (livingEntity.isInWaterOrBubble()) {
			radiation.decreaseContaminationMilliremsPerSecond(RadiationHelper.convertPerHourToPerSecond(25f));
		} else if (EntityUtil.isInRain(livingEntity)) {
			radiation.decreaseContaminationMilliremsPerSecond(RadiationHelper.convertPerHourToPerSecond(5f));
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
		float currentExposure = 0f;
		float currentDose;
		float currentContamination;

		// Decrease dose and contamination
		decreaseDoseAndContamination(livingEntity, radiation);

		// Save last and reset current received rads
		radiation.setLastExternalExposureMilliremsPerSecond(radiation.getCurrentExternalExposureMilliremsPerSecond());

		calculateAndUpdateRadiationResistance(livingEntity, radiation);

		// Accumulate via multiple methods into exposure
		float exposureFromItems = accumulateRadiationFromInventoryItems(livingEntity);
		float exposureFromNearby = accumulateRadiationFromNearbySources(level, livingEntity);

		// Apply contamination
		if (!RadiationHelper.wearingFullHazmatSet(livingEntity) && exposureFromItems > 0f) { // Only increase contamination if not wearing full hazmat gear
			float contaminationFromItems = MathHelper.tol(Math.min(exposureFromItems / 100f, 0.005f));
			radiation.increaseContaminationMilliremsPerSecond(contaminationFromItems);
		}

		currentContamination = radiation.getContaminationMilliremsPerSecond();
		//currentTotalExposure += exposureFromItems + exposureFromNearby + currentContamination;

		// Apply exposure
		radiation.setCurrentExternalExposureMilliremsPerSecond(exposureFromItems + exposureFromNearby);

		// Irradiate others from current contamination and inventory contents
		irradiateOthers(livingEntity, exposureFromItems, currentContamination);

		// Increase current absorbed dose by current received dose and contamination
		radiation.increaseAbsorbedDoseMillirems(MathHelper.tol(exposureFromItems + exposureFromNearby));
		radiation.increaseAbsorbedDoseMilliremsBypassExternalResistances(MathHelper.tol(currentContamination));
		currentDose = radiation.getAbsorbedDoseMillirems();

		RadiationFxHelper.doGeigerNoise(livingEntity, radiation);

		float effectiveExposure = radiation.getCurrentTotalEffectiveExposureMilliremsPerSecond();
		RadiationSicknessHelper.applyAcuteRadiationSicknessForDoseAndExposureMillirems(livingEntity, currentDose, RadiationHelper.convertPerSecondToPerHour(effectiveExposure));

		// Error on NaN and reset
		if (Float.isNaN(radiation.getAbsorbedDoseMillirems())) {
			WhyAmIGlowing.LOGGER.error("Entity " + livingEntity + " has an invalid radiation dosage! Resetting. Report to mod author!");
			radiation.setAbsorbedDoseMillirems(0f);
		}
		if (Float.isNaN(radiation.getCurrentExternalExposureMilliremsPerSecond())) {
			WhyAmIGlowing.LOGGER.error("Entity " + livingEntity + " has an invalid radiation exposure! Resetting. Report to mod author!");
			radiation.setCurrentExternalExposureMilliremsPerSecond(0f);
		}
	}


}
