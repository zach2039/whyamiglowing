package com.zach2039.whyamiglowing.world.effect;

import com.zach2039.whyamiglowing.WhyAmIGlowing;
import com.zach2039.whyamiglowing.core.RadiationSicknessHelper;
import com.zach2039.whyamiglowing.core.SicknessSeverity;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;

import java.lang.reflect.Field;

public class AcuteRadiationSyndromeEffect extends WhyAmIGlowingMobEffect {

	public AcuteRadiationSyndromeEffect() {
		super(MobEffectCategory.HARMFUL, 255, 255, 0, 1);
	}

	@Override
	public void applyEffectTick(LivingEntity livingEntity, int level) {
		SicknessSeverity severity = SicknessSeverity.values()[level];

		if (RadiationSicknessHelper.shouldApplyARSSymptoms(severity, livingEntity.getEffect(this).getDuration())) {
			RadiationSicknessHelper.applySymptomsForARS(livingEntity, severity);
		}

		if (RadiationSicknessHelper.doesRegenerationSpeedUpRecovery()) {
			if (livingEntity.hasEffect(MobEffects.REGENERATION) && livingEntity.getEffect(this) != null) {
				Field duration = ObfuscationReflectionHelper.findField(MobEffectInstance.class, "duration");
				try {
					int currentDuration = (int) duration.get(livingEntity.getEffect(this));
					WhyAmIGlowing.LOGGER.debug("Sickness Duration Before (s): " + currentDuration / 20);
					currentDuration = Math.max(1, currentDuration - (20 * 500)); // Reduce duration by 500 seconds every TICKS_BETWEEN_SYMPTOM_APPLICATION ticks
					duration.set(livingEntity.getEffect(this), currentDuration);
					WhyAmIGlowing.LOGGER.debug("Sickness Duration After (s): " + currentDuration / 20);
				} catch (IllegalAccessException e) {
					throw new RuntimeException(e);
				}
			}
		}
	}

	@Override
	public boolean isDurationEffectTick(int l, int m) {
		return l % RadiationSicknessHelper.TICKS_BETWEEN_SYMPTOM_APPLICATION == 0; // Always apply per X seconds
	}

}
