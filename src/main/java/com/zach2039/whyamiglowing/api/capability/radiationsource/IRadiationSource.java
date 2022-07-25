package com.zach2039.whyamiglowing.api.capability.radiationsource;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public interface IRadiationSource {
	ItemStack getItemStack();

	BlockState getBlockState(final Level level);

	BlockPos getBlockPos();
	void setBlockPos(BlockPos blockPos);
	float getEmittedMilliremsPerSecond();
	void setEmittedMilliremsPerSecond(float emittedMilliremsPerSecond);
	float getMaxEmittedMilliremsPerSecond();
	void setMaxEmittedMilliremsPerSecond(float maxEmittedMilliremsPerSecond);
	float irradiateLivingEntity(Level level, Entity sourceEntity, LivingEntity livingEntity);
	float irradiateLivingEntity(Level level, BlockPos blockPos, LivingEntity livingEntity);
}
