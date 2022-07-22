package com.zach2039.whyamiglowing.init;

import com.zach2039.whyamiglowing.WhyAmIGlowing;
import com.zach2039.whyamiglowing.data.*;
import net.minecraft.data.DataProvider;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

/**
 * Registers this mod's {@link DataProvider}s.
 *
 * @author Choonster
 * 
 * With additions/edits by:
 * @author zach2039
 */
@Mod.EventBusSubscriber(modid = WhyAmIGlowing.MODID, bus = Bus.MOD)
public class ModDataProviders {
	@SubscribeEvent
	public static void registerDataProviders(final GatherDataEvent event) {
		final var dataGenerator = event.getGenerator();
		final var existingFileHelper = event.getExistingFileHelper();

		dataGenerator.addProvider(event.includeClient(), new WhyAmIGlowingLanguageProvider(dataGenerator));

		final var itemModelProvider = new WhyAmIGlowingItemModelProvider(dataGenerator, existingFileHelper);
		dataGenerator.addProvider(event.includeClient(), itemModelProvider);

		dataGenerator.addProvider(event.includeClient(), new WhyAmIGlowingBlockStateProvider(dataGenerator, itemModelProvider.existingFileHelper));

		//dataGenerator.addProvider(event.includeServer(), new WhyAmIGlowingRecipeProvider(dataGenerator));
		//dataGenerator.addProvider(event.includeServer(), new WhyAmIGlowingLootTableProvider(dataGenerator));
		//dataGenerator.addProvider(event.includeServer(), new WhyAmIGlowingWorldgenRegistryDumpReport(dataGenerator));
		//dataGenerator.addProvider(event.includeServer(), new WhyAmIGlowingLootModifierProvider(dataGenerator));
		//dataGenerator.addProvider(event.includeServer(), new WhyAmIGlowingBiomeModifierProvider(dataGenerator, existingFileHelper));

		final var blockTagsProvider = new WhyAmIGlowingBlockTagsProvider(dataGenerator, existingFileHelper);
		dataGenerator.addProvider(event.includeServer(), blockTagsProvider);
		dataGenerator.addProvider(event.includeServer(), new WhyAmIGlowingItemTagsProvider(dataGenerator, blockTagsProvider, existingFileHelper));
		//dataGenerator.addProvider(event.includeServer(), new WhyAmIGlowingBiomeTagsProvider(dataGenerator, existingFileHelper));
		//dataGenerator.addProvider(event.includeServer(), new WhyAmIGlowingFluidTagsProvider(dataGenerator, existingFileHelper));
	}
}
