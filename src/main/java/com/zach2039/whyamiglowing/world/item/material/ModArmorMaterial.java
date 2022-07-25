package com.zach2039.whyamiglowing.world.item.material;

import com.zach2039.whyamiglowing.WhyAmIGlowing;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.util.Lazy;

import java.util.function.Supplier;

public enum ModArmorMaterial implements ArmorMaterial {
	HAZMAT_MK_1("hazmat_mk_1", 5, new int[]{1, 2, 3, 1}, 15, SoundEvents.ARMOR_EQUIP_LEATHER, 0.0F, 0.0F, () -> {
		return Ingredient.of(new ItemLike[]{Items.LEATHER});
	}),
	HAZMAT_MK_2("hazmat_mk_2", 15, new int[]{2, 5, 6, 2}, 9, SoundEvents.ARMOR_EQUIP_IRON, 0.0F, 0.0F, () -> {
		return Ingredient.of(new ItemLike[]{Items.IRON_INGOT});
	}),
	;

	/**
	 * The base max damage for each armour slot.
	 */
	private static final int[] MAX_DAMAGE_ARRAY = new int[]{13, 15, 16, 11};

	/**
	 * The name of the armour material.
	 */
	private final String name;

	/**
	 * The maximum damage factor of the armour material, this is the item damage (how much it can absorb before it breaks).
	 */
	private final int maxDamageFactor;

	/**
	 * The damage reduction (each 1 point is half a shield on the GUI) of each piece of armour (helmet, plate, legs and boots).
	 */
	private final int[] damageReductionAmountArray;

	/**
	 * The enchantability factor of the armour material.
	 */
	private final int enchantability;

	/**
	 * The sound played when armour of the armour material is equipped.
	 */
	private final SoundEvent soundEvent;

	/**
	 * The armour toughness value of the armour material.
	 */
	private final float toughness;

	/**
	 * The percentage of knockback resistance provided by armor of the material.
	 */
	private final float knockbackResistance;

	/**
	 * The repair material of the armour material.
	 */
	private final Supplier<Ingredient> repairMaterial;

	ModArmorMaterial(
			final String name, final int maxDamageFactor, final int[] damageReductionAmountArray,
			final int enchantability, final SoundEvent soundEvent, final float toughness,
			final float knockbackResistance, final Supplier<Ingredient> repairMaterial
	) {
		this.name = new ResourceLocation(WhyAmIGlowing.MODID, name).toString();
		this.maxDamageFactor = maxDamageFactor;
		this.damageReductionAmountArray = damageReductionAmountArray;
		this.enchantability = enchantability;
		this.soundEvent = soundEvent;
		this.toughness = toughness;
		this.knockbackResistance = knockbackResistance;
		this.repairMaterial = Lazy.of(repairMaterial);
	}

	@Override
	public int getDurabilityForSlot(final EquipmentSlot slotIn) {
		return MAX_DAMAGE_ARRAY[slotIn.getIndex()] * maxDamageFactor;
	}

	@Override
	public int getDefenseForSlot(final EquipmentSlot slotIn) {
		return damageReductionAmountArray[slotIn.getIndex()];
	}

	@Override
	public int getEnchantmentValue() {
		return enchantability;
	}

	@Override
	public SoundEvent getEquipSound() {
		return soundEvent;
	}

	@Override
	public Ingredient getRepairIngredient() {
		return repairMaterial.get();
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public float getToughness() {
		return toughness;
	}

	@Override
	public float getKnockbackResistance() {
		return knockbackResistance;
	}
}
