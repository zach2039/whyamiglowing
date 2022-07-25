package com.zach2039.whyamiglowing.event;

import com.zach2039.whyamiglowing.WhyAmIGlowing;
import com.zach2039.whyamiglowing.api.capability.radiationsource.IRadiationSource;
import com.zach2039.whyamiglowing.capability.radiationsource.RadiationSourceCapability;
import com.zach2039.whyamiglowing.core.RadiationHelper;
import com.zach2039.whyamiglowing.init.ModTags;
import com.zach2039.whyamiglowing.util.CapabilityNotPresentException;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = WhyAmIGlowing.MODID)
public class ItemEventHandler {

	@SubscribeEvent
	public static void applyRadiationLevelToRadioactiveItemTooltip(final ItemTooltipEvent event) {
		ItemStack itemStack = event.getItemStack();

		LazyOptional<IRadiationSource> radiationSourceOptional = RadiationSourceCapability.getRadiationSource(itemStack);

		if (!radiationSourceOptional.isPresent())
			return;

		IRadiationSource radiationSource = radiationSourceOptional.orElseThrow(CapabilityNotPresentException::new);

		float itemExposureMilliremsPerHour = RadiationHelper.convertPerSecondToPerHour(radiationSource.getEmittedMilliremsPerSecond());
		String itemExposure = RadiationHelper.getDosageDisplayMilliremsPerHour(itemExposureMilliremsPerHour);
		ChatFormatting chatFormatting = RadiationHelper.getChatColorForMilliremsPerHour(itemExposureMilliremsPerHour);
		event.getToolTip().add(Component.literal(""));
		event.getToolTip().add(Component.literal("☢: " + itemExposure).withStyle(chatFormatting));
	}

	@SubscribeEvent
	public static void applyRadiationResistanceToArmorTooltip(final ItemTooltipEvent event) {
		ItemStack itemStack = event.getItemStack();

		float radiationResistance = RadiationHelper.getRadiationResistanceOfEquipment(itemStack);


		if (radiationResistance == 0f)
			return;

		String radiationResistancePercentage = String.format("-%.1f%%", radiationResistance * 100f);
		String radiationResistancePercentageSetBonus = "";

		if (itemStack.is(ModTags.Items.HAZMAT_GEAR_PIECE)) {
			float radiationResistanceHazmatSetBonus = RadiationHelper.getFullHazmatEquipmentResistanceBonus();
			radiationResistancePercentageSetBonus = String.format(" (-%.1f%% set bonus)", radiationResistanceHazmatSetBonus * 100f);
		}

		event.getToolTip().add(Component.literal(""));
		event.getToolTip().add(Component.literal("☢: " + radiationResistancePercentage + radiationResistancePercentageSetBonus).withStyle(ChatFormatting.DARK_GREEN));
	}
}
