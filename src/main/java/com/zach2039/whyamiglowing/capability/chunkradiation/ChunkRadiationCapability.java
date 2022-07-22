package com.zach2039.whyamiglowing.capability.chunkradiation;

import com.zach2039.whyamiglowing.WhyAmIGlowing;
import com.zach2039.whyamiglowing.api.capability.chunkradiation.IChunkRadiation;
import com.zach2039.whyamiglowing.api.capability.radiation.IRadiation;
import com.zach2039.whyamiglowing.capability.SerializableCapabilityProvider;
import com.zach2039.whyamiglowing.capability.radiation.Radiation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class ChunkRadiationCapability {
	public static final Capability<IChunkRadiation> CHUNK_RADIATION_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {});

	public static final ResourceLocation ID = new ResourceLocation(WhyAmIGlowing.MODID, "chunk_radiation");

	public static void register(final RegisterCapabilitiesEvent event) {
		event.register(IChunkRadiation.class);
	}

	public static LazyOptional<IChunkRadiation> getChunkRadiation(final LevelChunk levelChunk) {
		return levelChunk.getCapability(CHUNK_RADIATION_CAPABILITY);
	}

	public static ICapabilityProvider createProvider(final IChunkRadiation chunkRadiation) {
		return new SerializableCapabilityProvider<>(CHUNK_RADIATION_CAPABILITY, chunkRadiation);
	}

	@SuppressWarnings("unused")
	@Mod.EventBusSubscriber(modid = WhyAmIGlowing.MODID)
	private static class EventHandler {

		@SubscribeEvent
		public static void attachCapabilities(final AttachCapabilitiesEvent<LevelChunk> event) {
			final ChunkRadiation chunkRadiation = new ChunkRadiation(event.getObject());
			event.addCapability(ID, createProvider(chunkRadiation));
		}
	}
}
