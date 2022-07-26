package com.zach2039.whyamiglowing.util;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;

import java.lang.reflect.Method;

public class EntityUtil {

	public static boolean isInRain(final Entity entity) {
		BlockPos blockpos = entity.blockPosition();
		return entity.level.isRainingAt(blockpos) || entity.level.isRainingAt(new BlockPos(blockpos.getX(), entity.getBoundingBox().maxY, blockpos.getZ()));
	}
}
