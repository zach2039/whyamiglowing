package com.zach2039.whyamiglowing.world.item;

import com.zach2039.whyamiglowing.core.RadiationHelper;
import com.zach2039.whyamiglowing.text.WhyAmIGlowingLang;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class GeigerCounterItem extends Item {

	public GeigerCounterItem(Properties arg) {
		super(arg);
	}

	@Override
	public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> tooltip, TooltipFlag tooltipFlag) {
		super.appendHoverText(itemStack, level, tooltip, tooltipFlag);

		boolean isSilent = itemStack.getOrCreateTag().getBoolean("Silent");

		if (isSilent) {
			tooltip.add(Component.literal(""));
			tooltip.add(Component.translatable(WhyAmIGlowingLang.ITEM_DESC_GEIGER_SILENT.getTranslationKey()).withStyle(ChatFormatting.DARK_GRAY).withStyle(ChatFormatting.ITALIC));
		}
	}

	private void toggleSilent(final ItemStack itemStack) {
		boolean lastSilentValue = itemStack.getOrCreateTag().getBoolean("Silent");
		itemStack.getOrCreateTag().putBoolean("Silent", !lastSilentValue);
	}

	@Override
	public InteractionResultHolder<ItemStack> use(final Level level, final Player player, final InteractionHand interactionHand) {
		final ItemStack heldItem = player.getItemInHand(interactionHand);

		if (!level.isClientSide) {
			if (player.isShiftKeyDown()) {
				toggleSilent(heldItem);
				player.getLevel().playSound(null, player.getX(), player.getY(), player.getZ(),
						SoundEvents.LEVER_CLICK, SoundSource.PLAYERS, 0.5f, 1f + ((player.getRandom().nextFloat() / 2f) - 0.5f));
				player.sendSystemMessage(Component.translatable(String.format(WhyAmIGlowingLang.MESSAGE_TOGGLED_GEIGER_SPEAKER.getTranslationKey())));
			} else {
				RadiationHelper.scanWithGeigerCounter(player, player);
			}
			return new InteractionResultHolder<>(InteractionResult.SUCCESS, heldItem);
		}

		return new InteractionResultHolder<>(InteractionResult.PASS, heldItem);
	}

	@Override
	public InteractionResult interactLivingEntity(final ItemStack stack, final Player player, final LivingEntity target, final InteractionHand hand) {
		if (!player.level.isClientSide) {
			RadiationHelper.scanWithGeigerCounter(player, target);
		}

		return InteractionResult.SUCCESS;
	}
}
