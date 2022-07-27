package com.zach2039.whyamiglowing.world.item;

import com.zach2039.whyamiglowing.core.RadiationHelper;
import com.zach2039.whyamiglowing.init.ModMobEffects;
import com.zach2039.whyamiglowing.text.WhyAmIGlowingLang;
import com.zach2039.whyamiglowing.util.TooltipHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class RadAwayItem extends Item {

	public RadAwayItem(Properties arg) {
		super(arg);
	}

	@Override
	public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> tooltip, TooltipFlag tooltipFlag) {
		super.appendHoverText(itemStack, level, tooltip, tooltipFlag);

		TooltipHelper.appendShiftTooltip(tooltip, WhyAmIGlowingLang.ITEM_RAD_AWAY_DESC.getTranslationKey());

		String doseReductionDuration = String.format("(%.1f sec)", RadiationHelper.getRadAwayDurationTicks() / 20f);
		String doseReduction = RadiationHelper.getDosageDisplayMilliremsPerHour(-RadiationHelper.getRadAwayDoseReductionMilliremPerHour());
		tooltip.add(Component.literal(""));
		tooltip.add(Component.literal("☢: " + doseReduction + " " + doseReductionDuration).withStyle(ChatFormatting.DARK_GREEN));
	}

	private void applyEffectToTarget(final Player player, final LivingEntity target) {
		int amp = 0;
		MobEffectInstance prevEffect = player.getEffect(ModMobEffects.RAD_AWAY_EFFECT.get());

		// Allow stacking of effect
		if (prevEffect != null)
			amp = Math.min(RadiationHelper.getRadAwayMaxLevel(), prevEffect.getAmplifier() + 1);

		// Apply rad-away
		target.addEffect(new MobEffectInstance(ModMobEffects.RAD_AWAY_EFFECT.get(), RadiationHelper.getRadAwayDurationTicks(), amp));
		player.getLevel().playSound(null, player.getX(), player.getY(), player.getZ(),
				SoundEvents.BOTTLE_EMPTY, SoundSource.PLAYERS, 0.5f, 1f + ((player.getRandom().nextFloat() / 2f) - 0.5f));
	}

	@Override
	public InteractionResultHolder<ItemStack> use(final Level level, final Player player, final InteractionHand interactionHand) {
		final ItemStack heldItem = player.getItemInHand(interactionHand);

		if (!level.isClientSide) {
			applyEffectToTarget(player, player);
			heldItem.shrink(1);

			return new InteractionResultHolder<>(InteractionResult.SUCCESS, heldItem);
		}

		return new InteractionResultHolder<>(InteractionResult.PASS, heldItem);
	}

	@Override
	public InteractionResult interactLivingEntity(final ItemStack stack, final Player player, final LivingEntity target, final InteractionHand interactionHand) {
		final ItemStack heldItem = player.getItemInHand(interactionHand);

		if (!player.level.isClientSide) {
			applyEffectToTarget(player, target);
			heldItem.shrink(1);
		}

		return InteractionResult.SUCCESS;
	}
}
