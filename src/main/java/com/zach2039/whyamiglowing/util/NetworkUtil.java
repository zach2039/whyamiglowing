package com.zach2039.whyamiglowing.util;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class NetworkUtil {
	@Nullable
	public static Entity getEntityByID(final Level level, final int entityID) {
		return level.getEntity(entityID);
	}
}
