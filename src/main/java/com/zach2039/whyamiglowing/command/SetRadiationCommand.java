package com.zach2039.whyamiglowing.command;

import com.mojang.brigadier.builder.ArgumentBuilder;
import com.zach2039.whyamiglowing.api.capability.radiation.IRadiation;
import com.zach2039.whyamiglowing.text.WhyAmIGlowingLang;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.world.entity.LivingEntity;

public class SetRadiationCommand {
	static ArgumentBuilder<CommandSourceStack, ?> register() {
		return RadiationCommand.create(
				Commands.literal("set").requires((sourceStack) -> sourceStack.hasPermission(4)),
				SetRadiationCommand::setDosageMillirems,
				WhyAmIGlowingLang.MESSAGE_RADIATION_SET.getTranslationKey()
		);
	}

	private static void setDosageMillirems(final LivingEntity entity, final IRadiation radiation, final float amount) {
		radiation.setAbsorbedDoseMillirems(amount);
	}
}
