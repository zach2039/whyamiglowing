package com.zach2039.whyamiglowing.network.capability.radiation;

import com.zach2039.whyamiglowing.api.capability.radiation.IRadiation;
import com.zach2039.whyamiglowing.capability.radiation.RadiationCapability;
import com.zach2039.whyamiglowing.util.CapabilityNotPresentException;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.LogicalSidedProvider;
import net.minecraftforge.network.NetworkEvent;

import java.util.Optional;
import java.util.function.Supplier;

public class UpdateRadiationMessage {
	private final int livingEntityId;
	private final float absorbedDoseRads;
	private final float currentReceivedRads;
	private final float lastReceivedRads;
	private final float contaminationMilliremPerHour;
	private final float radiationResistance;
	private final float internalRadiationResistance;

	public UpdateRadiationMessage(final int livingEntityId, final float absorbedDoseRads, final float currentReceivedRads, final float lastReceivedRads, final float contaminationMilliremPerHour, final float radiationResistance, final float internalRadiationResistance) {
		this.livingEntityId = livingEntityId;
		this.absorbedDoseRads = absorbedDoseRads;
		this.currentReceivedRads = currentReceivedRads;
		this.lastReceivedRads = lastReceivedRads;
		this.contaminationMilliremPerHour = contaminationMilliremPerHour;
		this.radiationResistance = radiationResistance;
		this.internalRadiationResistance = internalRadiationResistance;
	}

	public static UpdateRadiationMessage decode(final FriendlyByteBuf buf) {
		return new UpdateRadiationMessage(buf.readInt(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat());
	}

	public static void encode(final UpdateRadiationMessage message, final FriendlyByteBuf buf) {
		buf.writeInt(message.livingEntityId);
		buf.writeFloat(message.absorbedDoseRads);
		buf.writeFloat(message.currentReceivedRads);
		buf.writeFloat(message.lastReceivedRads);
		buf.writeFloat(message.contaminationMilliremPerHour);
		buf.writeFloat(message.radiationResistance);
		buf.writeFloat(message.internalRadiationResistance);
	}

	public static void handle(final UpdateRadiationMessage message, final Supplier<NetworkEvent.Context> ctx) {
		final Optional<Level> optionalLevel = LogicalSidedProvider.CLIENTWORLD.get(ctx.get().getDirection().getReceptionSide());

		optionalLevel.ifPresent(level -> {
			Entity entity = level.getEntity(message.livingEntityId);

			if (entity != null && entity instanceof LivingEntity) {
				LivingEntity livingEntity = (LivingEntity) entity;
				final IRadiation radiation = RadiationCapability.getRadiation(livingEntity).orElseThrow(CapabilityNotPresentException::new);

				radiation.setAbsorbedDoseMillirems(message.absorbedDoseRads);
				radiation.setCurrentExternalExposureMilliremsPerSecond(message.currentReceivedRads);
				radiation.setLastExternalExposureMilliremsPerSecond(message.lastReceivedRads);
				radiation.setContaminationMilliremsPerSecond(message.contaminationMilliremPerHour);
				radiation.setRadiationResistance(message.radiationResistance);
				radiation.setInternalRadiationResistance(message.internalRadiationResistance);
			}
		});
	}
}
