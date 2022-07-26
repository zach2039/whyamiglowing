package com.zach2039.whyamiglowing.command;

import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.zach2039.whyamiglowing.api.capability.radiation.IRadiation;
import com.zach2039.whyamiglowing.capability.radiation.RadiationCapability;
import com.zach2039.whyamiglowing.core.RadiationHelper;
import com.zach2039.whyamiglowing.text.WhyAmIGlowingLang;
import com.zach2039.whyamiglowing.util.CapabilityNotPresentException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public class SetRadiationContaminationCommand {

	static ArgumentBuilder<CommandSourceStack, ?> register() {
		return Commands.literal("set_contamination").requires((sourceStack) -> sourceStack.hasPermission(4))
				.then(Commands.argument("entity", EntityArgument.entity())
						.then(Commands.argument("amount", FloatArgumentType.floatArg())
								.executes(context ->
										execute(
												context,
												EntityArgument.getEntity(context, "entity"),
												FloatArgumentType.getFloat(context, "amount")
										))

						)
				);
	}

	private static int execute(final CommandContext<CommandSourceStack> context, final Entity entity, final float amount) throws CommandSyntaxException {
		if (!(entity instanceof final LivingEntity livingEntity)) {
			throw RadiationCommand.INVALID_ENTITY_EXCEPTION.create();
		}

		final var radiation = RadiationCapability
				.getRadiation(livingEntity)
				.orElseThrow(CapabilityNotPresentException::new);

		radiation.setContaminationMilliremsPerSecond(RadiationHelper.convertPerHourToPerSecond(amount));

		context.getSource().sendSuccess(
				Component.translatable(
						WhyAmIGlowingLang.MESSAGE_CONTAMINATION_SET.getTranslationKey(),
						entity.getDisplayName(),
						RadiationHelper.getDosageDisplayMilliremsPerHour(RadiationHelper.convertPerSecondToPerHour(radiation.getContaminationMilliremsPerSecond()))
				),
				true
		);

		return 0;
	}
}
