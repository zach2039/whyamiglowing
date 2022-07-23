package com.zach2039.whyamiglowing.capability.radiationsource;

import com.zach2039.whyamiglowing.WhyAmIGlowing;
import com.zach2039.whyamiglowing.api.capability.radiationsource.IRadiationSource;
import com.zach2039.whyamiglowing.capability.SimpleCapabilityProvider;
import com.zach2039.whyamiglowing.config.WhyAmIGlowingConfig;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;

public final class RadiationSourceCapability {
	public static final Capability<IRadiationSource> RADIATION_SOURCE_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {});

	public static final ResourceLocation ID = new ResourceLocation(WhyAmIGlowing.MODID, "radiation_source");

	public static void register(final RegisterCapabilitiesEvent event) {
		event.register(IRadiationSource.class);
	}

	public static LazyOptional<IRadiationSource> getRadiationSource(final ItemStack itemStack) {
		return itemStack.getCapability(RADIATION_SOURCE_CAPABILITY);
	}

	public static LazyOptional<IRadiationSource> getRadiationSource(final Entity entity) {
		return entity.getCapability(RADIATION_SOURCE_CAPABILITY);
	}

	public static ICapabilityProvider createProvider(final IRadiationSource radiationSource) {
		return new SimpleCapabilityProvider<>(RADIATION_SOURCE_CAPABILITY, radiationSource);
	}

	public static IRadiationSource getForAttach(final ItemStack itemStack) {

		final Item item = itemStack.getItem();
		String itemKey = "";
		final List<String> radiationSourceItems = WhyAmIGlowingConfig.SERVER.radiationSourceItems.get();

		for (String sourceItem : radiationSourceItems) {
			if (sourceItem.startsWith("#")) {
				TagKey<Item> tag = TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(sourceItem.substring(1)));

				if (itemStack.is(tag)) {
					itemKey = sourceItem;
					break;
				}
			} else {
				Item itemInConfig = ForgeRegistries.ITEMS.getValue(new ResourceLocation(sourceItem));

				if (itemStack.getItem() == itemInConfig) {
					itemKey = sourceItem;
					break;
				}
			}
		}

		if (itemKey.isEmpty())
			return null;

		int idx = radiationSourceItems.indexOf(itemKey);
		if (idx != -1) {
			final List<Float> radiationSourceIntensities = WhyAmIGlowingConfig.SERVER.radiationSourceItemIntensities.get();
			final RadiationSource radiationSource = new RadiationSource(itemStack);

			float intensity = 0.1f;
			if (idx >= radiationSourceIntensities.size()) {
				WhyAmIGlowing.LOGGER.error("Radiation source intensity list is too short for radiationSourceItems list. Defaulting intensity to 0.1f");
			} else {
				intensity = radiationSourceIntensities.get(idx);
			}
			radiationSource.setEmittedMilliremsPerSecond(intensity);
			radiationSource.setMaxEmittedMilliremsPerSecond(intensity);

			WhyAmIGlowing.LOGGER.debug("Added item of item " + item.getDescriptionId() + " as radiation source of intensity " + intensity + " rem/s, which matches tag/item " + itemKey + " in config.");

			return radiationSource;
		}

		return null;
	}

	public static IRadiationSource getForAttach(final BlockState blockState) {

		String blockKey = "";
		final List<String> radiationSourceBlocks = WhyAmIGlowingConfig.SERVER.radiationSourceBlocks.get();

		for (String sourceBlock : radiationSourceBlocks) {
			if (sourceBlock.startsWith("#")) {
				TagKey<Block> tag = TagKey.create(Registry.BLOCK_REGISTRY, new ResourceLocation(sourceBlock.substring(1)));

				if (blockState.is(tag)) {
					blockKey = sourceBlock;
					break;
				}
			} else {
				Block blockInConfig = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(sourceBlock));

				if (blockInConfig.defaultBlockState().getBlock() == blockState.getBlock()) {
					blockKey = sourceBlock;
					break;
				}
			}
		}

		if (blockKey.isEmpty()) {
			return null;
		}

		int idx = radiationSourceBlocks.indexOf(blockKey);
		if (idx != -1) {
			final List<Float> radiationSourceIntensities = WhyAmIGlowingConfig.SERVER.radiationSourceBlockIntensities.get();
			final RadiationSource radiationSource = new RadiationSource();

			float intensity = 0.1f;
			if (idx >= radiationSourceIntensities.size()) {
				WhyAmIGlowing.LOGGER.error("Radiation source intensity list is too short for radiationSourceItems list. Defaulting intensity to 0.1f");
			} else {
				intensity = radiationSourceIntensities.get(idx);
			}
			radiationSource.setEmittedMilliremsPerSecond(intensity);
			radiationSource.setMaxEmittedMilliremsPerSecond(intensity);

			WhyAmIGlowing.LOGGER.debug("Added block of blockstate " + blockState.getBlock().getDescriptionId() + " as radiation source of intensity " + intensity + " rem/s, which matches tag/block " + blockKey + " in config.");

			return radiationSource;
		}

		return null;
	}

	@SuppressWarnings("unused")
	@Mod.EventBusSubscriber(modid = WhyAmIGlowing.MODID)
	private static class EventHandler {

		@SubscribeEvent
		public static void attachCapabilitiesToItemStack(final AttachCapabilitiesEvent<ItemStack> event) {
			IRadiationSource radiationSource = getForAttach(event.getObject());
			if (radiationSource != null) {
				event.addCapability(ID, createProvider(radiationSource));
			}
		}

		@SubscribeEvent
		public static void attachCapabilitiesToEntity(final AttachCapabilitiesEvent<Entity> event) {
			final IRadiationSource radiationSource = new RadiationSource(event.getObject());

			if (radiationSource != null) {
				event.addCapability(ID, createProvider(radiationSource));
			}
		}
	}
}
