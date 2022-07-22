package com.zach2039.whyamiglowing.world.effect;

import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class RadAwayEffect extends WhyAmIGlowingMobEffect {

	public RadAwayEffect() {
		super(MobEffectCategory.BENEFICIAL, 204, 204, 0, 25);
	}

	@Override
	public void applyEffectTick(LivingEntity livingEntity, int level) {
		// Handled in RadiationManager.
	}
}
