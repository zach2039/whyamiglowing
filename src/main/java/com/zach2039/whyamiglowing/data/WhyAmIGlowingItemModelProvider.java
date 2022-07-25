package com.zach2039.whyamiglowing.data;

import com.zach2039.whyamiglowing.WhyAmIGlowing;
import com.zach2039.whyamiglowing.init.ModItems;
import com.zach2039.whyamiglowing.util.RegistryUtil;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.util.Lazy;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.IntStream;

/**
 * Generates this mod's item models.
 *
 * @author Choonster
 *
 * With additions/edits by:
 * @author zach2039
 */
public class WhyAmIGlowingItemModelProvider extends ItemModelProvider {
	private static final String LAYER_0 = "layer0";

	/**
	 * A model that extends item/generated and uses the same transforms as the Vanilla bow.
	 */
	private final Supplier<ModelFile> simpleModel = Lazy.of(() ->
			withGeneratedParent("simple_model")
					.transforms()

					.transform(ItemTransforms.TransformType.THIRD_PERSON_RIGHT_HAND)
					.rotation(-80, 260, -40)
					.translation(-1, -2, 2.5f)
					.scale(0.9f, 0.9f, 0.9f)
					.end()

					.transform(ItemTransforms.TransformType.THIRD_PERSON_LEFT_HAND)
					.rotation(-80, -280, 40)
					.translation(-1, -2, 2.5f)
					.scale(0.9f, 0.9f, 0.9f)
					.end()

					.transform(ItemTransforms.TransformType.FIRST_PERSON_RIGHT_HAND)
					.rotation(0, -90, 25)
					.translation(1.13f, 3.2f, 1.13f)
					.scale(0.68f, 0.68f, 0.68f)
					.end()

					.transform(ItemTransforms.TransformType.FIRST_PERSON_LEFT_HAND)
					.rotation(0, 90, -25)
					.translation(1.13f, 3.2f, 1.13f)
					.scale(0.68f, 0.68f, 0.68f)
					.end()

					.end()
	);


	public WhyAmIGlowingItemModelProvider(final DataGenerator generator, final ExistingFileHelper existingFileHelper) {
		super(generator, WhyAmIGlowing.MODID, existingFileHelper);
	}

	/**
	 * Gets a name for this provider, to use in logging.
	 */
	@Override
	public String getName() {
		return "WhyAmIGlowingItemModels";
	}

	@Override
	protected void registerModels() {
		// Equipment
		withGeneratedParentAndDefaultTexture(ModItems.GEIGER_COUNTER.get());
		withGeneratedParentAndDefaultTexture(ModItems.HAZMAT_RESPIRATOR_MK_1.get());
		withGeneratedParentAndDefaultTexture(ModItems.HAZMAT_SUIT_TOP_MK_1.get());
		withGeneratedParentAndDefaultTexture(ModItems.HAZMAT_SUIT_BOTTOM_MK_1.get());
		withGeneratedParentAndDefaultTexture(ModItems.HAZMAT_BOOTS_MK_1.get());
		withGeneratedParentAndDefaultTexture(ModItems.HAZMAT_RESPIRATOR_MK_2.get());
		withGeneratedParentAndDefaultTexture(ModItems.HAZMAT_SUIT_TOP_MK_2.get());
		withGeneratedParentAndDefaultTexture(ModItems.HAZMAT_SUIT_BOTTOM_MK_2.get());
		withGeneratedParentAndDefaultTexture(ModItems.HAZMAT_BOOTS_MK_2.get());
		// Consumables
		withGeneratedParentAndDefaultTexture(ModItems.RAD_X.get());
		withGeneratedParentAndDefaultTexture(ModItems.RAD_AWAY.get());
	}


	private ResourceLocation key(final Item item) {
		return RegistryUtil.getKey(item);
	}

	private String name(final Item item) {
		return key(item).getPath();
	}

	private ResourceLocation itemTexture(final Item item) {
		final ResourceLocation name = key(item);
		return new ResourceLocation(name.getNamespace(), ITEM_FOLDER + "/" + name.getPath());
	}

	private ItemModelBuilder withExistingParent(final Item item, final Item modelItem) {
		return withExistingParent(name(item), key(modelItem));
	}

	private ItemModelBuilder withGeneratedParentAndDefaultTexture(final @NotNull Item item) {
		return withGeneratedParent(name(item))
				.texture(LAYER_0, itemTexture(item));
	}

	private ItemModelBuilder withGeneratedParent(final String name) {
		return withExistingParent(name, mcLoc("generated"));
	}

	private ItemModelBuilder withSimpleParentAndDefaultTexture(final Item item) {
		return withSimpleParent(item, itemTexture(item));
	}

	private ItemModelBuilder withSimpleParent(final Item item, final ResourceLocation texture) {
		return withSimpleParent(item, texture.toString());
	}

	private ItemModelBuilder withSimpleParent(final Item item, final String texture) {
		return withSimpleParent(name(item))
				.texture(LAYER_0, texture);
	}

	private ItemModelBuilder withSimpleParent(final String name) {
		return getBuilder(name)
				.parent(simpleModel.get());
	}

	private void bowItem(final Item item) {
		// Create the parent model
		final ItemModelBuilder bow = withSimpleParent(item, itemTexture(Items.BOW));

		// Create three child models
		final List<ItemModelBuilder> bowPullingModels = IntStream.range(0, 3)
				.mapToObj(index ->
						getBuilder(name(item) + "_" + index)
								.parent(bow)
								.texture(LAYER_0, itemTexture(Items.BOW) + "_pulling_" + index)
				)
				.toList();

		// Add the child models as overrides that display when the bow is pulled back >= 0%, >= 65% and >= 90% respectively
		bow
				.override()
				.predicate(mcLoc("pulling"), 1)
				.model(bowPullingModels.get(0))
				.end()

				.override()
				.predicate(mcLoc("pulling"), 1)
				.predicate(mcLoc("pull"), 0.65f)
				.model(bowPullingModels.get(1))
				.end()

				.override()
				.predicate(mcLoc("pulling"), 1)
				.predicate(mcLoc("pull"), 0.9f)
				.model(bowPullingModels.get(2))
				.end();
	}

	private void spawnEggItem(final Item item) {
		withExistingParent(name(item), mcLoc("template_spawn_egg"));
	}
}
