package com.zach2039.whyamiglowing.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Quaternion;
import com.zach2039.whyamiglowing.WhyAmIGlowing;
import com.zach2039.whyamiglowing.capability.radiation.RadiationCapability;
import com.zach2039.whyamiglowing.config.WhyAmIGlowingConfig;
import com.zach2039.whyamiglowing.core.RadiationHelper;
import com.zach2039.whyamiglowing.init.ModItems;
import com.zach2039.whyamiglowing.text.WhyAmIGlowingLang;
import com.zach2039.whyamiglowing.util.CapabilityNotPresentException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class GeigerCounterGuiOverlay implements IGuiOverlay {
	private final Minecraft minecraft;

	private final WhyAmIGlowingConfig.Client clientConfig = WhyAmIGlowingConfig.CLIENT;

	private final ResourceLocation GEIGER_COUNTER_OVERLAY_TEXTURE = new ResourceLocation(WhyAmIGlowing.MODID, "textures/gui/overlay.png");

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
		float currentReceivedDoseMilliremsPerHour = RadiationHelper.convertPerSecondToPerHour(radiation.getCurrentTotalExposureMilliremsPerSecond());
		float currentReceivedDoseExternalMilliremsPerHour = RadiationHelper.convertPerSecondToPerHour(radiation.getCurrentExternalExposureMilliremsPerSecond());
		float currentRadiationResistance = radiation.getRadiationResistance();
		final var hudPos = clientConfig.geigerCounterHudPos;

		if (clientConfig.geigerCounterTextHud.get()) {
			String absorbedDose = RadiationHelper.getDosageDisplayMillirems(currentAbsorbedDoseMillirems);
			String currentExposure = RadiationHelper.getDosageDisplayMilliremsPerHour(currentReceivedDoseMilliremsPerHour);
			String effectiveExposure = (currentRadiationResistance > 0f) ? " / " + RadiationHelper.getDosageDisplayMilliremsPerHour(currentReceivedDoseExternalMilliremsPerHour * (1f - currentRadiationResistance)) + " eff" : "";
			final var text = I18n.get(WhyAmIGlowingLang.GEIGER_COUNTER_HUD.getTranslationKey(),
					absorbedDose, currentExposure, effectiveExposure);
			poseStack.pushPose();
			poseStack.scale(clientConfig.geigerCounterHudScale.get().floatValue(), clientConfig.geigerCounterHudScale.get().floatValue(), 1f);
			GuiComponent.drawString(poseStack, minecraft.font, text, hudPos.x.get(), hudPos.y.get(), 0xFFFFFF);
			poseStack.popPose();
		} else {
			poseStack.pushPose();
			poseStack.scale(clientConfig.geigerCounterHudScale.get().floatValue(), clientConfig.geigerCounterHudScale.get().floatValue(), 1f);
			RenderSystem.setShader(GameRenderer::getPositionTexShader);
			RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
			RenderSystem.setShaderTexture(0, GEIGER_COUNTER_OVERLAY_TEXTURE);
			int bgX = hudPos.x.get();
			int bgY = hudPos.y.get();
			int mulX = bgX + (106 / 3) * 2;
			int mulY = bgY + (105 / 2);
			int nedX = bgX + (106 / 2) - 1;
			int nedY = bgY + (105 / 10) * 7;
			int coverX = bgX + (106 / 2) - 9;
			int coverY = bgY + ((105 / 10) * 7) - 5;
			float rangeDiv;
			gui.blit(poseStack, bgX, bgY, 1, 1, 106, 105); // counter background
			if (currentReceivedDoseMilliremsPerHour < 500f) {
				gui.blit(poseStack, mulX, mulY, 1, 107, 23, 7); // x0.1 multi
				rangeDiv = 500f;
			} else if (currentReceivedDoseMilliremsPerHour < 5000f) {
				gui.blit(poseStack, mulX, mulY, 26, 107, 23, 7); // x1 multi
				rangeDiv = 5000f;
			} else if (currentReceivedDoseMilliremsPerHour < 50000f) {
				gui.blit(poseStack, mulX, mulY, 51, 107, 23, 7); // x10 multi
				rangeDiv = 50000f;
			} else {
				gui.blit(poseStack, mulX, mulY, 76, 107, 23, 7); // x100 multi
				rangeDiv = 500000f;
			}

			float lerpExposure = currentReceivedDoseMilliremsPerHour;
			float angleRand = (lerpExposure > 0f) ? (player.getRandom().nextFloat() - 0.5f) * 0.02f : 0f;
			float angleRatio = Math.max(0f, Math.min(1f, (lerpExposure / rangeDiv) + angleRand));
			float nedAngle = (90f * angleRatio) - 135f;
			poseStack.translate(nedX, nedY, 0f);
			poseStack.pushPose();
			poseStack.mulPose(new Quaternion(0f, 0f, nedAngle, true));
			gui.blit(poseStack, 0, 0, 1, 116, 51, 3); // needle
			poseStack.popPose();
			poseStack.translate(-nedX, -nedY, 0f);
			gui.blit(poseStack, coverX, coverY, 109, 1, 19, 25); // needle pivot

			poseStack.popPose();
		}
	}
}
