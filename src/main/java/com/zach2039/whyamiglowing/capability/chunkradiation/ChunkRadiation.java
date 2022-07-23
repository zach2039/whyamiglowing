package com.zach2039.whyamiglowing.capability.chunkradiation;

import com.zach2039.whyamiglowing.WhyAmIGlowing;
import com.zach2039.whyamiglowing.api.capability.chunkradiation.IChunkRadiation;
import com.zach2039.whyamiglowing.capability.radiationsource.RadiationSource;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.HashMap;
import java.util.Map;

public class ChunkRadiation implements IChunkRadiation, INBTSerializable<CompoundTag> {

	private Map<BlockPos, RadiationSource> radiationSources = new HashMap<>();

	private ChunkAccess chunkAccess;

	public ChunkRadiation(ChunkAccess chunkAccess) {
		this.chunkAccess = chunkAccess;
	}

	@Override
	public Map<BlockPos, RadiationSource> getBlockSources() {
		return this.radiationSources;
	}

	@Override
	public CompoundTag serializeNBT() {
		CompoundTag tag = new CompoundTag();
		ListTag listTag = new ListTag();

		for (BlockPos blockPos : this.radiationSources.keySet()) {
			RadiationSource radiationSource = this.radiationSources.get(blockPos);
			listTag.add(radiationSource.serializeNBT());
			WhyAmIGlowing.LOGGER.debug("Saved radiation source to disk: " + blockPos + "/" + radiationSource);
		}
		tag.put("radiationSources", listTag);


		return tag;
	}

	@Override
	public void deserializeNBT(CompoundTag tag) {
		ListTag listTag = tag.getList("radiationSources", Tag.TAG_COMPOUND);

		for (Tag tagItem : listTag) {
			RadiationSource source = new RadiationSource();
			source.deserializeNBT((CompoundTag) tagItem);
			this.radiationSources.put(source.getBlockPos(), source);
			WhyAmIGlowing.LOGGER.debug("Loaded radiation source from disk: " + source.getBlockPos() + "/" + source);
		}
	}
}
