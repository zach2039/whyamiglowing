package com.zach2039.whyamiglowing.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import net.minecraftforge.common.ForgeConfigSpec.DoubleValue;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Arrays;
import java.util.List;

public class WhyAmIGlowingConfig {
	public static class Client {
		public final HUDPos geigerCounterHudPos;

		public final BooleanValue geigerCounterTextHud;

		public final DoubleValue geigerCounterHudScale;

		Client(final ForgeConfigSpec.Builder builder) {
			builder.comment("Client Config Settings").push("client");

			geigerCounterTextHud = builder
					.comment("Selects between graphical and text geiger counter overlay.")
					.define("geigerCounterTextHud", false);

			geigerCounterHudScale = builder
					.comment("Selects between graphical and text geiger counter overlay.")
					.defineInRange("geigerCounterHudScale", 0.5f, 0.01f, 10f);

			geigerCounterHudPos = new HUDPos(
					builder,
					"The position of the Geiger Counter HUD on-screen",
					"geigerCounterHudPos",
					0,0
				);



			builder.pop();
		}

		public static class HUDPos {
			public final IntValue x;

			public final IntValue y;

			HUDPos(final ForgeConfigSpec.Builder builder, final String comment, final String path, final int defaultX, final int defaultY) {
				builder.comment(comment)
						.push(path);

				x = builder
						.comment("The x coordinate")
						.defineInRange("x", defaultX, 0, Integer.MAX_VALUE);

				y = builder
						.comment("The y coordinate")
						.defineInRange("y", defaultY, 0, Integer.MAX_VALUE);

				builder.pop();
			}
		}
	}

	public static class Server {
		public final ConfigValue<List<String>> radiationSourceItems;

		public final ConfigValue<List<Float>> radiationSourceItemIntensities;

		public final ConfigValue<List<String>> radiationSourceBlocks;

		public final ConfigValue<List<Float>> radiationSourceBlockIntensities;

		public final IntValue radiationMaxDistance;

		public final IntValue radiationSourceBlockChunkMaxRadius;

		public final BooleanValue allowRadiationShieldingWithBlocks;

		public final IntValue radiationShieldingScanDistance;

		public final DoubleValue heavyRadiationShieldingReductionFactor;

		public final DoubleValue mediumRadiationShieldingReductionFactor;

		public final DoubleValue lightRadiationShieldingReductionFactor;

		public final DoubleValue veryLowResistanceEquipmentBonus;
		public final DoubleValue lowResistanceEquipmentBonus;
		public final DoubleValue mediumResistanceEquipmentBonus;
		public final DoubleValue highResistanceEquipmentBonus;
		public final DoubleValue veryHighResistanceEquipmentBonus;
		public final DoubleValue fullHazmatEquipmentResistanceBonus;
		public final DoubleValue maxRadiationResistanceBonus;
		public final DoubleValue maxInternalRadiationResistanceBonus;

		public final DoubleValue passiveDoseReductionMilliremPerHour;

		public final DoubleValue radAwayDoseReductionMilliremPerHour;

		public final IntValue radAwayMaxLevel;

		public final IntValue radAwayDurationTicks;

		public final DoubleValue radXExposureResistancePerLevel;

		public final IntValue radXMaxLevel;

		public final IntValue radXDurationTicks;

		public final DoubleValue iodineDefenseInternalExposureResistancePerLevel;

		public final IntValue iodineDefenseMaxLevel;

		public final IntValue iodineDefenseDurationTicks;

		public final BooleanValue regenerationEffectStopsSlightMildARSSymptoms;

		public final BooleanValue regenerationEffectSpeedsUpARSRecovery;

		public final ConfigValue<List<String>> livingEntitiesImmuneToRadiationSickness;

		Server(final ForgeConfigSpec.Builder builder) {
			builder.comment("Server Config Settings").push("server");

			radiationSourceItems = builder
					.comment("A list of radiation sources items. Item tags can be used.")
					.define("radiationSourceItems", Arrays.asList(
							"#forge:nuggets/uranium",
							"#forge:rods/uranium",
							"#forge:ingots/uranium",
							"#forge:dusts/uranium",
							"#forge:plates/uranium",
							"#forge:gears/uranium",
							"#forge:storage_blocks/uranium",
							"#whyamiglowing:fuel_rod/uranium",
							"#whyamiglowing:dual_fuel_rod/uranium",
							"#whyamiglowing:quad_fuel_rod/uranium",
							"whyamiglowing:fallout"
					));

			radiationSourceItemIntensities = builder
					.comment("A list of intensities for the previously defined source items, in mrem/s.")
					.define("radiationSourceIntensities", Arrays.asList(
							27.5f,
							125f,
							250f,
							250f,
							250f,
							1000f,
							2250f,
							1000f,
							2000f,
							4000f,
							833f
					));

			radiationSourceBlocks = builder
					.comment("A list of radiation source blocks. Block tags can be used. Some block sources might be active, like reactors.")
					.define("radiationSourceBlocks", Arrays.asList(
							"ftbic:uranium_block",
							"ftbic:nuclear_reactor",
							"whyamiglowing:fallout"
					));

			radiationSourceBlockIntensities = builder
					.comment("A list of intensities for the previously defined source blocks, in mrem/s.")
					.define("radiationSourceBlockIntensities", Arrays.asList(
							2250f,
							10000000f,
							833f
					));

			radiationMaxDistance = builder
					.comment("The range that radiation can travel from sources.")
					.comment("Large numbers might affect server tick speed.")
					.defineInRange("radiationMaxDistance", 32, 1, 256);

			radiationSourceBlockChunkMaxRadius = builder
					.comment("The radius in chunks around living entities that are checked for radiation source blocks.")
					.comment("Large numbers might affect server tick speed.")
					.defineInRange("radiationSourceBlockChunkMaxRadius", 3, 0, 32);

			allowRadiationShieldingWithBlocks = builder
					.comment("Allows blocks that are tagged appropriately to reduce radiation through them.")
					.comment("Valid tags for blocks are #whyamiglowing:blocks/radiation_shielding/light, #whyamiglowing:blocks/radiation_shielding/medium, #whyamiglowing:blocks/radiation_shielding/heavy")
					.define("allowRadiationShieldingWithBlocks", true);

			radiationShieldingScanDistance = builder
					.comment("The number of max blocks scanned for between radiation sources and entities.")
					.comment("Large numbers might affect server tick speed.")
					.defineInRange("radiationShieldingScanDistance", 32, 1, 256);

			lightRadiationShieldingReductionFactor = builder
					.comment("The factor by which blocks tagged #whyamiglowing:blocks/radiation_shielding/light reduce radiation.")
					.comment("Radiation received will be calculated as R = Rorig * (1 / (1 + factor)).")
					.defineInRange("lightRadiationShieldingReductionFactor", 25f, 0f, Float.MAX_VALUE);

			mediumRadiationShieldingReductionFactor = builder
					.comment("The factor by which blocks tagged #whyamiglowing:blocks/radiation_shielding/medium reduce radiation.")
					.comment("Radiation received will be calculated as R = Rorig * (1 / (1 + factor)).")
					.defineInRange("mediumRadiationShieldingReductionFactor", 50f, 0f, Float.MAX_VALUE);

			heavyRadiationShieldingReductionFactor = builder
					.comment("The factor by which blocks tagged #whyamiglowing:blocks/radiation_shielding/heavy reduce radiation.")
					.comment("Radiation received will be calculated as R = Rorig * (1 / (1 + factor)).")
					.defineInRange("heavyRadiationShieldingReductionFactor", 100f, 0f, Float.MAX_VALUE);

			veryLowResistanceEquipmentBonus = builder
					.comment("The bonus to radiation resistance that very low resistance equipment gives.")
					.defineInRange("veryLowResistanceEquipmentBonus", 0.05f, 0.0f, 1.0f)
					;

			lowResistanceEquipmentBonus = builder
					.comment("The bonus to radiation resistance that low resistance equipment gives.")
					.defineInRange("lowResistanceEquipmentBonus", 0.10f, 0.0f, 1.0f)
					;

			mediumResistanceEquipmentBonus = builder
					.comment("The bonus to radiation resistance that medium resistance equipment gives.")
					.defineInRange("mediumResistanceEquipmentBonus", 0.15f, 0.0f, 1.0f)
					;

			highResistanceEquipmentBonus = builder
					.comment("The bonus to radiation resistance that high resistance equipment gives.")
					.defineInRange("highResistanceEquipmentBonus", 0.20f, 0.0f, 1.0f)
					;

			veryHighResistanceEquipmentBonus = builder
					.comment("The bonus to radiation resistance that very high resistance equipment gives.")
					.defineInRange("veryHighResistanceEquipmentBonus", 0.25f, 0.0f, 1.0f)
					;

			fullHazmatEquipmentResistanceBonus = builder
					.comment("The bonus to radiation resistance that wearing full hazmat set will provide .")
					.defineInRange("fullHazmatEquipmentResistanceBonus", 0.10f, 0.0f, 1.0f)
					;

			maxRadiationResistanceBonus = builder
					.comment("The max radiation resistance an entity can have.")
					.defineInRange("maxRadiationResistanceBonus", 0.95f, 0.0f, 1.0f)
					;

			maxInternalRadiationResistanceBonus = builder
					.comment("The max internal radiation resistance an entity can have.")
					.defineInRange("maxInternalRadiationResistanceBonus", 0.75f, 0.0f, 1.0f)
					;

			passiveDoseReductionMilliremPerHour = builder
					.comment("The max absorbed dose lost over time passively for entities, in mrem/hr.")
					.defineInRange("passiveDoseReductionMilliremPerHour", 150f, 0f, Float.MAX_VALUE);

			radAwayDoseReductionMilliremPerHour = builder
					.comment("The max absorbed dose lost over time for each level of RadAway, in mrem/hr.")
					.defineInRange("radAwayDoseReductionMilliremPerHour", 2000f, 0f, Float.MAX_VALUE);

			radAwayMaxLevel = builder
					.comment("The max level Rad-Away can be stacked with multiple uses.")
					.defineInRange("radAwayMaxLevel", 3, 0, Integer.MAX_VALUE);

			radAwayDurationTicks = builder
					.comment("The duration of Rad-Away when applied.")
					.defineInRange("radAwayDurationTicks", 6000, 0, Integer.MAX_VALUE);

			radXExposureResistancePerLevel = builder
					.comment("The radiation resistance bonus for each level of RadAway.")
					.defineInRange("radXExposureResistancePerLevel", 0.10f, 0f, 1f);

			radXMaxLevel = builder
					.comment("The max level Rad-X can be stacked with multiple uses.")
					.defineInRange("radXMaxLevel", 3, 0, Integer.MAX_VALUE);

			radXDurationTicks = builder
					.comment("The duration of Rad-X when applied.")
					.defineInRange("radXDurationTicks", 12000, 0, Integer.MAX_VALUE);

			iodineDefenseInternalExposureResistancePerLevel = builder
					.comment("The internal radiation resistance bonus for each level of Iodine Defense.")
					.defineInRange("iodineDefenseInternalExposureResistancePerLevel", 0.50f, 0f, 1f);

			iodineDefenseMaxLevel = builder
					.comment("The max level Iodine Defense can be stacked with multiple uses.")
					.defineInRange("iodineDefenseMaxLevel", 0, 0, Integer.MAX_VALUE);

			iodineDefenseDurationTicks = builder
					.comment("The duration of Iodine Defense when applied.")
					.defineInRange("iodineDefenseDurationTicks", 24000, 0, Integer.MAX_VALUE);

			regenerationEffectStopsSlightMildARSSymptoms = builder
					.comment("Whether slight and mild radiation sickness can have symptoms reduced by having an active regeneration effect.")
					.define("regenerationEffectStopsSlightMildARSSymptoms", true);

			regenerationEffectSpeedsUpARSRecovery = builder
					.comment("Whether radiation sickness recovery speeds up when having an active regeneration effect.")
					.define("regenerationEffectSpeedsUpARSRecovery", true);

			livingEntitiesImmuneToRadiationSickness = builder
					.comment("A list of living entities that are immune to the effects of radiation sickness. Partial matches are possible with items preceeded with a # symbol")
					.define("livingEntitiesImmuneToRadiationSickness", Arrays.asList(
							"entity.minecraft.iron_golem",
							"entity.minecraft.skeleton_horse",
							"entity.minecraft.zombie_horse",
							"entity.minecraft.snow_golem",
							"entity.minecraft.mooshroom",
							"entity.minecraft.guardian",
							"entity.minecraft.elder_guardian",
							"entity.minecraft.vex",
							"entity.minecraft.allay",
							"entity.minecraft.slime",
							"entity.minecraft.phantom",
							"entity.minecraft.zombie",
							"entity.minecraft.zombie_villager",
							"entity.minecraft.husk",
							"entity.minecraft.drowned",
							"entity.minecraft.giant",
							"entity.minecraft.skeleton",
							"entity.minecraft.stray",
							"entity.minecraft.wither",
							"entity.minecraft.wither_skeleton",
							"entity.minecraft.warden",
							"entity.minecraft.blaze",
							"entity.minecraft.magma_cube",
							"entity.minecraft.ghast",
							"entity.minecraft.zombified_piglin",
							"entity.minecraft.zoglin",
							"entity.quark.wraith",
							"entity.quark.forgotten",
							"entity.quark.stoneling",
							"entity.earthmobsmod.bone_spider",
							"entity.earthmobsmod.stray_bone_spider",
							"entity.earthmobsmod.bouldering_zombie",
							"entity.earthmobsmod.lobber_zombie",
							"entity.earthmobsmod.bouldering_drowned",
							"entity.earthmobsmod.lobber_drowned",
							"entity.earthmobsmod.tropical_slime",
							"entity.earthmobsmod.skeleton_wolf",
							"entity.alexmobs.bone_serpent",
							"entity.alexmobs.bone_serpent_part",
							"entity.alexmobs.cockroach",
							"entity.alexmobs.cockroach_egg",
							"entity.alexmobs.soul_vulture",
							"entity.alexmobs.spectre",
							"entity.alexmobs.guster",
							"entity.alexmobs.skelewag",
							"#copper_golem"
					));

			builder.pop();
		}
	}


	private static final ForgeConfigSpec clientSpec;
	public static final Client CLIENT;

	static {
		final Pair<Client, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Client::new);
		clientSpec = specPair.getRight();
		CLIENT = specPair.getLeft();
	}

	private static final ForgeConfigSpec serverSpec;
	public static final Server SERVER;

	static {
		final Pair<Server, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Server::new);
		serverSpec = specPair.getRight();
		SERVER = specPair.getLeft();
	}

	public static void register(final ModLoadingContext context) {
		context.registerConfig(ModConfig.Type.CLIENT, clientSpec);
		context.registerConfig(ModConfig.Type.SERVER, serverSpec);
	}
}


