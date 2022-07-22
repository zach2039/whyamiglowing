package com.zach2039.whyamiglowing.world.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.state.BlockState;

public class FalloutLayerBlock extends SnowLayerBlock {

	public FalloutLayerBlock(Properties arg) {
		super(arg);
	}

	@Override
	public void randomTick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
		if (randomSource.nextFloat() < 0.05f) {
			if (blockState.getValue(LAYERS) > 1) {
				blockState.setValue(LAYERS, blockState.getValue(LAYERS) - 1);
			} else {
				serverLevel.removeBlock(blockPos, false);
			}
		}
	}
}
