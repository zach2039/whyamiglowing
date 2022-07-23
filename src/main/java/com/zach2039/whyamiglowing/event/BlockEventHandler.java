package com.zach2039.whyamiglowing.event;

import com.zach2039.whyamiglowing.WhyAmIGlowing;
import com.zach2039.whyamiglowing.api.capability.chunkradiation.IChunkRadiation;
import com.zach2039.whyamiglowing.api.capability.radiationsource.IRadiationSource;
import com.zach2039.whyamiglowing.capability.chunkradiation.ChunkRadiationCapability;
import com.zach2039.whyamiglowing.capability.radiationsource.RadiationSource;
import com.zach2039.whyamiglowing.capability.radiationsource.RadiationSourceCapability;
import com.zach2039.whyamiglowing.util.CapabilityNotPresentException;
import dev.architectury.event.events.common.InteractionEvent;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
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

	@SubscribeEvent
	public static void onBlockInteractUse(final PlayerInteractEvent.RightClickBlock event) {
		BlockState blockState = event.getLevel().getBlockState(event.getPos());
		IRadiationSource radiationSource = RadiationSourceCapability.getForAttach(blockState);

		if (radiationSource != null) {
			LevelChunk levelChunk = (LevelChunk) event.getLevel().getChunk(event.getPos());
			LazyOptional<IChunkRadiation> chunkRadiationOptional = ChunkRadiationCapability.getChunkRadiation(levelChunk);

			if (!chunkRadiationOptional.isPresent())
				return;

			IChunkRadiation chunkRadiation = chunkRadiationOptional.orElseThrow(CapabilityNotPresentException::new);

			// If block should have radiation source, but doesn't then give it one! Solves cheeky already placed sources not giving radiation.
			if (!chunkRadiation.getBlockSources().containsKey(event.getPos())) {
				if (radiationSource instanceof RadiationSource radSourceImpl) {
					radiationSource.setBlockPos(event.getPos());
					chunkRadiation.getBlockSources().put(event.getPos(), radSourceImpl);
				}
			}
		}
	}
}
