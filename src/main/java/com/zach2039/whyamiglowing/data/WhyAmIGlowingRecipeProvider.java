package com.zach2039.whyamiglowing.data;

import com.zach2039.whyamiglowing.WhyAmIGlowing;
import com.zach2039.whyamiglowing.init.ModItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;

import java.util.function.Consumer;

import static net.minecraft.world.item.Items.*;

public class WhyAmIGlowingRecipeProvider extends RecipeProvider {

	public WhyAmIGlowingRecipeProvider(final DataGenerator dataGenerator) {
		super(dataGenerator);
	}

	@Override
	protected void buildCraftingRecipes(final Consumer<FinishedRecipe> recipeConsumer) {
		// Geiger Counter
		{
			ShapedRecipeBuilder.shaped(ModItems.GEIGER_COUNTER.get())
					.pattern("gCg")
					.pattern("gRB")
					.pattern("gcL")
					.define('C', Ingredient.of(COMPASS))
					.define('g', Ingredient.of(GOLD_INGOT))
					.define('c', Ingredient.of(Blocks.COMPARATOR))
					.define('L', Ingredient.of(LEVER))
					.define('B', Ingredient.of(Blocks.STONE_BUTTON))
					.define('R', Ingredient.of(REDSTONE))
					.unlockedBy("has_compass", has(COMPASS))
					.unlockedBy("has_gold_ingot", has(GOLD_INGOT))
					.unlockedBy("has_comparator", has(Blocks.COMPARATOR))
					.unlockedBy("has_lever", has(LEVER))
					.unlockedBy("has_stone_button", has(Blocks.STONE_BUTTON))
					.unlockedBy("has_redstone", has(REDSTONE))
					.save(recipeConsumer, new ResourceLocation(WhyAmIGlowing.MODID, "geiger_counter"));
		}

		// Rad-Away
		{
			ShapedRecipeBuilder.shaped(ModItems.RAD_AWAY.get())
					.pattern(" T ")
					.pattern("MBG")
					.pattern(" P ")
					.define('B', Ingredient.of(HONEY_BOTTLE))
					.define('T', Ingredient.of(GHAST_TEAR))
					.define('M', Ingredient.of(GLISTERING_MELON_SLICE))
					.define('G', Ingredient.of(GOLDEN_APPLE))
					.define('P', Ingredient.of(PRISMARINE_CRYSTALS))
					.unlockedBy("has_honey_bottle", has(HONEY_BOTTLE))
					.unlockedBy("has_ghast_tear", has(GHAST_TEAR))
					.unlockedBy("has_glistering_melon_slice", has(GLISTERING_MELON_SLICE))
					.unlockedBy("has_golden_apple", has(GOLDEN_APPLE))
					.unlockedBy("has_prismarine_crystals", has(PRISMARINE_CRYSTALS))
					.save(recipeConsumer, new ResourceLocation(WhyAmIGlowing.MODID, "rad_away"));
		}

		// Rad-X
		{
			ShapedRecipeBuilder.shaped(ModItems.RAD_X.get())
					.pattern(" O ")
					.pattern("MBG")
					.pattern(" P ")
					.define('B', Ingredient.of(HONEY_BOTTLE))
					.define('O', Ingredient.of(RAW_GOLD))
					.define('M', Ingredient.of(MAGMA_CREAM))
					.define('G', Ingredient.of(GLOWSTONE_DUST))
					.define('P', Ingredient.of(PRISMARINE_CRYSTALS))
					.unlockedBy("has_honey_bottle", has(HONEY_BOTTLE))
					.unlockedBy("has_gold_ore", has(GOLD_ORE))
					.unlockedBy("has_magma_cream", has(MAGMA_CREAM))
					.unlockedBy("has_glowstone_dust", has(GLOWSTONE_DUST))
					.unlockedBy("has_prismarine_crystals", has(PRISMARINE_CRYSTALS))
					.save(recipeConsumer, new ResourceLocation(WhyAmIGlowing.MODID, "rad_x"));
		}
	}

	@Override
	public String getName() {
		return "WhyAmIGlowing Recipes";
	}
}
