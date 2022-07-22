package com.zach2039.whyamiglowing.client.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import com.zach2039.whyamiglowing.capability.radiation.RadiationCapability;
import com.zach2039.whyamiglowing.config.WhyAmIGlowingConfig;
import com.zach2039.whyamiglowing.core.RadiationHelper;
import com.zach2039.whyamiglowing.init.ModItems;
import com.zach2039.whyamiglowing.text.WhyAmIGlowingLang;
import com.zach2039.whyamiglowing.util.CapabilityNotPresentException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.resources.language.I18n;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class GeigerCounterGuiOverlay implements IGuiOverlay {
	private final Minecraft minecraft;

	public GeigerCounterGuiOverlay(final Minecraft minecraft) {
		this.minecraft = minecraft;
	}

	@Override
	public void render(final ForgeGui gui, final PoseStack poseStack, final float partialTick, final int width, final int height) {
		if (minecraft.level == null || minecraft.player == null) {
			return;
		}

		final var player = minecraft.player;
		if (!player.getInventory().hasAnyMatching((e) -> e.getItem() == ModItems.GEIGER_COUNTER.get())) {
			return;
		}

		final var radiation = RadiationCapability
				.getRadiation(minecraft.player)
				.orElseThrow(CapabilityNotPresentException::new);

		float currentAbsorbedDoseMillirems = radiation.getAbsorbedDoseMillirems();
		float currentReceivedDoseMilliremsPerHour = RadiationHelper.convertPerSecondToPerHour(radiation.getCurrentExposureMilliremsPerSecond());
		float currentRadiationResistance = radiation.getRadiationResistance();

		String absorbedDose = RadiationHelper.getDosageDisplayMillirems(currentAbsorbedDoseMillirems);
		String currentExposure = RadiationHelper.getDosageDisplayMilliremsPerHour(currentReceivedDoseMilliremsPerHour);
		String effectiveExposure = (currentRadiationResistance > 0f) ? " / " + RadiationHelper.getDosageDisplayMilliremsPerHour(currentReceivedDoseMilliremsPerHour * (1f - currentRadiationResistance)) + " eff" : "";

		final var text = I18n.get(WhyAmIGlowingLang.GEIGER_COUNTER_HUD.getTranslationKey(),
				absorbedDose, currentExposure, effectiveExposure);
		final var hudPos = WhyAmIGlowingConfig.CLIENT.geigerCounterHudPos;
		GuiComponent.drawString(poseStack, minecraft.font, text, hudPos.x.get(), hudPos.y.get(), 0xFFFFFF);
	}
}
