package com.zach2039.whyamiglowing.init;

import com.zach2039.whyamiglowing.WhyAmIGlowing;
import com.zach2039.whyamiglowing.network.capability.radiation.UpdateRadiationMessage;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class ModNetwork {
	public static final ResourceLocation CHANNEL_NAME = new ResourceLocation(WhyAmIGlowing.MODID, "network");

	public static final String NETWORK_VERSION = new ResourceLocation(WhyAmIGlowing.MODID, "2").toString();

	public static SimpleChannel getNetworkChannel() {
		final SimpleChannel channel = NetworkRegistry.ChannelBuilder.named(CHANNEL_NAME)
				.clientAcceptedVersions(version -> true)
				.serverAcceptedVersions(version -> true)
				.networkProtocolVersion(() -> NETWORK_VERSION)
				.simpleChannel();

		int id = 1;

		channel.messageBuilder(UpdateRadiationMessage.class, id++)
				.decoder(UpdateRadiationMessage::decode)
				.encoder(UpdateRadiationMessage::encode)
				.consumerMainThread(UpdateRadiationMessage::handle)
				.add();

		return channel;
	}
}
