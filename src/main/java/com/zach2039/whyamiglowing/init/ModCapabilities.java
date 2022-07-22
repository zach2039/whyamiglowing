package com.zach2039.whyamiglowing.init;

import com.zach2039.whyamiglowing.WhyAmIGlowing;
import com.zach2039.whyamiglowing.capability.chunkradiation.ChunkRadiationCapability;
import com.zach2039.whyamiglowing.capability.radiation.RadiationCapability;
import com.zach2039.whyamiglowing.capability.radiationsource.RadiationSourceCapability;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod.EventBusSubscriber(modid = WhyAmIGlowing.MODID, bus = Bus.MOD)
public class ModCapabilities {

	@SubscribeEvent
	public static void registerCapabilities(final RegisterCapabilitiesEvent event) {
		RadiationCapability.register(event);
		RadiationSourceCapability.register(event);
		ChunkRadiationCapability.register(event);
	}
}
