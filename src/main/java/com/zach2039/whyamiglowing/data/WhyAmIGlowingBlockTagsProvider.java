package com.zach2039.whyamiglowing.data;

import com.zach2039.whyamiglowing.WhyAmIGlowing;
import com.zach2039.whyamiglowing.init.ModTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagEntry;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;

public class WhyAmIGlowingBlockTagsProvider extends BlockTagsProvider {
	public WhyAmIGlowingBlockTagsProvider(final DataGenerator dataGenerator, @Nullable final ExistingFileHelper existingFileHelper) {
		super(dataGenerator, WhyAmIGlowing.MODID, existingFileHelper);
	}

	@Override
	protected void addTags() {
		tag(ModTags.Blocks.RADIATION_SHIELDING)
				.addTags(ModTags.Blocks.HEAVY_RADIATION_SHIELDING)
				.addTags(ModTags.Blocks.MEDIUM_RADIATION_SHIELDING)
				.addTags(ModTags.Blocks.LIGHT_RADIATION_SHIELDING)
				;

		tag(ModTags.Blocks.HEAVY_RADIATION_SHIELDING)
				.addTag(Tags.Blocks.STORAGE_BLOCKS_NETHERITE)
				.add(TagEntry.element(new ResourceLocation("ftbic", "lead_block")))
				.add(TagEntry.element(new ResourceLocation("ftbic", "reinforced_stone")))
				;

		tag(ModTags.Blocks.MEDIUM_RADIATION_SHIELDING)
				.addTag(Tags.Blocks.STORAGE_BLOCKS_IRON)
				.addTag(Tags.Blocks.STORAGE_BLOCKS_GOLD)
				.addTag(Tags.Blocks.STORAGE_BLOCKS_COPPER)
				.add(Blocks.WAXED_COPPER_BLOCK)
				.add(Blocks.EXPOSED_COPPER)
				.add(Blocks.WEATHERED_COPPER)
				.add(Blocks.OXIDIZED_COPPER)
				.add(Blocks.CUT_COPPER)
				.add(Blocks.EXPOSED_COPPER)
				.add(Blocks.WEATHERED_COPPER)
				.add(Blocks.OXIDIZED_COPPER)
				.add(Blocks.WAXED_CUT_COPPER)
				.add(Blocks.WAXED_EXPOSED_COPPER)
				.add(Blocks.WAXED_WEATHERED_COPPER)
				.add(Blocks.WAXED_OXIDIZED_COPPER)
				;

		tag(ModTags.Blocks.LIGHT_RADIATION_SHIELDING)
				.addTag(Tags.Blocks.STONE)
				.addTag(Tags.Blocks.COBBLESTONE)
				.addTag(Tags.Blocks.STORAGE_BLOCKS_QUARTZ)
				.add(Blocks.WATER)
				.add(Blocks.BRICKS)
				.add(Blocks.STONE_BRICKS)
				.add(Blocks.MOSSY_STONE_BRICKS)
				.add(Blocks.CRACKED_STONE_BRICKS)
				.add(Blocks.CHISELED_STONE_BRICKS)
				.add(Blocks.MUD_BRICKS)
				.add(Blocks.DEEPSLATE_BRICKS)
				.add(Blocks.CRACKED_STONE_BRICKS)
				.add(Blocks.RED_NETHER_BRICKS)
				.add(Blocks.NETHER_BRICKS)
				.add(Blocks.CHISELED_NETHER_BRICKS)
				.add(Blocks.CRACKED_NETHER_BRICKS)
				.add(Blocks.END_STONE_BRICKS)
				.add(Blocks.QUARTZ_BRICKS)
				.add(Blocks.QUARTZ_PILLAR)
				.add(Blocks.PRISMARINE_BRICKS)
				.add(Blocks.BLACKSTONE)
				.add(Blocks.GILDED_BLACKSTONE)
				.add(Blocks.POLISHED_BLACKSTONE)
				.add(Blocks.CHISELED_POLISHED_BLACKSTONE)
				.add(Blocks.POLISHED_BLACKSTONE_BRICKS)
				.add(Blocks.CRACKED_POLISHED_BLACKSTONE_BRICKS)
				.add(Blocks.DRIPSTONE_BLOCK)
				.add(Blocks.SMOOTH_STONE)
				.add(Blocks.TINTED_GLASS)
				;
	}
}


