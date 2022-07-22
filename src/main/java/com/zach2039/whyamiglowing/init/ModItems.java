package com.zach2039.whyamiglowing.init;

import com.zach2039.whyamiglowing.WhyAmIGlowing;
import com.zach2039.whyamiglowing.world.item.GeigerCounterItem;
import com.zach2039.whyamiglowing.world.item.RadAwayItem;
import com.zach2039.whyamiglowing.world.item.RadXItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
	private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, WhyAmIGlowing.MODID);

	private static boolean isInitialized;

	// Equipment
	public static final RegistryObject<GeigerCounterItem> GEIGER_COUNTER = ITEMS.register("geiger_counter",
			() -> new GeigerCounterItem(defaultItemProperties().durability(1024))
	);

	// Consumables
	public static final RegistryObject<RadXItem> RAD_X = ITEMS.register("rad_x",
			() -> new RadXItem(defaultItemProperties().stacksTo(4))
	);
	public static final RegistryObject<RadAwayItem> RAD_AWAY = ITEMS.register("rad_away",
			() -> new RadAwayItem(defaultItemProperties().stacksTo(4))
	);

	public static void initialize(final IEventBus modEventBus) {
		if (isInitialized) {
			throw new IllegalStateException("Already initialized");
		}

		ITEMS.register(modEventBus);

		isInitialized = true;
	}

	private static Item.Properties defaultItemProperties() {
		return new Item.Properties().tab(WhyAmIGlowing.CREATIVE_MODE_TAB);
	}
}
