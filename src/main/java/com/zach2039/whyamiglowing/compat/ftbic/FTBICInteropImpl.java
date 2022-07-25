package com.zach2039.whyamiglowing.compat.ftbic;

import com.zach2039.whyamiglowing.api.capability.radiationsource.IRadiationSource;
import com.zach2039.whyamiglowing.capability.radiationsource.RadiationSourceCapability;
import com.zach2039.whyamiglowing.core.RadiationHelper;
import com.zach2039.whyamiglowing.util.CapabilityNotPresentException;
import com.zach2039.whyamiglowing.util.MathHelper;
import dev.ftb.mods.ftbic.block.entity.generator.NuclearReactorBlockEntity;
import dev.ftb.mods.ftbic.item.reactor.ReactorPlatingItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;

public class FTBICInteropImpl implements IFTBICInterop {

	@Override
	public void handleReactorRadiationOutput(final BlockEntity blockEntity) {
		if (blockEntity instanceof NuclearReactorBlockEntity reactorBlockEntity) {
			IRadiationSource radiationSource = RadiationHelper.getRadiationSourceFromChunk(blockEntity.getLevel(), blockEntity.getBlockPos());

			if (radiationSource == null)
				return;

			// Get internal shielding from plates
			float shieldingFactorFromPlating = 0f;
			float maxShieldingFactorFromPlating = 1000f;
			for (ItemStack itemStack : reactorBlockEntity.inputItems) {
				if (itemStack.getItem() instanceof ReactorPlatingItem reactorPlatingItem) {
					shieldingFactorFromPlating += (1 - reactorPlatingItem.explosionModifier) * 50f;
				}
			}
			double platingModifier = 1 / (1 + shieldingFactorFromPlating);

			if (!reactorBlockEntity.reactor.paused && reactorBlockEntity.reactor.energyOutput > 0) {
				// If reactor is on, radiation should be proportional to power output (maxing out after 100 zaps/t), but lower if using plating
				double powerOutputModifier = Math.max(1f , (reactorBlockEntity.reactor.energyOutput / 100d));
				double emittedMilliremsPerSecond = powerOutputModifier * platingModifier * radiationSource.getMaxEmittedMilliremsPerSecond();
				radiationSource.setEmittedMilliremsPerSecond(MathHelper.tol((float) emittedMilliremsPerSecond));
			} else {
				// If reactor is off, radiation should be accumulation of whatever is inside, shielded by plates
				// Get internal radiation from fuel
				double containedSourcesMillirems = 0f;
				for (ItemStack itemStack : reactorBlockEntity.inputItems) {
					if (itemStack.getCapability(RadiationSourceCapability.RADIATION_SOURCE_CAPABILITY).isPresent()) {
						IRadiationSource radiationSourceItem = itemStack.getCapability(RadiationSourceCapability.RADIATION_SOURCE_CAPABILITY).orElseThrow(CapabilityNotPresentException::new);

						containedSourcesMillirems += radiationSourceItem.getEmittedMilliremsPerSecond();
					}
				}
				radiationSource.setEmittedMilliremsPerSecond(MathHelper.tol((float) (0.95 * (containedSourcesMillirems * platingModifier))));
			}
		}
	}

}
