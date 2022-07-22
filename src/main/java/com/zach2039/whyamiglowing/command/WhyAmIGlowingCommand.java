package com.zach2039.whyamiglowing.command;

import com.mojang.brigadier.CommandDispatcher;
import com.zach2039.whyamiglowing.api.capability.radiation.IRadiation;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

/**
 * Base class for commands that affect an entity's {@link IRadiation}.
 *
 * @author Choonster
 *
 * With edits/additions by:
 * @author zach2039
 */
public class WhyAmIGlowingCommand {

	public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
		dispatcher.register(
			Commands.literal("whyamiglowing")
					.then(RadiationCommand.register())
		);
	}

}
