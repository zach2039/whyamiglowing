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

	}
}


