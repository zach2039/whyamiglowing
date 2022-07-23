package com.zach2039.whyamiglowing.command;

import com.mojang.brigadier.builder.ArgumentBuilder;
import com.zach2039.whyamiglowing.api.capability.radiation.IRadiation;
import com.zach2039.whyamiglowing.text.WhyAmIGlowingLang;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.world.entity.LivingEntity;

public class AddRadiationCommand {
	static ArgumentBuilder<CommandSourceStack, ?> register() {
		return RadiationCommand.create(
				Commands.literal("add").requires((sourceStack) -> sourceStack.hasPermission(4)),
				AddRadiationCommand::addDosageMillirems,
				WhyAmIGlowingLang.MESSAGE_RADIATION_ADD.getTranslationKey()
		);
	}

	private static void addDosageMillirems(final LivingEntity entity, final IRadiation radiation, final float amount) {
		radiation.increaseAbsorbedDoseMillirems(amount);
	}
}
