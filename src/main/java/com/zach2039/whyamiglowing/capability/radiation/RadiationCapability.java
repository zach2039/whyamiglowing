package com.zach2039.whyamiglowing.capability.radiation;

import com.zach2039.whyamiglowing.WhyAmIGlowing;
import com.zach2039.whyamiglowing.api.capability.radiation.IRadiation;
import com.zach2039.whyamiglowing.capability.SerializableCapabilityProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public final class RadiationCapability {
	public static final Capability<IRadiation> RADIATION_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {});

	public static final ResourceLocation ID = new ResourceLocation(WhyAmIGlowing.MODID, "radiation");

	public static void register(final RegisterCapabilitiesEvent event) {
		event.register(IRadiation.class);
	}

	public static LazyOptional<IRadiation> getRadiation(final LivingEntity entity) {
		return entity.getCapability(RADIATION_CAPABILITY);
	}

	public static ICapabilityProvider createProvider(final IRadiation radiation) {
		return new SerializableCapabilityProvider<>(RADIATION_CAPABILITY, radiation);
	}

	@SuppressWarnings("unused")
	@Mod.EventBusSubscriber(modid = WhyAmIGlowing.MODID)
	private static class EventHandler {

		@SubscribeEvent
		public static void attachCapabilities(final AttachCapabilitiesEvent<Entity> event) {
			if (event.getObject() instanceof LivingEntity) {
				final Radiation radiation = new Radiation((LivingEntity) event.getObject());
				event.addCapability(ID, createProvider(radiation));
			}
		}
	}
}
