package com.zach2039.whyamiglowing.command;

import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
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

/**
 * Base class for commands that affect an entity's {@link IRadiation}.
 *
 * @author Choonster
 *
 * With edits/additions by:
 * @author zach2039
 */
public class RadiationCommand {
	protected static final SimpleCommandExceptionType INVALID_ENTITY_EXCEPTION = new SimpleCommandExceptionType(
			Component.translatable(WhyAmIGlowingLang.COMMAND_RADIATION_INVALID_ENTITY.getTranslationKey())
	);

	public static ArgumentBuilder<CommandSourceStack, ?> register() {
		return Commands.literal("radiation")
				.then(AddRadiationContaminationCommand.register())
				.then(GetRadiationContaminationCommand.register())
				.then(SetRadiationContaminationCommand.register())
				.then(AddRadiationDoseCommand.register())
				.then(GetRadiationDoseCommand.register())
				.then(SetRadiationDoseCommand.register());
	}

	static ArgumentBuilder<CommandSourceStack, ?> create(final ArgumentBuilder<CommandSourceStack, ?> builder, final IEntityProcessor processor, final String successMessage) {
		return builder
				.then(Commands.argument("entity", EntityArgument.entities())
						.then(Commands.argument("amount", FloatArgumentType.floatArg())
								.executes(context ->
										execute(
												context,
												EntityArgument.getEntity(context, "entity"),
												FloatArgumentType.getFloat(context, "amount"),
												processor,
												successMessage
										))

						)
				);
	}

	/**
	 * Executes the command.
	 *
	 * @param context        The command context
	 * @param entity         The specified entity
	 * @param amount         The specified amount
	 * @param processor      The entity processor
	 * @param successMessage The translation key of the message to send when the command succeeds.
	 *                       This will be provided with the entity's display name and the amount as format arguments.
	 */
	private static int execute(final CommandContext<CommandSourceStack> context, final Entity entity, final float amount, final IEntityProcessor processor, final String successMessage) throws CommandSyntaxException, CommandSyntaxException {
		if (!(entity instanceof final LivingEntity livingEntity)) {
			throw INVALID_ENTITY_EXCEPTION.create();
		}

		final var radiation = RadiationCapability
				.getRadiation(livingEntity)
				.orElseThrow(CapabilityNotPresentException::new);

		processor.process(livingEntity, radiation, amount);

		context.getSource()
				.sendSuccess(Component.translatable(successMessage, entity.getDisplayName(), RadiationHelper.getDosageDisplayMillirems(radiation.getAbsorbedDoseMillirems())), true);

		return 0;
	}

	@FunctionalInterface
	interface IEntityProcessor {
		/**
		 * Make a change to the livingentity's {@link IRadiation}.
		 * @author Choonster
		 *
		 * @param entity    The entity
		 * @param radiation The entity's IRadiation
		 * @param amount    The dosage amount to add/set
		 */
		void process(LivingEntity entity, IRadiation radiation, float amount);
	}
}
