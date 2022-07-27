package com.zach2039.whyamiglowing.event;

import com.zach2039.whyamiglowing.WhyAmIGlowing;
import com.zach2039.whyamiglowing.api.capability.chunkradiation.IChunkRadiation;
import com.zach2039.whyamiglowing.api.capability.radiation.IRadiation;
import com.zach2039.whyamiglowing.api.capability.radiationsource.IRadiationSource;
import com.zach2039.whyamiglowing.capability.chunkradiation.ChunkRadiationCapability;
import com.zach2039.whyamiglowing.capability.radiationsource.RadiationSource;
import com.zach2039.whyamiglowing.capability.radiationsource.RadiationSourceCapability;
import com.zach2039.whyamiglowing.core.RadiationHelper;
import com.zach2039.whyamiglowing.text.WhyAmIGlowingLang;
import com.zach2039.whyamiglowing.util.CapabilityNotPresentException;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.event.level.PistonEvent;
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

	@SubscribeEvent
	public static void onPistonMovedPre(final PistonEvent.Pre event) {
		Level level = (Level) event.getLevel();
		if (!level.isClientSide) {
			BlockPos oldPos;
			BlockPos newPos;
			if (event.getPistonMoveType() == PistonEvent.PistonMoveType.EXTEND) {
				oldPos = event.getFaceOffsetPos();
				newPos = oldPos.relative(event.getDirection());
			} else {
				oldPos = event.getFaceOffsetPos().relative(event.getDirection());
				newPos = event.getFaceOffsetPos();
			}
			IRadiationSource radSource = RadiationHelper.getRadiationSourceFromChunk(level, oldPos);
			WhyAmIGlowing.LOGGER.info("type: " + event.getPistonMoveType());
			WhyAmIGlowing.LOGGER.info("blockstate old: " + level.getBlockState(oldPos) + " / " + oldPos);
			if (radSource != null) { // Shift radiation source
				WhyAmIGlowing.LOGGER.info("blockstate new: " + level.getBlockState(newPos) + " / " + newPos);
				RadiationHelper.getRadiationSourcesFromChunk(level, oldPos).remove(oldPos);
				RadiationHelper.getRadiationSourcesFromChunk(level, newPos).put(newPos, (RadiationSource) radSource);
			}
		}
	}
}
