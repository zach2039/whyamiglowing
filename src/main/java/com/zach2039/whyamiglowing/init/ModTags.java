package com.zach2039.whyamiglowing.init;

import com.zach2039.whyamiglowing.WhyAmIGlowing;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.versions.forge.ForgeVersion;

public class ModTags {
	public static class Blocks {

		public static final TagKey<Block> RADIATION_SHIELDING = tag("radiation_shielding/any");

		public static final TagKey<Block> LIGHT_RADIATION_SHIELDING = tag("radiation_shielding/light");

		public static final TagKey<Block> MEDIUM_RADIATION_SHIELDING = tag("radiation_shielding/medium");

		public static final TagKey<Block> HEAVY_RADIATION_SHIELDING = tag("radiation_shielding/heavy");

		private static TagKey<Block> tag(final String name) {
			return BlockTags.create(new ResourceLocation(WhyAmIGlowing.MODID, name));
		}
	}

	public static class Items {
		public static final TagKey<Item> FUEL_ROD_URANIUM = tag("fuel_rod/uranium");
		public static final TagKey<Item> DUAL_FUEL_ROD_URANIUM = tag("dual_fuel_rod/uranium");
		public static final TagKey<Item> QUAD_FUEL_ROD_URANIUM = tag("quad_fuel_rod/uranium");

		private static TagKey<Item> tag(final String name) {
			return ItemTags.create(new ResourceLocation(WhyAmIGlowing.MODID, name));
		}

		private static TagKey<Item> forgeTag(final String name) {
			return ItemTags.create(new ResourceLocation(ForgeVersion.MOD_ID, name));
		}
	}
}
