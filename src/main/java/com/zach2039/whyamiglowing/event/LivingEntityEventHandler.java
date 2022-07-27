package com.zach2039.whyamiglowing.event;

import com.zach2039.whyamiglowing.WhyAmIGlowing;
import com.zach2039.whyamiglowing.api.capability.radiation.IRadiation;
import com.zach2039.whyamiglowing.capability.radiation.RadiationCapability;
import com.zach2039.whyamiglowing.capability.radiationsource.RadiationSourceCapability;
import com.zach2039.whyamiglowing.core.RadiationManager;
import com.zach2039.whyamiglowing.init.ModMobEffects;
import com.zach2039.whyamiglowing.util.CapabilityNotPresentException;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = WhyAmIGlowing.MODID)
public class LivingEntityEventHandler {

	@SubscribeEvent
	public static void onLivingEntityEvent(final LivingEvent.LivingTickEvent event) {
		if (event.getEntity() != null) {
			final LivingEntity livingEntity = event.getEntity();
			if (livingEntity.tickCount % 20 == 0) { // Every second, manage radiation
				RadiationManager.handleRadiation(livingEntity);
			}
		}
	}

	@SubscribeEvent
	public static void onPotionEffectRemoved(final MobEffectEvent.Remove event) {
		if (event.getEffect() == ModMobEffects.ACUTE_RADIATION_SICKNESS_EFFECT.get()) {
			// Prevent removal of radiation sickness via milk

			if (event.getEntity() instanceof Player player) {
				if (player.isCreative())
					return; // allow creative mode removal
			}

			event.setCanceled(true);
		}
	}

	@SubscribeEvent
	public static void onEntityDeath(final LivingEvent event) {
		if (event.getEntity() != null) {
			if (event.getEntity().isDeadOrDying()) {
				// Convert entities to other types if radiation on death was high enough
				LazyOptional<IRadiation> radiationOptional = RadiationCapability.getRadiation(event.getEntity());

				if (!radiationOptional.isPresent())
					return;

				IRadiation radiation = radiationOptional.orElseThrow(CapabilityNotPresentException::new);
				// TODO: Convert entities
			}
		}
	}
}
