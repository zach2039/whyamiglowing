package com.zach2039.whyamiglowing.init;

import com.zach2039.whyamiglowing.WhyAmIGlowing;
import com.zach2039.whyamiglowing.world.block.FalloutLayerBlock;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {
	private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, WhyAmIGlowing.MODID);
	private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, WhyAmIGlowing.MODID);

	private static boolean isInitialized;

	// Debris
	public static final RegistryObject<FalloutLayerBlock> FALLOUT = registerBlock("fallout",
			() -> new FalloutLayerBlock(BlockBehaviour.Properties.of(Material.TOP_SNOW).randomTicks().strength(0.1F).requiresCorrectToolForDrops().sound(SoundType.SNOW).isViewBlocking(
					(arg, arg2, arg3) -> arg.getValue(FalloutLayerBlock.LAYERS) >= 8
			)));

	public static void initialize(final IEventBus modEventBus) {
		if (isInitialized) {
			throw new IllegalStateException("Already initialized");
		}

		BLOCKS.register(modEventBus);
		ITEMS.register(modEventBus);

		isInitialized = true;
	}

	/**
	 * Registers a block with a standard {@link BlockItem} as its block item.
	 * @author Choonster
	 *
	 * @param name         The registry name of the block
	 * @param blockFactory The factory used to create the block
	 * @param <BLOCK>      The block type
	 * @return A RegistryObject reference to the block
	 */
	private static <BLOCK extends Block> RegistryObject<BLOCK> registerBlock(final String name, final Supplier<BLOCK> blockFactory) {
		return registerBlock(name, blockFactory, block -> new BlockItem(block, defaultItemProperties()));
	}

	/**
	 * Registers a block and its block item.
	 * @author Choonster
	 *
	 * @param name         The registry name of the block
	 * @param blockFactory The factory used to create the block
	 * @param itemFactory  The factory used to create the block item
	 * @param <BLOCK>      The block type
	 * @return A RegistryObject reference to the block
	 */
	private static <BLOCK extends Block> RegistryObject<BLOCK> registerBlock(final String name, final Supplier<BLOCK> blockFactory, final IBlockItemFactory<BLOCK> itemFactory) {
		final RegistryObject<BLOCK> block = BLOCKS.register(name, blockFactory);

		ITEMS.register(name, () -> itemFactory.create(block.get()));

		return block;
	}

	/**
	 * Registers a block without a blockItem.
	 * @author Choonster
	 *
	 * @param name         The registry name of the block
	 * @param blockFactory The factory used to create the block
	 * @param <BLOCK>      The block type
	 * @return A RegistryObject reference to the block
	 */
	private static <BLOCK extends Block> RegistryObject<BLOCK> registerItemlessBlock(final String name, final Supplier<BLOCK> blockFactory) {
		final RegistryObject<BLOCK> block = BLOCKS.register(name, blockFactory);

		return block;
	}

	/**
	 * Gets an {@link Item.Properties} instance with the {@link CreativeModeTab} set to {@link WhyAmIGlowing#CREATIVE_MODE_TAB}.
	 * @author Choonster
	 *
	 * @return The item properties
	 */
	private static Item.Properties defaultItemProperties() {
		return new Item.Properties().tab(WhyAmIGlowing.CREATIVE_MODE_TAB);
	}

	/**
	 * A factory function used to create block items.
	 * @author Choonster
	 *
	 * @param <BLOCK> The block type
	 */
	@FunctionalInterface
	private interface IBlockItemFactory<BLOCK extends Block> {
		Item create(BLOCK block);
	}
}
