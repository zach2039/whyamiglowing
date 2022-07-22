package com.zach2039.whyamiglowing.util;

import com.zach2039.whyamiglowing.WhyAmIGlowing;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;
import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChunkUtil {

	public static List<LevelChunk> getChunksAround(final Level level, final BlockPos positionBase, final int chunkRadius) {
		if (!level.isLoaded(positionBase))
			return Arrays.asList(); // Return nothing if the base chunk isn't loaded

		LevelChunk baseChunk = level.getChunkAt(positionBase);
		ArrayList<LevelChunk> collectedChunks = new ArrayList<>();

		collectedChunks.add(baseChunk);

		ChunkPos baseChunkPos = baseChunk.getPos();
		for (int i = -chunkRadius + baseChunkPos.x; i < chunkRadius + baseChunkPos.x; i++) {
			for (int j = -chunkRadius + baseChunkPos.z; j < chunkRadius + baseChunkPos.z; j++) {
				ChunkPos newChunkPos = new ChunkPos(i, j);

				if (newChunkPos.x == baseChunkPos.x && newChunkPos.z == baseChunkPos.z) {
					continue; // Dont add duplicate of base chunk
				}

				LevelChunk levelChunk = level.getChunk(i, j);

				if (levelChunk != null || !level.isLoaded(newChunkPos.getMiddleBlockPosition(64))) { // Check chunk before adding!
					collectedChunks.add(levelChunk);
				}
			}
		}

		return collectedChunks;
	}
}
