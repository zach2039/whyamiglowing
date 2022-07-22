package com.zach2039.whyamiglowing.world.effect;

import com.zach2039.whyamiglowing.core.RadiationSicknessHelper;
import com.zach2039.whyamiglowing.core.SicknessSeverity;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

import javax.print.attribute.standard.Severity;

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
	}

	@Override
	public boolean isDurationEffectTick(int l, int m) {
		return l % RadiationSicknessHelper.TICKS_BETWEEN_SYMPTOM_APPLICATION == 0; // Always apply per X seconds
	}

}
