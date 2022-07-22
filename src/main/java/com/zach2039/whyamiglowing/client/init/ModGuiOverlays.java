package com.zach2039.whyamiglowing.client.init;

import com.zach2039.whyamiglowing.WhyAmIGlowing;
import com.zach2039.whyamiglowing.capability.radiation.RadiationCapability;
import com.zach2039.whyamiglowing.client.gui.GeigerCounterGuiOverlay;
import com.zach2039.whyamiglowing.text.WhyAmIGlowingLang;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod.EventBusSubscriber(modid = WhyAmIGlowing.MODID, bus = Bus.MOD, value = Dist.CLIENT)
public class ModGuiOverlays {

	@SubscribeEvent
	public static void registerGuiOverlays(final RegisterGuiOverlaysEvent event) {
		event.registerAboveAll(WhyAmIGlowingLang.GEIGER_COUNTER_HUD.getTranslationKey(), new GeigerCounterGuiOverlay(Minecraft.getInstance()));
	}
}
