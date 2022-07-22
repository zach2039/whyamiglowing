package com.zach2039.whyamiglowing.event;

import com.mojang.datafixers.util.Pair;
import com.zach2039.whyamiglowing.WhyAmIGlowing;
import com.zach2039.whyamiglowing.api.capability.chunkradiation.IChunkRadiation;
import com.zach2039.whyamiglowing.api.capability.radiationsource.IRadiationSource;
import com.zach2039.whyamiglowing.capability.chunkradiation.ChunkRadiationCapability;
import com.zach2039.whyamiglowing.capability.radiationsource.RadiationSource;
import com.zach2039.whyamiglowing.capability.radiationsource.RadiationSourceCapability;
import com.zach2039.whyamiglowing.core.RadiationHelper;
import com.zach2039.whyamiglowing.core.RadiationManager;
import com.zach2039.whyamiglowing.util.CapabilityNotPresentException;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = WhyAmIGlowing.MODID)
public class BlockEventHandler {
	@SubscribeEvent
	public static void onBlockPlaced(final BlockEvent.EntityPlaceEvent event) {
		IRadiationSource radiationSource = RadiationSourceCapability.getForAttach(event.getState());

		if (radiationSource != null) {
			LevelChunk levelChunk = (LevelChunk) event.getLevel().getChunk(event.getPos());
			LazyOptional<IChunkRadiation> chunkRadiationOptional = ChunkRadiationCapability.getChunkRadiation(levelChunk);

			if (!chunkRadiationOptional.isPresent())
				return;

			IChunkRadiation chunkRadiation = chunkRadiationOptional.orElseThrow(CapabilityNotPresentException::new);

			if (radiationSource instanceof RadiationSource radSourceImpl) {
				radiationSource.setBlockPos(event.getPos());
				chunkRadiation.getBlockSources().put(event.getPos(), radSourceImpl);
			}
		}
	}

	@SubscribeEvent
	public static void onBlockRemoved(final BlockEvent.BreakEvent event) {
		IRadiationSource radiationSource = RadiationSourceCapability.getForAttach(event.getState());

		if (radiationSource != null) {
			LevelChunk levelChunk = (LevelChunk) event.getLevel().getChunk(event.getPos());
			LazyOptional<IChunkRadiation> chunkRadiationOptional = ChunkRadiationCapability.getChunkRadiation(levelChunk);

			if (!chunkRadiationOptional.isPresent())
				return;

			IChunkRadiation chunkRadiation = chunkRadiationOptional.orElseThrow(CapabilityNotPresentException::new);

			chunkRadiation.getBlockSources().remove(event.getPos());
		}
	}
}
