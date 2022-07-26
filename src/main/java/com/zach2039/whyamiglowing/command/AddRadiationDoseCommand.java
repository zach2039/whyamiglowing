package com.zach2039.whyamiglowing.command;

import com.mojang.brigadier.builder.ArgumentBuilder;
import com.zach2039.whyamiglowing.api.capability.radiation.IRadiation;
import com.zach2039.whyamiglowing.text.WhyAmIGlowingLang;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.world.entity.LivingEntity;

public class AddRadiationDoseCommand {
	static ArgumentBuilder<CommandSourceStack, ?> register() {
		return RadiationCommand.create(
				Commands.literal("add_dose").requires((sourceStack) -> sourceStack.hasPermission(4)),
				AddRadiationDoseCommand::addDosageMillirems,
				WhyAmIGlowingLang.MESSAGE_DOSE_ADD.getTranslationKey()
		);
	}

	private static void addDosageMillirems(final LivingEntity entity, final IRadiation radiation, final float amount) {
		radiation.increaseAbsorbedDoseMillirems(amount);
	}
}
