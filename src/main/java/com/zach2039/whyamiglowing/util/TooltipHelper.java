package com.zach2039.whyamiglowing.util;

import com.zach2039.whyamiglowing.text.WhyAmIGlowingLang;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.util.List;

public class TooltipHelper {

	public static void appendShiftTooltip(List<Component> tooltip, String translationKey) {
		if (Screen.hasShiftDown()) {
			tooltip.add(Component.literal("---").withStyle(ChatFormatting.DARK_GRAY));
			tooltip.add(Component.translatable(translationKey).withStyle(ChatFormatting.GRAY));
			tooltip.add(Component.literal("---").withStyle(ChatFormatting.DARK_GRAY));
		} else {
			tooltip.add(
					Component.literal("[")
							.append(Component.translatable(WhyAmIGlowingLang.ITEM_SHIFT_FOR_INFO_DESC.getTranslationKey()))
							.append(Component.literal("]"))
							.withStyle(ChatFormatting.DARK_GRAY)
				);
		}
	}
}
