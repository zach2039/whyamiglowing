package com.zach2039.whyamiglowing.core;

import com.mojang.datafixers.util.Pair;
import com.zach2039.whyamiglowing.WhyAmIGlowing;
import com.zach2039.whyamiglowing.api.capability.chunkradiation.IChunkRadiation;
import com.zach2039.whyamiglowing.api.capability.radiation.IRadiation;
import com.zach2039.whyamiglowing.api.capability.radiationsource.IRadiationSource;
import com.zach2039.whyamiglowing.capability.chunkradiation.ChunkRadiationCapability;
import com.zach2039.whyamiglowing.capability.radiation.RadiationCapability;
import com.zach2039.whyamiglowing.capability.radiationsource.RadiationSourceCapability;
import com.zach2039.whyamiglowing.config.WhyAmIGlowingConfig;
import com.zach2039.whyamiglowing.init.ModTags;
import com.zach2039.whyamiglowing.text.WhyAmIGlowingLang;
import com.zach2039.whyamiglowing.util.CapabilityNotPresentException;
import com.zach2039.whyamiglowing.util.RegistryUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.util.LazyOptional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class RadiationHelper {

	public static final float TOLERANCE = 0.0001f;

	private static final WhyAmIGlowingConfig.Server serverConfig = WhyAmIGlowingConfig.SERVER;

	public static float getAdjustedFloat(float value) {
		if (value < TOLERANCE) {
			return 0f;
		}
		return value;
	}

	public static int getRadiaitonMaxDistance() {
		return serverConfig.radiationMaxDistance.get();
	}

	public static int getRadiationSourceBlockChunkMaxRadius() {
		return serverConfig.radiationSourceBlockChunkMaxRadius.get();
	}

	public static int getRadiationShieldingScanDistance() {
		return serverConfig.radiationShieldingScanDistance.get();
	}

	public static float getLightRadiationShieldingReductionFactor() {
		return serverConfig.lightRadiationShieldingReductionFactor.get().floatValue();
	}

	public static float getMediumRadiationShieldingReductionFactor() {
		return serverConfig.mediumRadiationShieldingReductionFactor.get().floatValue();
	}

	public static float getHeavyRadiationShieldingReductionFactor() {
		return serverConfig.heavyRadiationShieldingReductionFactor.get().floatValue();
	}

	public static float getPassiveDoseReductionMilliremPerHour() {
		return serverConfig.passiveDoseReductionMilliremPerHour.get().floatValue();
	}

	public static float getRadAwayDoseReductionMilliremPerHour() {
		return serverConfig.radAwayDoseReductionMilliremPerHour.get().floatValue();
	}

	public static int getRadAwayMaxLevel() {
		return serverConfig.radAwayMaxLevel.get();
	}

	public static int getRadAwayDurationTicks() {
		return serverConfig.radAwayDurationTicks.get();
	}

	public static float getRadXExposureResistance() {
		return serverConfig.radXExposureResistancePerLevel.get().floatValue();
	}

	public static int getRadXMaxLevel() {
		return serverConfig.radXMaxLevel.get();
	}

	public static int getRadXDurationTicks() {
		return serverConfig.radXDurationTicks.get();
	}

	public static void cleanInvalidBlockToSourceBlocks(final Level level) {
		// TODO: Implement this?
	}

	public static IRadiationSource getRadiationSourceFromChunk(final Level level, final BlockPos blockPos) {
		LevelChunk levelChunk = (LevelChunk) level.getChunk(blockPos);

		LazyOptional<IChunkRadiation> chunkRadiationOptional = ChunkRadiationCapability.getChunkRadiation(levelChunk);

		if (!chunkRadiationOptional.isPresent())
			return null;

		IChunkRadiation chunkRadiation = chunkRadiationOptional.orElseThrow(CapabilityNotPresentException::new);

		IRadiationSource radiationSource = chunkRadiation.getBlockSources().get(blockPos);

		return radiationSource;
	}

	public static List<BlockState> getShieldBlocksBetween(final Level level, Vec3 start, Vec3 finish) {
		List<BlockState> blocksBetween = new ArrayList<BlockState>();

		Vec3 vecStart = new Vec3((double) Mth.floor(start.x) + 0.5, (double)Mth.floor(start.y) + 0.5, (double)Mth.floor(start.z) + 0.5);
		Vec3 vecFinish = new Vec3((double)Mth.floor(finish.x) + 0.5, (double)Mth.floor(finish.y) + 0.5, (double)Mth.floor(finish.z) + 0.5);

		// Accumulate blocks for shielding
		BlockPos pos = new BlockPos(vecStart);
		Vec3 vecDir = vecFinish.subtract(vecStart).normalize();
		for (int i = 0; i < 32; i++) {
			BlockState blockState = level.getBlockState(pos);

			if (pos == new BlockPos(vecFinish))
				break;

			if (blockState.is(ModTags.Blocks.RADIATION_SHIELDING)) {
				blocksBetween.add(blockState);
			}

			int dirX = (int) vecDir.x;
			int dirY = (int) vecDir.y;
			int dirZ = (int) vecDir.z;
			pos = pos.offset(dirX, dirY, dirZ);
			vecDir = vecFinish.subtract(new Vec3(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5)).normalize();
		}

		double lerp = 1 / vecFinish.distanceTo(vecStart);
		Direction[] directions = Direction.values();
		int numDirections = directions.length;
		for (double j = 0d; j < 1.1d; j = j + lerp) {
			Vec3 lerpVec = vecFinish.lerp(vecStart, j);
			for(int dirOrdinal = 0; dirOrdinal < numDirections; ++dirOrdinal) {
				Direction direction = directions[dirOrdinal];
				BlockState blockState = level.getBlockState(new BlockPos(lerpVec).relative(direction));

				if (blockState.isAir())
					continue;

				if (blockState.is(ModTags.Blocks.RADIATION_SHIELDING)) {
					blocksBetween.add(blockState);
				}
			}
		}

		return blocksBetween;
	}

	public static void scanWithGeigerCounter(final Player player, final LivingEntity target) {
		// Get dose, receiving, and output of interacted entity.
		LazyOptional<IRadiation> radiationOptional = RadiationCapability.getRadiation(target);
		LazyOptional<IRadiationSource> radiationSourceOptional = RadiationSourceCapability.getRadiationSource(target);

		if (radiationOptional.isPresent() && radiationSourceOptional.isPresent()) {
			IRadiation radiation = radiationOptional.orElseThrow(CapabilityNotPresentException::new);
			IRadiationSource radiationSource = radiationSourceOptional.orElseThrow(CapabilityNotPresentException::new);

			float currentAbsorbedDoseMillirems = radiation.getAbsorbedDoseMillirems();
			float currentReceivedDoseMilliremsPerHour = RadiationHelper.convertPerSecondToPerHour(radiation.getCurrentExposureMilliremsPerSecond());
			float currentOutputExposureMilliremsPerHour = RadiationHelper.convertPerSecondToPerHour(radiationSource.getEmittedMilliremsPerSecond());
			float currentRadiationResistance = radiation.getRadiationResistance();

			String absorbedDose = RadiationHelper.getDosageDisplayMillirems(currentAbsorbedDoseMillirems);
			String currentExposure = RadiationHelper.getDosageDisplayMilliremsPerHour(currentReceivedDoseMilliremsPerHour);
			String effectiveExposure = RadiationHelper.getDosageDisplayMilliremsPerHour(currentReceivedDoseMilliremsPerHour * (1f - currentRadiationResistance));
			String currentOutput = RadiationHelper.getDosageDisplayMilliremsPerHour(currentOutputExposureMilliremsPerHour);
			String radiationResistance = String.format("%.2f%%", currentRadiationResistance * 100f);

			player.sendSystemMessage(Component.translatable(
					WhyAmIGlowingLang.MESSAGE_GEIGER_SCAN_0.getTranslationKey(), target.getName()
			).withStyle(ChatFormatting.BOLD).withStyle(ChatFormatting.ITALIC));
			player.sendSystemMessage(Component.translatable(
					WhyAmIGlowingLang.MESSAGE_GEIGER_SCAN_1.getTranslationKey(), absorbedDose
			).withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC));
			player.sendSystemMessage(Component.translatable(
					WhyAmIGlowingLang.MESSAGE_GEIGER_SCAN_2.getTranslationKey(), currentExposure, effectiveExposure
			).withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC));
			player.sendSystemMessage(Component.translatable(
					WhyAmIGlowingLang.MESSAGE_GEIGER_SCAN_3.getTranslationKey(), currentOutput
			).withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC));
			player.sendSystemMessage(Component.translatable(
					WhyAmIGlowingLang.MESSAGE_GEIGER_SCAN_4.getTranslationKey(), radiationResistance
			).withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC));

			WhyAmIGlowing.LOGGER.info("Entity : " + target.getName());
			WhyAmIGlowing.LOGGER.info("       absorbed : " + radiation.getAbsorbedDoseMillirems());
			WhyAmIGlowing.LOGGER.info("       current  : " + radiation.getCurrentExposureMilliremsPerSecond());
			WhyAmIGlowing.LOGGER.info("       resist   : " + radiation.getRadiationResistance());
		}
	}

	public static float getRadiationResistanceOfEquipment(Item item) {

		//WhyAmIGlowing.LOGGER.info(RegistryUtil.getKey(item).toString());
		//WhyAmIGlowing.LOGGER.info(serverConfig.veryLowResistanceEquipment.get());

		if (serverConfig.veryLowResistanceEquipment.get().contains(RegistryUtil.getKey(item).toString())) {
			return serverConfig.veryLowResistanceEquipmentBonus.get().floatValue();
		}

		if (serverConfig.lowResistanceEquipment.get().contains(RegistryUtil.getKey(item).toString())) {
			return serverConfig.lowResistanceEquipmentBonus.get().floatValue();
		}

		if (serverConfig.medResistanceEquipment.get().contains(RegistryUtil.getKey(item).toString())) {
			return serverConfig.medResistanceEquipmentBonus.get().floatValue();
		}

		if (serverConfig.highResistanceEquipment.get().contains(RegistryUtil.getKey(item).toString())) {
			return serverConfig.highResistanceEquipmentBonus.get().floatValue();
		}

		if (serverConfig.veryHighResistanceEquipment.get().contains(RegistryUtil.getKey(item).toString())) {
			return serverConfig.veryHighResistanceEquipmentBonus.get().floatValue();
		}

		return 0.0f;
	}

	public static float convertMilliremToRem(float millirem) {
		return millirem / 1000f;
	}

	public static float convertPerSecondToPerHour(float perSecond) {
		float perHour = perSecond * 60f * 60f;
		return perHour;
	}

	public static float convertPerHourToPerSecond(float perHour) {
		float perSecond = perHour / 60f / 60f;
		return perSecond;
	}

	public static ChatFormatting getChatColorForMilliremsPerHour(final float milliremsPerHour) {
		if (milliremsPerHour < 100000f)
			return ChatFormatting.YELLOW;

		if (milliremsPerHour < 300000f)
			return ChatFormatting.RED;

		return ChatFormatting.LIGHT_PURPLE;
	}



	public static String getDosageDisplayMillirems(final float doseMillirems) {
		String doseUnit;
		String absorbedDose;
		if (doseMillirems < 1000f) {
			doseUnit = "mrem";
			absorbedDose = String.format("%.1f", doseMillirems);
		} else {
			doseUnit = "rem";
			absorbedDose = String.format("%.1f", convertMilliremToRem(doseMillirems));
		}

		return String.format("%s %s", absorbedDose, doseUnit);
	}

	public static String getDosageDisplayMilliremsPerHour(final float doseMilliremsPerHour) {
		String doseUnit;
		String absorbedDose;
		String receivedType = (doseMilliremsPerHour >= 0) ? "+" : "";
		if (Math.abs(doseMilliremsPerHour) < 1000f) {
			doseUnit = "mrem/h";
			absorbedDose = String.format("%.1f", doseMilliremsPerHour);
		} else {
			doseUnit = "rem/h";
			absorbedDose = String.format("%.1f", convertMilliremToRem(doseMilliremsPerHour));
		}

		return String.format("%s%s %s", receivedType, absorbedDose, doseUnit);
	}
}
