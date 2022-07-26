package com.zach2039.whyamiglowing.world.effect;

import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class IodineDefenseEffect extends WhyAmIGlowingMobEffect {

	public IodineDefenseEffect() {
		super(MobEffectCategory.BENEFICIAL, 204, 104, 0, 25);
	}

	@Override
	public void applyEffectTick(LivingEntity livingEntity, int level) {
		// Handled in RadiationManager.
	}
}
