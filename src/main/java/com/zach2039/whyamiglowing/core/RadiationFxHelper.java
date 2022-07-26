package com.zach2039.whyamiglowing.core;

import com.zach2039.whyamiglowing.api.capability.radiation.IRadiation;
import com.zach2039.whyamiglowing.init.ModItems;
import com.zach2039.whyamiglowing.init.ModSoundEvents;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class RadiationFxHelper {

	private static void playGeigerSound(final Player player, final SoundEvent geigerSoundEvent) {
		player.getLevel().playSound(
				null, player.getX(), player.getY(), player.getZ(),
				geigerSoundEvent, SoundSource.PLAYERS,
				0.05f, 1.0f);
	}

	public static void doGeigerNoise(final LivingEntity livingEntity, final IRadiation radiation) {
		if (radiation.getCurrentTotalExposureMilliremsPerSecond() == 0f)
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
		float receivedDoseMilliremPerHour = RadiationHelper.convertPerSecondToPerHour(radiation.getCurrentTotalExposureMilliremsPerSecond());
		if (receivedDoseMilliremPerHour < 100f) {
			if (player.tickCount % 40 == 0)
				playGeigerSound(player, ModSoundEvents.GEIGER_TICK_LOW.get());
		}

		if (receivedDoseMilliremPerHour >= 100f && receivedDoseMilliremPerHour < 500f) {
			if (player.tickCount % 40 == 0)	playGeigerSound(player, ModSoundEvents.GEIGER_TICK_MED.get());
		} else if (receivedDoseMilliremPerHour >= 500f && receivedDoseMilliremPerHour < 5000f) {
			if (player.tickCount % 40 == 0) playGeigerSound(player, ModSoundEvents.GEIGER_TICK_HIGH.get());
		}else if (receivedDoseMilliremPerHour >= 5000f && receivedDoseMilliremPerHour < 50000f) {
			if (player.tickCount % 40 == 0) playGeigerSound(player, ModSoundEvents.GEIGER_TICK_VERY_HIGH.get());
		} else if (receivedDoseMilliremPerHour >= 50000f){
			if (player.tickCount % 40 == 0) playGeigerSound(player, ModSoundEvents.GEIGER_TICK_EXTREME.get());
		}
	}

	@OnlyIn(Dist.CLIENT)
	public static void handleRadiationEffects(final Level level) {
		// TODO: Make stuff glow here
	}
}
