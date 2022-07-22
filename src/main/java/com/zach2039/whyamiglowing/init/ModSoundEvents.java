package com.zach2039.whyamiglowing.init;

import com.zach2039.whyamiglowing.WhyAmIGlowing;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModSoundEvents {
	private static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, WhyAmIGlowing.MODID);

	private static boolean isInitialized;

	public static final RegistryObject<SoundEvent> GEIGER_TICK_LOW = registerSoundEvent("geiger_tick.low");

	public static final RegistryObject<SoundEvent> GEIGER_TICK_MED = registerSoundEvent("geiger_tick.med");

	public static final RegistryObject<SoundEvent> GEIGER_TICK_HIGH = registerSoundEvent("geiger_tick.high");

	public static final RegistryObject<SoundEvent> GEIGER_TICK_VERY_HIGH = registerSoundEvent("geiger_tick.very_high");

	public static final RegistryObject<SoundEvent> GEIGER_TICK_EXTREME = registerSoundEvent("geiger_tick.extreme");

	public static void initialize(final IEventBus modEventBus) {
		if (isInitialized) {
			throw new IllegalStateException("Already initialized");
		}

		SOUND_EVENTS.register(modEventBus);

		isInitialized = true;
	}

	private static RegistryObject<SoundEvent> registerSoundEvent(final String soundName) {
		return SOUND_EVENTS.register(soundName, () -> new SoundEvent(new ResourceLocation(WhyAmIGlowing.MODID, soundName)));
	}
}
