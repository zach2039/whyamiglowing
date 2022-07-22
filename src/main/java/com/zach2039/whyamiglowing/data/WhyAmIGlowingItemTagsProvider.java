package com.zach2039.whyamiglowing.data;

import com.zach2039.whyamiglowing.WhyAmIGlowing;
import com.zach2039.whyamiglowing.init.ModTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagEntry;
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
	}
}
