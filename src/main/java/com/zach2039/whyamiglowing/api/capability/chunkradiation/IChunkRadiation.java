package com.zach2039.whyamiglowing.api.capability.chunkradiation;

import com.zach2039.whyamiglowing.capability.radiationsource.RadiationSource;
import net.minecraft.core.BlockPos;

import java.util.Map;

public interface IChunkRadiation {

	Map<BlockPos, RadiationSource> getBlockSources();

}
