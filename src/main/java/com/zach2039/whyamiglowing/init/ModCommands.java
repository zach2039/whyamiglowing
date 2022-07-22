package com.zach2039.whyamiglowing.init;

import com.zach2039.whyamiglowing.WhyAmIGlowing;
import com.zach2039.whyamiglowing.command.WhyAmIGlowingCommand;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = WhyAmIGlowing.MODID)
public class ModCommands {

	@SubscribeEvent
	public static void registerCommands(final RegisterCommandsEvent event) {
		WhyAmIGlowingCommand.register(event.getDispatcher());
	}
}
