package com.zach2039.whyamiglowing.world.effect;

import com.zach2039.whyamiglowing.api.capability.radiation.IRadiation;
import com.zach2039.whyamiglowing.capability.radiation.RadiationCapability;
import com.zach2039.whyamiglowing.util.CapabilityNotPresentException;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.util.LazyOptional;

public class RadXEffect extends WhyAmIGlowingMobEffect {

	public RadXEffect() {
		super(MobEffectCategory.BENEFICIAL, 204, 104, 0, 25);
	}

	@Override
	public void applyEffectTick(LivingEntity livingEntity, int level) {
		// Handled in RadiationManager.
	}
}
