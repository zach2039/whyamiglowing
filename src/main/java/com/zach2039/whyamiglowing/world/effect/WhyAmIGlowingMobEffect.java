package com.zach2039.whyamiglowing.world.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

import java.awt.*;

public class WhyAmIGlowingMobEffect extends MobEffect {
	protected WhyAmIGlowingMobEffect(final MobEffectCategory effectCategory, final int liquidColor) {
		super(effectCategory, liquidColor);
	}

	public WhyAmIGlowingMobEffect(final MobEffectCategory effectCategory, final int colorR, final int colorG, final int colorB, final int colorA) {
		this(effectCategory, new Color(colorR, colorG, colorB, colorA).getRGB());
	}
}
