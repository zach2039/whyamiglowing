package com.zach2039.whyamiglowing.core;

import com.zach2039.whyamiglowing.init.ModMobEffects;
import com.zach2039.whyamiglowing.text.WhyAmIGlowingLang;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class RadiationSicknessHelper {

	public static final int TICKS_BETWEEN_SYMPTOM_APPLICATION = 600;

	private static void applyHungerSymptom(final LivingEntity livingEntity, int level) {
		livingEntity.addEffect(new MobEffectInstance(MobEffects.HUNGER, TICKS_BETWEEN_SYMPTOM_APPLICATION, level, false, false, false)); // Hidden hunger
	}

	private static void applyPoisonSymptom(final LivingEntity livingEntity, int level) {
		livingEntity.addEffect(new MobEffectInstance(MobEffects.POISON, TICKS_BETWEEN_SYMPTOM_APPLICATION, level, false, false, false)); // Hidden poison
	}

	private static void applyWitherSymptom(final LivingEntity livingEntity, int level) {
		livingEntity.addEffect(new MobEffectInstance(MobEffects.WITHER, TICKS_BETWEEN_SYMPTOM_APPLICATION, level, false, false, false)); // Hidden wither
	}

	private static void applyConfusionSymptom(final LivingEntity livingEntity, int level, float chance, int duration) {
		if (livingEntity.getRandom().nextFloat() < chance) {
			livingEntity.addEffect(new MobEffectInstance(MobEffects.CONFUSION, duration, level, false, false, false)); // Hidden confusion
		}
	}

	private static void applyBlindnessSymptom(final LivingEntity livingEntity, int level, float chance, int duration) {
		if (livingEntity.getRandom().nextFloat() < chance) {
			livingEntity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, duration, level, false, false, false)); // Hidden blindness
		}
	}

	private static void applyFatigueSymptom(final LivingEntity livingEntity, int level, float chance) {
		if (livingEntity.getRandom().nextFloat() < chance) {
			livingEntity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, TICKS_BETWEEN_SYMPTOM_APPLICATION, level, false, false, false)); // Hidden weakness
			livingEntity.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, TICKS_BETWEEN_SYMPTOM_APPLICATION, level, false, false, false)); // Hidden mining fatigue
			livingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, TICKS_BETWEEN_SYMPTOM_APPLICATION, level, false, false, false)); // Hidden slowness
		}
	}

	public static void sendRandomChatMessageForSicknessSeverity(final Player player, final SicknessSeverity severity) {
		if (!(player.getRandom().nextFloat() < 0.05f))
			return; // Dont always print a message

		MutableComponent warningMsg = Component.empty();

		if (severity.ordinal() <= SicknessSeverity.MILD.ordinal()) {
			switch(player.getRandom().nextInt(3)) {
				case 0:
					warningMsg.append(Component.translatable(WhyAmIGlowingLang.MESSAGE_EXPOSURE_SYMPTOM_0.getTranslationKey()));
					break;
				case 1:
					warningMsg.append(Component.translatable(WhyAmIGlowingLang.MESSAGE_EXPOSURE_SYMPTOM_1.getTranslationKey()));
					break;
				case 2:
					warningMsg.append(Component.translatable(WhyAmIGlowingLang.MESSAGE_EXPOSURE_SYMPTOM_2.getTranslationKey()));
					break;
			}
			warningMsg.withStyle(ChatFormatting.YELLOW).withStyle(ChatFormatting.ITALIC);
		} else if (severity.ordinal() <= SicknessSeverity.MODERATE.ordinal()) {
			switch (player.getRandom().nextInt(4)) {
				case 0:
					warningMsg.append(Component.translatable(WhyAmIGlowingLang.MESSAGE_HIGH_EXPOSURE_SYMPTOM_0.getTranslationKey()));
					break;
				case 1:
					warningMsg.append(Component.translatable(WhyAmIGlowingLang.MESSAGE_HIGH_EXPOSURE_SYMPTOM_1.getTranslationKey()));
					break;
				case 2:
					warningMsg.append(Component.translatable(WhyAmIGlowingLang.MESSAGE_HIGH_EXPOSURE_SYMPTOM_2.getTranslationKey()));
					break;
				case 3:
					warningMsg.append(Component.translatable(WhyAmIGlowingLang.MESSAGE_HIGH_EXPOSURE_SYMPTOM_3.getTranslationKey()));
					break;
			}
			warningMsg.withStyle(ChatFormatting.RED).withStyle(ChatFormatting.ITALIC);
		} else if (severity.ordinal() <= SicknessSeverity.SEVERE.ordinal()) {
			switch (player.getRandom().nextInt(4)) {
				case 0:
					warningMsg.append(Component.translatable(WhyAmIGlowingLang.MESSAGE_EXTREME_EXPOSURE_SYMPTOM_0.getTranslationKey()));
					break;
				case 1:
					warningMsg.append(Component.translatable(WhyAmIGlowingLang.MESSAGE_EXTREME_EXPOSURE_SYMPTOM_1.getTranslationKey()));
					break;
				case 2:
					warningMsg.append(Component.translatable(WhyAmIGlowingLang.MESSAGE_EXTREME_EXPOSURE_SYMPTOM_2.getTranslationKey()));
					break;
				case 3:
					warningMsg.append(Component.translatable(WhyAmIGlowingLang.MESSAGE_EXTREME_EXPOSURE_SYMPTOM_3.getTranslationKey()));
					break;
			}
			warningMsg.withStyle(ChatFormatting.LIGHT_PURPLE).withStyle(ChatFormatting.ITALIC);
		} else {
			return;
		}

		player.sendSystemMessage(warningMsg);
	}

	public static void applySymptomsForARS(final LivingEntity livingEntity, final SicknessSeverity severity) {
		if (livingEntity instanceof Player player) {
			if (player.isCreative())
				return; // Dont apply symptoms in creative mode
		}

		switch (severity) {
			case SLIGHT:
				applyHungerSymptom(livingEntity, 0);
				break;
			case MILD:
				applyHungerSymptom(livingEntity, 1);
				applyPoisonSymptom(livingEntity, 0);
				applyConfusionSymptom(livingEntity, 0, 0.025f, TICKS_BETWEEN_SYMPTOM_APPLICATION / 4);
				applyFatigueSymptom(livingEntity, 0, 0.05f);
				break;
			case MODERATE:
				applyHungerSymptom(livingEntity, 2);
				applyWitherSymptom(livingEntity, 0);
				applyConfusionSymptom(livingEntity, 0, 0.05f, TICKS_BETWEEN_SYMPTOM_APPLICATION / 3);
				applyBlindnessSymptom(livingEntity, 0, 0.025f, TICKS_BETWEEN_SYMPTOM_APPLICATION / 3);
				applyFatigueSymptom(livingEntity, 0, 0.1f);
				break;
			case SEVERE:
				applyHungerSymptom(livingEntity, 3);
				applyWitherSymptom(livingEntity, 1);
				applyConfusionSymptom(livingEntity, 0, 0.075f, TICKS_BETWEEN_SYMPTOM_APPLICATION / 2);
				applyBlindnessSymptom(livingEntity, 0, 0.05f, TICKS_BETWEEN_SYMPTOM_APPLICATION / 2);
				applyFatigueSymptom(livingEntity, 0, 0.25f);
				break;
			case FATAL:
			default:
				applyHungerSymptom(livingEntity, 4);
				applyWitherSymptom(livingEntity, 2);
				applyConfusionSymptom(livingEntity, 0, 0.1f, TICKS_BETWEEN_SYMPTOM_APPLICATION);
				applyBlindnessSymptom(livingEntity, 0, 0.075f, TICKS_BETWEEN_SYMPTOM_APPLICATION);
				applyFatigueSymptom(livingEntity, 1, 1f);
				break;
		}

		if (livingEntity instanceof Player player)
			RadiationSicknessHelper.sendRandomChatMessageForSicknessSeverity(player, severity);
	}

	public static boolean shouldApplyARSSymptoms(SicknessSeverity severity, int currentDurationTicks) {
		if (getTicksForARSTotalDuration(severity) - currentDurationTicks > getTicksForARSImmediateOnset(severity)) {
			return true;
		}

		return false;
	}

	public static int getTicksForARSTotalDuration(SicknessSeverity severity) {
		int ticks = getTicksForARSImmediateOnset(severity); // include onset in duration

		switch (severity) {
			case SLIGHT:
				ticks += 12000; // 12 hours in-game time
			case MILD:
				ticks += 24000; // 24 hours in-game time
			case MODERATE:
				ticks += 36000; // 36 hours in-game time
			case SEVERE:
				ticks += 48000; // 48 hours in-game time
			case FATAL:
			default:
				ticks += 128000; // 128 hours in-game time
		}

		return ticks;
	}

	public static int getTicksForARSImmediateOnset(SicknessSeverity severity) {
		switch (severity) {
			case SLIGHT:
				return 6000; // 6 hours in-game time
			case MILD:
				return 2000; // 2 hours in-game time
			case MODERATE:
				return 1000; // 1 hour in-game time
			case SEVERE:
				return 166; // 10 minutes in-game time
			case FATAL:
			default:
				return 32; // 2 minutes in-game time
		}
	}

	private static SicknessSeverity getARSSeverityForDoseAndExposure(final float currentDoseMillirems, final float currentExposureMilliremsPerHour) {
		if (currentDoseMillirems >= 3000000f && currentExposureMilliremsPerHour >= 3000000f)
			return SicknessSeverity.FATAL;

		if (currentDoseMillirems >= 800000f && currentExposureMilliremsPerHour >= 800000f)
			return SicknessSeverity.SEVERE;

		if (currentDoseMillirems >= 600000f && currentExposureMilliremsPerHour >= 600000f)
			return SicknessSeverity.MODERATE;

		if (currentDoseMillirems >= 200000f && currentExposureMilliremsPerHour >= 200000f)
			return SicknessSeverity.MILD;

		if (currentDoseMillirems >= 100000f && currentExposureMilliremsPerHour >= 100000f)
			return SicknessSeverity.SLIGHT;

		return null;
	}

	public static void applyAcuteRadiationSicknessForDoseAndExposureMillirems(final LivingEntity livingEntity, final float currentDoseMillirems, final float currentExposureMilliremsPerHour) {
		SicknessSeverity severity = getARSSeverityForDoseAndExposure(currentDoseMillirems, currentExposureMilliremsPerHour);
		if (severity == null)
			return;

		int effectDuration = getTicksForARSTotalDuration(severity);

		// Replace sickness if new severity is greater than old severity, but subtract onset from duration to instantly apply symptoms
		MobEffectInstance sicknessInstance = livingEntity.getEffect(ModMobEffects.ACUTE_RADIATION_SICKNESS_EFFECT.get());
		if (sicknessInstance != null) {
			if (sicknessInstance.getAmplifier() > severity.ordinal()) {
				return;
			} else {
				// Remove effect for replacement
				livingEntity.removeEffect(ModMobEffects.ACUTE_RADIATION_SICKNESS_EFFECT.get());
				effectDuration -= getTicksForARSImmediateOnset(severity);
				applySymptomsForARS(livingEntity, severity);
			}
		}

		// New sickness
		livingEntity.addEffect(new MobEffectInstance(
				ModMobEffects.ACUTE_RADIATION_SICKNESS_EFFECT.get(),
				effectDuration,
				severity.ordinal(),
				false,
				false,
				true
		));
	}

}
