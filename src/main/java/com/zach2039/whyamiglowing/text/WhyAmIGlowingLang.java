package com.zach2039.whyamiglowing.text;

import com.zach2039.whyamiglowing.WhyAmIGlowing;
import net.minecraft.Util;
import net.minecraft.resources.ResourceLocation;

public enum WhyAmIGlowingLang {
	// Messages
	MESSAGE_DOSE_GET("message", "radiation.dose.get"),
	MESSAGE_DOSE_ADD("message", "radiation.dose.add"),
	MESSAGE_DOSE_SET("message", "radiation.dose.set"),
	MESSAGE_CONTAMINATION_GET("message", "radiation.contamination.get"),
	MESSAGE_CONTAMINATION_ADD("message", "radiation.contamination.add"),
	MESSAGE_CONTAMINATION_SET("message", "radiation.contamination.set"),
	MESSAGE_TOGGLED_GEIGER_SPEAKER("message", "toggled_geiger_speaker"),
	MESSAGE_GEIGER_SCAN_0("message", "geiger_scan.0"),
	MESSAGE_GEIGER_SCAN_1("message", "geiger_scan.1"),
	MESSAGE_GEIGER_SCAN_2("message", "geiger_scan.2"),
	MESSAGE_GEIGER_SCAN_3("message", "geiger_scan.3"),
	MESSAGE_GEIGER_SCAN_4("message", "geiger_scan.4"),
	MESSAGE_GEIGER_SCAN_5("message", "geiger_scan.5"),
	MESSAGE_GEIGER_SCAN_6("message", "geiger_scan.6"),
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
	ITEM_SHIFT_FOR_INFO_DESC("item", "shift_for_info.desc"),
	ITEM_GEIGER_COUNTER_SILENT("item", "geiger_counter.silent"),
	ITEM_GEIGER_COUNTER_DESC("item", "geiger_counter.desc"),
	ITEM_RAD_AWAY_DESC("item", "rad_away.desc"),
	ITEM_RAD_X_DESC("item", "rad_x.desc"),
	ITEM_IODINE_DEFENSE_DESC("item", "iodine_defense.desc"),
	ITEM_HAZMAT_GEAR_DESC("item", "hazmat_gear.desc"),


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
