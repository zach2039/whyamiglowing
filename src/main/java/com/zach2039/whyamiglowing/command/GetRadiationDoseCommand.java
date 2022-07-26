package com.zach2039.whyamiglowing.command;

import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
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

public class GetRadiationDoseCommand {
	private static final SimpleCommandExceptionType INVALID_ENTITY_EXCEPTION = new SimpleCommandExceptionType(Component.translatable("commands.whyamiglowing.radiation.invalid_entity"));

	static ArgumentBuilder<CommandSourceStack, ?> register() {
		return Commands.literal("get_dose").requires((sourceStack) -> sourceStack.hasPermission(4))
				.then(Commands.argument("entity", EntityArgument.entity())
						.executes(context ->
								execute(
										context,
										EntityArgument.getEntity(context, "entity")
								)
						)
				);
	}

	private static int execute(final CommandContext<CommandSourceStack> context, final Entity entity) throws CommandSyntaxException {
		if (!(entity instanceof final LivingEntity livingEntity)) {
			throw INVALID_ENTITY_EXCEPTION.create();
		}

		final var radiation = RadiationCapability
				.getRadiation(livingEntity)
				.orElseThrow(CapabilityNotPresentException::new);

		context.getSource().sendSuccess(
				Component.translatable(
						WhyAmIGlowingLang.MESSAGE_DOSE_GET.getTranslationKey(),
						entity.getDisplayName(),
						RadiationHelper.getDosageDisplayMillirems(radiation.getAbsorbedDoseMillirems())
				),
				true
		);

		return 0;
	}
}
