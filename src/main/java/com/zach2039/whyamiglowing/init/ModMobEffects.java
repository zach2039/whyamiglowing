package com.zach2039.whyamiglowing.init;

import com.zach2039.whyamiglowing.WhyAmIGlowing;
import com.zach2039.whyamiglowing.world.effect.AcuteRadiationSyndromeEffect;
import com.zach2039.whyamiglowing.world.effect.RadXEffect;
import com.zach2039.whyamiglowing.world.effect.WhyAmIGlowingMobEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModMobEffects {
	private static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, WhyAmIGlowing.MODID);

	private static boolean isInitialized;

	public static final RegistryObject<WhyAmIGlowingMobEffect> RAD_X_EFFECT = EFFECTS.register("rad_x", RadXEffect::new);
	public static final RegistryObject<WhyAmIGlowingMobEffect> RAD_AWAY_EFFECT = EFFECTS.register("rad_away", RadXEffect::new);

	public static final RegistryObject<WhyAmIGlowingMobEffect> ACUTE_RADIATION_SICKNESS_EFFECT = EFFECTS.register("acute_radiation_sickness", AcuteRadiationSyndromeEffect::new);

	public static void initialize(final IEventBus modEventBus) {
		if (isInitialized) {
			throw new IllegalStateException("Already initialized");
		}

		EFFECTS.register(modEventBus);

		isInitialized = true;
	}
}
