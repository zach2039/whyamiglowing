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
					.pattern("gGg")
					.pattern("gRB")
					.pattern("gCL")
					.define('G', Ingredient.of(Blocks.GLASS))
					.define('g', Ingredient.of(GOLD_INGOT))
					.define('C', Ingredient.of(Blocks.COMPARATOR))
					.define('L', Ingredient.of(LEVER))
					.define('B', Ingredient.of(Blocks.STONE_BUTTON))
					.define('R', Ingredient.of(REDSTONE))
					.unlockedBy("has_glass", has(Blocks.GLASS))
					.unlockedBy("has_gold_ingot", has(GOLD_INGOT))
					.unlockedBy("has_comparator", has(Blocks.COMPARATOR))
					.unlockedBy("has_lever", has(LEVER))
					.unlockedBy("has_stone_button", has(Blocks.STONE_BUTTON))
					.unlockedBy("has_redstone", has(REDSTONE))
					.save(recipeConsumer, new ResourceLocation(WhyAmIGlowing.MODID, "geiger_counter"));
		}
	}

	@Override
	public String getName() {
		return "WhyAmIGlowing Recipes";
	}
}
