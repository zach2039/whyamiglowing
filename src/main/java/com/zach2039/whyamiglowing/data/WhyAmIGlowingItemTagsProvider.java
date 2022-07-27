package com.zach2039.whyamiglowing.data;

import com.zach2039.whyamiglowing.WhyAmIGlowing;
import com.zach2039.whyamiglowing.init.ModItems;
import com.zach2039.whyamiglowing.init.ModTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagEntry;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;

public class WhyAmIGlowingItemTagsProvider extends ItemTagsProvider {
	public WhyAmIGlowingItemTagsProvider(final DataGenerator dataGenerator, final BlockTagsProvider blockTagProvider, @Nullable final ExistingFileHelper existingFileHelper) {
		super(dataGenerator, blockTagProvider, WhyAmIGlowing.MODID, existingFileHelper);
	}

	@Override
	protected void addTags() {
		tag(ModTags.Items.FUEL_ROD_URANIUM)
				.add(TagEntry.element(new ResourceLocation("ftbic", "uranium_fuel_rod")))
				;

		tag(ModTags.Items.DUAL_FUEL_ROD_URANIUM)
				.add(TagEntry.element(new ResourceLocation("ftbic", "dual_uranium_fuel_rod")))
				;

		tag(ModTags.Items.QUAD_FUEL_ROD_URANIUM)
				.add(TagEntry.element(new ResourceLocation("ftbic", "quad_uranium_fuel_rod")))
				;

		// Radiation Protection Equipment
		{
			final var veryLowRadProtectionTag = tag(ModTags.Items.VERY_LOW_RADIATION_PROTECTION_EQUIPMENT);
			final var lowRadProtectionTag = tag(ModTags.Items.LOW_RADIATION_PROTECTION_EQUIPMENT);
			final var mediumRadProtectionTag = tag(ModTags.Items.MEDIUM_RADIATION_PROTECTION_EQUIPMENT);
			final var highRadProtectionTag = tag(ModTags.Items.HIGH_RADIATION_PROTECTION_EQUIPMENT);
			final var veryHighRadProtectionTag = tag(ModTags.Items.VERY_HIGH_RADIATION_PROTECTION_EQUIPMENT);
			final var hazmatGearPieceTag = tag(ModTags.Items.HAZMAT_GEAR_PIECE);

			veryLowRadProtectionTag.add(Items.IRON_HELMET);
			veryLowRadProtectionTag.add(Items.IRON_BOOTS);
			veryLowRadProtectionTag.add(Items.GOLDEN_HELMET);
			veryLowRadProtectionTag.add(Items.GOLDEN_BOOTS);

			lowRadProtectionTag.add(Items.IRON_CHESTPLATE);
			lowRadProtectionTag.add(Items.GOLDEN_CHESTPLATE);
			lowRadProtectionTag.add(Items.IRON_LEGGINGS);
			lowRadProtectionTag.add(Items.GOLDEN_LEGGINGS);

			mediumRadProtectionTag.add(ModItems.HAZMAT_RESPIRATOR_MK_1.get());
			mediumRadProtectionTag.add(ModItems.HAZMAT_BOOTS_MK_1.get());
			mediumRadProtectionTag.addOptional(new ResourceLocation("ftbic", "carbon_helmet"));
			mediumRadProtectionTag.addOptional(new ResourceLocation("ftbic", "carbon_boots"));

			highRadProtectionTag.add(ModItems.HAZMAT_SUIT_TOP_MK_1.get());
			highRadProtectionTag.add(ModItems.HAZMAT_SUIT_BOTTOM_MK_1.get());
			highRadProtectionTag.add(ModItems.HAZMAT_RESPIRATOR_MK_2.get());
			highRadProtectionTag.add(ModItems.HAZMAT_BOOTS_MK_2.get());
			highRadProtectionTag.addOptional(new ResourceLocation("ftbic", "carbon_chestplate"));
			highRadProtectionTag.addOptional(new ResourceLocation("ftbic", "carbon_leggings"));
			highRadProtectionTag.addOptional(new ResourceLocation("ftbic", "quantum_helmet"));
			highRadProtectionTag.addOptional(new ResourceLocation("ftbic", "quantum_boots"));


			veryHighRadProtectionTag.add(ModItems.HAZMAT_SUIT_TOP_MK_2.get());
			veryHighRadProtectionTag.add(ModItems.HAZMAT_SUIT_BOTTOM_MK_2.get());
			veryHighRadProtectionTag.addOptional(new ResourceLocation("ftbic", "quantum_chestplate"));
			veryHighRadProtectionTag.addOptional(new ResourceLocation("ftbic", "quantum_leggings"));

			hazmatGearPieceTag.add(ModItems.HAZMAT_RESPIRATOR_MK_1.get());
			hazmatGearPieceTag.add(ModItems.HAZMAT_RESPIRATOR_MK_2.get());
			hazmatGearPieceTag.add(ModItems.HAZMAT_SUIT_TOP_MK_1.get());
			hazmatGearPieceTag.add(ModItems.HAZMAT_SUIT_TOP_MK_2.get());
			hazmatGearPieceTag.add(ModItems.HAZMAT_SUIT_BOTTOM_MK_1.get());
			hazmatGearPieceTag.add(ModItems.HAZMAT_SUIT_BOTTOM_MK_2.get());
			hazmatGearPieceTag.add(ModItems.HAZMAT_BOOTS_MK_1.get());
			hazmatGearPieceTag.add(ModItems.HAZMAT_BOOTS_MK_2.get());
			hazmatGearPieceTag.addOptional(new ResourceLocation("ftbic", "carbon_helmet"));
			hazmatGearPieceTag.addOptional(new ResourceLocation("ftbic", "carbon_chestplate"));
			hazmatGearPieceTag.addOptional(new ResourceLocation("ftbic", "carbon_leggings"));
			hazmatGearPieceTag.addOptional(new ResourceLocation("ftbic", "carbon_boots"));
			hazmatGearPieceTag.addOptional(new ResourceLocation("ftbic", "quantum_helmet"));
			hazmatGearPieceTag.addOptional(new ResourceLocation("ftbic", "quantum_chestplate"));
			hazmatGearPieceTag.addOptional(new ResourceLocation("ftbic", "quantum_leggings"));
			hazmatGearPieceTag.addOptional(new ResourceLocation("ftbic", "quantum_boots"));
		}

		tag(ModTags.Items.RADIATION_SHIELDING)
				.addTags(ModTags.Items.HEAVY_RADIATION_SHIELDING)
				.addTags(ModTags.Items.MEDIUM_RADIATION_SHIELDING)
				.addTags(ModTags.Items.LIGHT_RADIATION_SHIELDING)
				;

		tag(ModTags.Items.HEAVY_RADIATION_SHIELDING)
				.addOptionalTag(new ResourceLocation("forge", "storage_blocks/iridium"))
				.addOptionalTag(new ResourceLocation("forge", "storage_blocks/platinum"))
				.addOptionalTag(new ResourceLocation("forge", "storage_blocks/tungsten"))
				.addOptionalTag(new ResourceLocation("forge", "storage_blocks/mercury"))
				.addOptionalTag(new ResourceLocation("forge", "storage_blocks/lead"))
				.addOptionalTag(new ResourceLocation("forge", "storage_blocks/silver"))
				.addOptionalTag(new ResourceLocation("forge", "storage_blocks/bismuth"))
				.addOptionalTag(new ResourceLocation("forge", "storage_blocks/electrum"))
				.addOptionalTag(new ResourceLocation("forge", "storage_blocks/enderium"))
				.addOptionalTag(new ResourceLocation("factorymade", "plate_blocks/lead"))
				.addOptionalTag(new ResourceLocation("factorymade", "non_slip_walkway_blocks/lead"))
				.addOptionalTag(new ResourceLocation("factorymade", "truss_blocks/lead"))
				.addOptional(new ResourceLocation("ftbic", "reinforced_stone"))
				.addTag(Tags.Items.STORAGE_BLOCKS_GOLD)
				.addTag(Tags.Items.STORAGE_BLOCKS_NETHERITE)
				;

		tag(ModTags.Items.MEDIUM_RADIATION_SHIELDING)
				.addOptionalTag(new ResourceLocation("forge", "storage_blocks/nickel"))
				.addOptionalTag(new ResourceLocation("forge", "storage_blocks/cobalt"))
				.addOptionalTag(new ResourceLocation("forge", "storage_blocks/cadmium"))
				.addOptionalTag(new ResourceLocation("forge", "storage_blocks/steel"))
				.addOptionalTag(new ResourceLocation("forge", "storage_blocks/constantan"))
				.addOptionalTag(new ResourceLocation("forge", "storage_blocks/invar"))
				.addOptionalTag(new ResourceLocation("factorymade", "plate_blocks/iron"))
				.addOptionalTag(new ResourceLocation("factorymade", "non_slip_walkway_blocks/iron"))
				.addOptionalTag(new ResourceLocation("factorymade", "truss_blocks/iron"))
				.addOptional(new ResourceLocation("ftbic", "reinforced_glass"))
				.addTag(Tags.Items.STORAGE_BLOCKS_IRON)
				.addTag(Tags.Items.STORAGE_BLOCKS_COPPER)
				.add(Blocks.WAXED_COPPER_BLOCK.asItem())
				.add(Blocks.EXPOSED_COPPER.asItem())
				.add(Blocks.WEATHERED_COPPER.asItem())
				.add(Blocks.OXIDIZED_COPPER.asItem())
				.add(Blocks.CUT_COPPER.asItem())
				.add(Blocks.EXPOSED_COPPER.asItem())
				.add(Blocks.WEATHERED_COPPER.asItem())
				.add(Blocks.OXIDIZED_COPPER.asItem())
				.add(Blocks.WAXED_CUT_COPPER.asItem())
				.add(Blocks.WAXED_EXPOSED_COPPER.asItem())
				.add(Blocks.WAXED_WEATHERED_COPPER.asItem())
				.add(Blocks.WAXED_OXIDIZED_COPPER.asItem())
				;

		tag(ModTags.Items.LIGHT_RADIATION_SHIELDING)
				.addOptionalTag(new ResourceLocation("forge", "storage_blocks/tin"))
				.addOptionalTag(new ResourceLocation("forge", "storage_blocks/chromium"))
				.addOptionalTag(new ResourceLocation("forge", "storage_blocks/zinc"))
				.addOptionalTag(new ResourceLocation("forge", "storage_blocks/antimony"))
				.addOptionalTag(new ResourceLocation("forge", "storage_blocks/aluminium"))
				.addOptionalTag(new ResourceLocation("forge", "storage_blocks/aluminum"))
				.addOptionalTag(new ResourceLocation("forge", "storage_blocks/bronze"))
				.addOptionalTag(new ResourceLocation("forge", "storage_blocks/brass"))
				.addOptionalTag(new ResourceLocation("factorymade", "cinder_block_bricks_blocks/any"))
				.addOptionalTag(new ResourceLocation("factorymade", "concrete_siding_blocks/any"))
				.addOptionalTag(new ResourceLocation("factorymade", "asphalt_blocks/any"))
				.addTag(Tags.Items.STONE)
				.addTag(Tags.Items.COBBLESTONE)
				.addTag(Tags.Items.STORAGE_BLOCKS_QUARTZ)
				.add(Blocks.BRICKS.asItem())
				.add(Blocks.STONE_BRICKS.asItem())
				.add(Blocks.MOSSY_STONE_BRICKS.asItem())
				.add(Blocks.CRACKED_STONE_BRICKS.asItem())
				.add(Blocks.CHISELED_STONE_BRICKS.asItem())
				.add(Blocks.MUD_BRICKS.asItem())
				.add(Blocks.DEEPSLATE_BRICKS.asItem())
				.add(Blocks.CRACKED_STONE_BRICKS.asItem())
				.add(Blocks.RED_NETHER_BRICKS.asItem())
				.add(Blocks.NETHER_BRICKS.asItem())
				.add(Blocks.CHISELED_NETHER_BRICKS.asItem())
				.add(Blocks.CRACKED_NETHER_BRICKS.asItem())
				.add(Blocks.END_STONE_BRICKS.asItem())
				.add(Blocks.QUARTZ_BRICKS.asItem())
				.add(Blocks.QUARTZ_PILLAR.asItem())
				.add(Blocks.PRISMARINE_BRICKS.asItem())
				.add(Blocks.BLACKSTONE.asItem())
				.add(Blocks.GILDED_BLACKSTONE.asItem())
				.add(Blocks.POLISHED_BLACKSTONE.asItem())
				.add(Blocks.CHISELED_POLISHED_BLACKSTONE.asItem())
				.add(Blocks.POLISHED_BLACKSTONE_BRICKS.asItem())
				.add(Blocks.CRACKED_POLISHED_BLACKSTONE_BRICKS.asItem())
				.add(Blocks.DRIPSTONE_BLOCK.asItem())
				.add(Blocks.SMOOTH_STONE.asItem())
				.add(Blocks.TINTED_GLASS.asItem())
				;
	}
}

