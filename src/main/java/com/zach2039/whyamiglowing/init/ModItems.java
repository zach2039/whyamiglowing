package com.zach2039.whyamiglowing.init;

import com.zach2039.whyamiglowing.WhyAmIGlowing;
import com.zach2039.whyamiglowing.world.item.GeigerCounterItem;
import com.zach2039.whyamiglowing.world.item.RadAwayItem;
import com.zach2039.whyamiglowing.world.item.RadXItem;
import com.zach2039.whyamiglowing.world.item.material.ModArmorMaterial;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
	private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, WhyAmIGlowing.MODID);

	private static boolean isInitialized;

	// Equipment
	public static final RegistryObject<GeigerCounterItem> GEIGER_COUNTER = ITEMS.register("geiger_counter", () -> new GeigerCounterItem(defaultItemProperties().durability(1024)));
	public static final RegistryObject<ArmorItem> HAZMAT_RESPIRATOR_MK_1 = ITEMS.register("hazmat_respirator_mk_1",	() -> new ArmorItem(ModArmorMaterial.HAZMAT_MK_1, EquipmentSlot.HEAD, defaultItemProperties().durability(512)));
	public static final RegistryObject<ArmorItem> HAZMAT_RESPIRATOR_MK_2 = ITEMS.register("hazmat_respirator_mk_2",	() -> new ArmorItem(ModArmorMaterial.HAZMAT_MK_2, EquipmentSlot.HEAD, defaultItemProperties().durability(1024)));
	public static final RegistryObject<ArmorItem> HAZMAT_SUIT_TOP_MK_1 = ITEMS.register("hazmat_suit_top_mk_1",	() -> new ArmorItem(ModArmorMaterial.HAZMAT_MK_1, EquipmentSlot.CHEST, defaultItemProperties().durability(512)));
	public static final RegistryObject<ArmorItem> HAZMAT_SUIT_TOP_MK_2 = ITEMS.register("hazmat_suit_top_mk_2",	() -> new ArmorItem(ModArmorMaterial.HAZMAT_MK_2, EquipmentSlot.CHEST, defaultItemProperties().durability(1024)));
	public static final RegistryObject<ArmorItem> HAZMAT_SUIT_BOTTOM_MK_1 = ITEMS.register("hazmat_suit_bottom_mk_1",	() -> new ArmorItem(ModArmorMaterial.HAZMAT_MK_1, EquipmentSlot.LEGS, defaultItemProperties().durability(512)));
	public static final RegistryObject<ArmorItem> HAZMAT_SUIT_BOTTOM_MK_2 = ITEMS.register("hazmat_suit_bottom_mk_2",	() -> new ArmorItem(ModArmorMaterial.HAZMAT_MK_2, EquipmentSlot.LEGS, defaultItemProperties().durability(1024)));
	public static final RegistryObject<ArmorItem> HAZMAT_BOOTS_MK_1 = ITEMS.register("hazmat_boots_mk_1",	() -> new ArmorItem(ModArmorMaterial.HAZMAT_MK_1, EquipmentSlot.FEET, defaultItemProperties().durability(512)));
	public static final RegistryObject<ArmorItem> HAZMAT_BOOTS_MK_2 = ITEMS.register("hazmat_boots_mk_2",	() -> new ArmorItem(ModArmorMaterial.HAZMAT_MK_2, EquipmentSlot.FEET, defaultItemProperties().durability(1024)));

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
