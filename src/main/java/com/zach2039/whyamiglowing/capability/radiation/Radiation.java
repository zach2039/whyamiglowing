package com.zach2039.whyamiglowing.capability.radiation;

import com.zach2039.whyamiglowing.WhyAmIGlowing;
import com.zach2039.whyamiglowing.api.capability.radiation.IRadiation;
import com.zach2039.whyamiglowing.config.WhyAmIGlowingConfig;
import com.zach2039.whyamiglowing.core.RadiationHelper;
import com.zach2039.whyamiglowing.network.capability.radiation.UpdateRadiationMessage;
import com.zach2039.whyamiglowing.util.CapabilityNotPresentException;
import com.zach2039.whyamiglowing.util.MathHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.network.PacketDistributor;

import javax.annotation.Nullable;

public class Radiation implements IRadiation, INBTSerializable<CompoundTag> {

	public static final float MAX_DOSAGE_MILLIREMS = 5000000f;

	private static final WhyAmIGlowingConfig.Server serverConfig = WhyAmIGlowingConfig.SERVER;

	@Nullable
	private final LivingEntity livingEntity;
	private float absorbedDoseMillirems = 0f;
	private float currentExposureMilliremsPerSecond = 0f;
	private float lastExposureMilliremsPerSecond = 0f;
	private float radiationResistance = 0f;

	public Radiation(@Nullable final LivingEntity livingEntity) {
		this.livingEntity = livingEntity;
		onChanged(); // think we need this or else client gets no update
	}

	@Override
	public float getAbsorbedDoseMillirems() {
		return MathHelper.tol(this.absorbedDoseMillirems);
	}

	@Override
	public void setAbsorbedDoseMillirems(float absorbedDoseMillirems) {
		this.absorbedDoseMillirems = MathHelper.tol(Math.min(MAX_DOSAGE_MILLIREMS, absorbedDoseMillirems));
		onChanged();
	}

	@Override
	public void increaseAbsorbedDoseMillirems(float absorbedDoseMillirems) {
		this.absorbedDoseMillirems = MathHelper.tol(Math.min(MAX_DOSAGE_MILLIREMS, this.absorbedDoseMillirems + (absorbedDoseMillirems) * (1f - this.radiationResistance)));
		onChanged();
	}

	@Override
	public void decreaseAbsorbedDoseMillirems(float absorbedDoseMillirems) {
		this.absorbedDoseMillirems = MathHelper.tol(Math.max(0f, this.absorbedDoseMillirems - absorbedDoseMillirems));
		onChanged();
	}

	@Override
	public float getRadiationResistance() {
		return MathHelper.tol(this.radiationResistance);
	}

	@Override
	public void setRadiationResistance(float radiationResistance) {
		this.radiationResistance = MathHelper.tol(Math.max(0, Math.min(serverConfig.maxRadiationResistanceBonus.get().floatValue(), MathHelper.tol(radiationResistance))));
		onChanged();
	}

	@Override
	public float getCurrentExposureMilliremsPerSecond() {
		return MathHelper.tol(this.currentExposureMilliremsPerSecond);
	}

	@Override
	public void setCurrentExposureMilliremsPerSecond(float currentExposureMilliremsPerSecond) {
		this.currentExposureMilliremsPerSecond = MathHelper.tol(currentExposureMilliremsPerSecond);
		onChanged();
	}

	@Override
	public float getLastExposureMilliremsPerSecond() {
		return MathHelper.tol(this.lastExposureMilliremsPerSecond);
	}

	@Override
	public void setLastExposureMilliremsPerSecond(float lastExposureMilliremsPerSecond) {
		this.lastExposureMilliremsPerSecond = MathHelper.tol(lastExposureMilliremsPerSecond);
		onChanged();
	}

	@Override
	public void increaseCurrentExposureMilliremsPerSecond(float currentExposureMilliremsPerSecond) {
		this.currentExposureMilliremsPerSecond = MathHelper.tol(this.currentExposureMilliremsPerSecond + currentExposureMilliremsPerSecond);
		onChanged();
	}

	@Override
	public CompoundTag serializeNBT() {
		CompoundTag tag = new CompoundTag();

		tag.putFloat("absorbedDoseMillirems", this.absorbedDoseMillirems);
		tag.putFloat("radiationResistance", this.radiationResistance);
		tag.putFloat("currentExposureMilliremsPerSecond", this.currentExposureMilliremsPerSecond);
		tag.putFloat("lastExposureMilliremsPerSecond", this.lastExposureMilliremsPerSecond);

		return tag;
	}

	@Override
	public void deserializeNBT(CompoundTag tag) {
		this.absorbedDoseMillirems = tag.getFloat("absorbedDoseMillirems");
		this.radiationResistance = tag.getFloat("radiationResistance");
		this.currentExposureMilliremsPerSecond = tag.getFloat("currentExposureMilliremsPerSecond");
		this.lastExposureMilliremsPerSecond = tag.getFloat("lastExposureMilliremsPerSecond");
	}

	protected void onChanged() {
		final Level level = livingEntity.getLevel();

		if (level.isClientSide) {
			return;
		}

		LazyOptional<IRadiation> radCap = RadiationCapability.getRadiation(livingEntity);

		if (radCap.isPresent()) {
			IRadiation radiation = radCap.orElseThrow(CapabilityNotPresentException::new);

			WhyAmIGlowing.network.send(
					PacketDistributor.NEAR.with(
							PacketDistributor.TargetPoint.p(
									livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(),
									256f, livingEntity.getLevel().dimension()
								)
						),
					new UpdateRadiationMessage(
							livingEntity.getId(),
							radiation.getAbsorbedDoseMillirems(),
							radiation.getCurrentExposureMilliremsPerSecond(),
							radiation.getLastExposureMilliremsPerSecond(),
							radiation.getRadiationResistance()
						)
				);
		}
	}
}
