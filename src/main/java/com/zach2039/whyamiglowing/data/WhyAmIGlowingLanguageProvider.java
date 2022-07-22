package com.zach2039.whyamiglowing.data;

import com.zach2039.whyamiglowing.WhyAmIGlowing;
import com.zach2039.whyamiglowing.init.ModBlocks;
import com.zach2039.whyamiglowing.init.ModItems;
import com.zach2039.whyamiglowing.init.ModMobEffects;
import com.zach2039.whyamiglowing.text.WhyAmIGlowingLang;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;

public class WhyAmIGlowingLanguageProvider extends LanguageProvider {

	public WhyAmIGlowingLanguageProvider(final DataGenerator gen) {
		super(gen, WhyAmIGlowing.MODID, "en_us");
	}

	@Override
	protected void addTranslations() {
		addItems();
		addBlocks();
		addCapabilities();
		addMessages();
		addTooltips();
		addSubtitles();
		addEffects();
		addCommands();
		addMisc();
	}

	private void addMisc() {
		add("itemGroup." + WhyAmIGlowing.MODID, "Why Am I Glowing?");
	}

	private void addCommands() {
		add(WhyAmIGlowingLang.COMMAND_RADIATION_INVALID_ENTITY.getTranslationKey(), "Invalid entity, only living entities (e.g. players, animals, monsters) are supported");
	}

	private void addEffects() {
		addEffect(ModMobEffects.RAD_X_EFFECT,  "Rad-X");
		addEffect(ModMobEffects.RAD_AWAY_EFFECT,  "Rad-Away");
		addEffect(ModMobEffects.ACUTE_RADIATION_SICKNESS_EFFECT,  "Rad Sickness");
	}
	private void addSubtitles() {
		add(WhyAmIGlowingLang.SUBTITLE_GEIGER_TICK_LOW.getTranslationKey(), "Geiger slowly ticks");
		add(WhyAmIGlowingLang.SUBTITLE_GEIGER_TICK_MED.getTranslationKey(), "Geiger ticks");
		add(WhyAmIGlowingLang.SUBTITLE_GEIGER_TICK_HIGH.getTranslationKey(), "Geiger frequently ticks");
		add(WhyAmIGlowingLang.SUBTITLE_GEIGER_TICK_VERY_HIGH.getTranslationKey(), "Geiger rapidly ticks");
		add(WhyAmIGlowingLang.SUBTITLE_GEIGER_TICK_EXTREME.getTranslationKey(), "Geiger continuously ticks");
	}

	private void addTooltips() {
		add(WhyAmIGlowingLang.ITEM_DESC_GEIGER_SILENT.getTranslationKey(), "Speaker off");
	}
	private void addMessages() {
		// Commands
		add(WhyAmIGlowingLang.MESSAGE_RADIATION_GET.getTranslationKey(), "Entity: %s, Dose: %s");
		add(WhyAmIGlowingLang.MESSAGE_RADIATION_ADD.getTranslationKey(), "Added %2$s dose to %1$s");
		add(WhyAmIGlowingLang.MESSAGE_RADIATION_SET.getTranslationKey(), "Set radiation dose of %s to %s");

		// Geiger Counter
		add(WhyAmIGlowingLang.MESSAGE_TOGGLED_GEIGER_SPEAKER.getTranslationKey(), "Toggled speaker output.");
		add(WhyAmIGlowingLang.MESSAGE_GEIGER_SCAN_0.getTranslationKey(), "Scan of: %s");
		add(WhyAmIGlowingLang.MESSAGE_GEIGER_SCAN_1.getTranslationKey(), "AbsDose: %s");
		add(WhyAmIGlowingLang.MESSAGE_GEIGER_SCAN_2.getTranslationKey(), "Exposure: %s (%s eff.)");
		add(WhyAmIGlowingLang.MESSAGE_GEIGER_SCAN_3.getTranslationKey(), "Output: %s");
		add(WhyAmIGlowingLang.MESSAGE_GEIGER_SCAN_4.getTranslationKey(), "Resistance: %s");

		// Exposure
		add(WhyAmIGlowingLang.MESSAGE_EXPOSURE_SYMPTOM_0.getTranslationKey(), "You feel sick...");
		add(WhyAmIGlowingLang.MESSAGE_EXPOSURE_SYMPTOM_1.getTranslationKey(), "Your head hurts...");
		add(WhyAmIGlowingLang.MESSAGE_EXPOSURE_SYMPTOM_2.getTranslationKey(), "You feel dizzy...");
		add(WhyAmIGlowingLang.MESSAGE_HIGH_EXPOSURE_SYMPTOM_0.getTranslationKey(), "You feel very nauseous...");
		add(WhyAmIGlowingLang.MESSAGE_HIGH_EXPOSURE_SYMPTOM_1.getTranslationKey(), "Your head aches horribly!");
		add(WhyAmIGlowingLang.MESSAGE_HIGH_EXPOSURE_SYMPTOM_2.getTranslationKey(), "You feel very disoriented...");
		add(WhyAmIGlowingLang.MESSAGE_HIGH_EXPOSURE_SYMPTOM_3.getTranslationKey(), "Your skin stings!");
		add(WhyAmIGlowingLang.MESSAGE_EXTREME_EXPOSURE_SYMPTOM_0.getTranslationKey(), "You feel like you are about to vomit!");
		add(WhyAmIGlowingLang.MESSAGE_EXTREME_EXPOSURE_SYMPTOM_1.getTranslationKey(), "Your head is pounding!");
		add(WhyAmIGlowingLang.MESSAGE_EXTREME_EXPOSURE_SYMPTOM_2.getTranslationKey(), "You feel like you are going to pass out!");
		add(WhyAmIGlowingLang.MESSAGE_EXTREME_EXPOSURE_SYMPTOM_3.getTranslationKey(), "Your skin is burning!");
	}

	private void addCapabilities() {
		add(WhyAmIGlowingLang.GEIGER_COUNTER_HUD.getTranslationKey(), "Absorbed Dose: %s (%s%s)");
	}

	private void addBlocks() {
		addBlock(ModBlocks.FALLOUT, "Fallout");
	}

	private void addItems() {
		addItem(ModItems.GEIGER_COUNTER, "Geiger Counter");
		addItem(ModItems.RAD_X, "Rad-X");
		addItem(ModItems.RAD_AWAY, "Rad-Away");
	}
}
