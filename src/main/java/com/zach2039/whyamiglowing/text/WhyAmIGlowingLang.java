package com.zach2039.whyamiglowing.text;

import com.zach2039.whyamiglowing.WhyAmIGlowing;
import net.minecraft.Util;
import net.minecraft.resources.ResourceLocation;

public enum WhyAmIGlowingLang {
	// Messages
	MESSAGE_RADIATION_GET("message", "radiation.get"),
	MESSAGE_RADIATION_ADD("message", "radiation.add"),
	MESSAGE_RADIATION_SET("message", "radiation.set"),
	MESSAGE_TOGGLED_GEIGER_SPEAKER("message", "toggled_geiger_speaker"),
	MESSAGE_GEIGER_SCAN_0("message", "geiger_scan.0"),
	MESSAGE_GEIGER_SCAN_1("message", "geiger_scan.1"),
	MESSAGE_GEIGER_SCAN_2("message", "geiger_scan.2"),
	MESSAGE_GEIGER_SCAN_3("message", "geiger_scan.3"),
	MESSAGE_GEIGER_SCAN_4("message", "geiger_scan.4"),
	MESSAGE_EXPOSURE_SYMPTOM_0("message", "exposure_symptom.0"),
	MESSAGE_EXPOSURE_SYMPTOM_1("message", "exposure_symptom.1"),
	MESSAGE_EXPOSURE_SYMPTOM_2("message", "exposure_symptom.2"),
	MESSAGE_HIGH_EXPOSURE_SYMPTOM_0("message", "high_exposure_symptom.0"),
	MESSAGE_HIGH_EXPOSURE_SYMPTOM_1("message", "high_exposure_symptom.1"),
	MESSAGE_HIGH_EXPOSURE_SYMPTOM_2("message", "high_exposure_symptom.2"),
	MESSAGE_HIGH_EXPOSURE_SYMPTOM_3("message", "high_exposure_symptom.3"),
	MESSAGE_EXTREME_EXPOSURE_SYMPTOM_0("message", "extreme_exposure_symptom.0"),
	MESSAGE_EXTREME_EXPOSURE_SYMPTOM_1("message", "extreme_exposure_symptom.1"),
	MESSAGE_EXTREME_EXPOSURE_SYMPTOM_2("message", "extreme_exposure_symptom.2"),
	MESSAGE_EXTREME_EXPOSURE_SYMPTOM_3("message", "extreme_exposure_symptom.3"),

	// GUI
	GEIGER_COUNTER_HUD(prefix() + "geiger_counter.hud"),

	// Item Tooltips
	ITEM_DESC_GEIGER_SILENT("item", "geiger_silent.desc"),

	// Subtitles
	SUBTITLE_GEIGER_TICK_LOW("subtitles", "geiger_tick.low"),
	SUBTITLE_GEIGER_TICK_MED("subtitles", "geiger_tick.med"),
	SUBTITLE_GEIGER_TICK_HIGH("subtitles", "geiger_tick.high"),
	SUBTITLE_GEIGER_TICK_VERY_HIGH("subtitles", "geiger_tick.very_high"),
	SUBTITLE_GEIGER_TICK_EXTREME("subtitles", "geiger_tick.extreme"),

	// Commands
	COMMAND_RADIATION_INVALID_ENTITY("commands", "radiation.invalid_entity")
	;

	private final String key;

	WhyAmIGlowingLang(final String type, final String path) {
		this(Util.makeDescriptionId(type, new ResourceLocation(WhyAmIGlowing.MODID, path)));
	}

	WhyAmIGlowingLang(final String key) {
		this.key = key;
	}

	public String getTranslationKey() {
		return key;
	}

	private static String prefix() {
		return WhyAmIGlowing.MODID + ".";
	}
}
