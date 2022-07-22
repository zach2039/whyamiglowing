package com.zach2039.whyamiglowing.capability.radiationsource;

import com.google.common.collect.ArrayListMultimap;
import com.zach2039.whyamiglowing.WhyAmIGlowing;
import com.zach2039.whyamiglowing.api.capability.radiation.IRadiation;
import com.zach2039.whyamiglowing.api.capability.radiationsource.IRadiationSource;
import com.zach2039.whyamiglowing.capability.radiation.RadiationCapability;
import com.zach2039.whyamiglowing.core.RadiationHelper;
import com.zach2039.whyamiglowing.init.ModTags;
import com.zach2039.whyamiglowing.util.CapabilityNotPresentException;
import com.zach2039.whyamiglowing.util.RegistryUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.registries.ForgeRegistries;
import org.checkerframework.checker.units.qual.C;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class RadiationSource implements IRadiationSource, INBTSerializable<CompoundTag> {

	private ItemStack itemStack = ItemStack.EMPTY;

	@Nullable
	private Block block = null;

	@Nullable
	private BlockPos blockPos = null;

	private float emittedMilliremsPerSecond = 0f;
	private float maxEmittedMilliremsPerSecond = 0f;

	public RadiationSource(@Nullable final ItemStack itemStack) {
		this.itemStack = itemStack;
	}

	public RadiationSource(@Nullable final Entity entity) {}

	public RadiationSource(@Nullable final Block block, final BlockPos blockPos) {
		this.block = block;
		this.blockPos = blockPos;
	}

	public RadiationSource() {}

	@Nullable
	@Override
	public ItemStack getItemStack() {
		return this.itemStack;
	}

	@Nullable
	@Override
	public BlockState getBlockState(final Level level) {
		return level.getBlockState(this.blockPos);
	}

	@Nullable
	@Override
	public BlockPos getBlockPos() {
		return this.blockPos;
	}

	@Override
	public void setBlockPos(final BlockPos blockPos) {
		this.blockPos = blockPos;
	}

	@Override
	public float getEmittedMilliremsPerSecond() {
		return this.emittedMilliremsPerSecond;
	}

	@Override
	public void setEmittedMilliremsPerSecond(float emittedMilliremsPerSecond) {
		this.emittedMilliremsPerSecond = RadiationHelper.getAdjustedFloat(emittedMilliremsPerSecond);
	}

	@Override
	public float getMaxEmittedMilliremsPerSecond() {
		return this.maxEmittedMilliremsPerSecond;
	}

	@Override
	public void setMaxEmittedMilliremsPerSecond(float maxEmittedMilliremsPerSecond) {
		this.maxEmittedMilliremsPerSecond = RadiationHelper.getAdjustedFloat(maxEmittedMilliremsPerSecond);
	}

	@Override
	public void irradiateLivingEntity(final Level level, final BlockPos blockPos, final LivingEntity livingEntity) {
		BlockPos posEntity = livingEntity.blockPosition();
		BlockPos posSource = blockPos;
		double distanceToSource = posSource.distSqr(posEntity);

		if (livingEntity.getCapability(RadiationCapability.RADIATION_CAPABILITY).isPresent()) {
			IRadiation radiation = livingEntity.getCapability(RadiationCapability.RADIATION_CAPABILITY).orElseThrow(CapabilityNotPresentException::new);
			float intensityAt1Meter = this.emittedMilliremsPerSecond;
			float intensityAt1MeterMilliremsPerHour = RadiationHelper.convertPerSecondToPerHour(intensityAt1Meter);
			float intensityAtEntity = (float) Math.min(intensityAt1Meter, (intensityAt1Meter * (1f / Math.pow(distanceToSource, 2))));

			// Get blocks between entity and source. and reduce exposure based on list
			List<BlockState> blocksBetween = new ArrayList<>();
			float reductionFactor = 0.0f;
			if (intensityAt1MeterMilliremsPerHour > 5f) { // Dont try to block anything under 5mrem/h
				blocksBetween = RadiationHelper.getShieldBlocksBetween(level, new Vec3(blockPos.getX() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() + 0.5), livingEntity.position().add(0f, livingEntity.getEyeHeight() / 2f, 0.5f));
				for (BlockState blockstate : blocksBetween) {
					if (blockstate.is(ModTags.Blocks.HEAVY_RADIATION_SHIELDING)) {
						reductionFactor += RadiationHelper.getHeavyRadiationShieldingReductionFactor();
						continue;
					}
					if (blockstate.is(ModTags.Blocks.MEDIUM_RADIATION_SHIELDING)) {
						reductionFactor += RadiationHelper.getMediumRadiationShieldingReductionFactor();
						continue;
					}
					if (blockstate.is(ModTags.Blocks.LIGHT_RADIATION_SHIELDING)) {
						reductionFactor += RadiationHelper.getLightRadiationShieldingReductionFactor();
						continue;
					}
				}
			}

			// Get actual exposure at distance
			float intensityAtEntityReduced = intensityAtEntity;
			intensityAtEntityReduced = RadiationHelper.getAdjustedFloat(intensityAtEntity * (1f / (1f + reductionFactor)));

			radiation.increaseCurrentExposureMilliremsPerSecond(intensityAtEntityReduced);
		}
	}

	@Override
	public void irradiateLivingEntity(final Level level, final Entity sourceEntity, final LivingEntity livingEntity) {
		irradiateLivingEntity(level, sourceEntity.blockPosition(), livingEntity);
	}

	@Override
	public CompoundTag serializeNBT() {
		CompoundTag tag = new CompoundTag();

		tag.putFloat("emittedMilliremsPerSecond", this.emittedMilliremsPerSecond);
		tag.putFloat("maxEmittedMilliremsPerSecond", this.maxEmittedMilliremsPerSecond);
		tag.put("itemStack", this.itemStack.serializeNBT());
		if (this.block != null) {
			tag.putString("block", RegistryUtil.getKey(this.block).toString());
		}
		if (this.blockPos != null) {
			tag.putInt("blockPosX", this.blockPos.getX());
			tag.putInt("blockPosY", this.blockPos.getY());
			tag.putInt("blockPosZ", this.blockPos.getZ());
		}

		return tag;
	}

	@Override
	public void deserializeNBT(CompoundTag tag) {
		this.emittedMilliremsPerSecond = tag.getFloat("emittedMilliremsPerSecond");
		this.maxEmittedMilliremsPerSecond = tag.getFloat("maxEmittedMilliremsPerSecond");
		this.itemStack = ItemStack.of(tag.getCompound("itemStack"));
		if (tag.contains("block")) {
			Block block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(tag.getString("block")));
			if (block != null) {
				this.block = block;
			}
		}
		if (tag.contains("blockPosX") && tag.contains("blockPosY") && tag.contains("blockPosZ")) {
			this.blockPos = new BlockPos(
					tag.getInt("blockPosX"),
					tag.getInt("blockPosY"),
					tag.getInt("blockPosZ")
				);
		}
	}
}
