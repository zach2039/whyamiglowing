package com.zach2039.whyamiglowing.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;
import net.minecraftforge.common.ForgeConfigSpec.DoubleValue;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Arrays;
import java.util.List;

public class WhyAmIGlowingConfig {
	public static class Client {
		public final HUDPos geigerCounterHudPos;

		Client(final ForgeConfigSpec.Builder builder) {
			builder.comment("Server Config Settings").push("server");

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

		public final ForgeConfigSpec.BooleanValue allowRadiationShieldingWithBlocks;

		public final IntValue radiationShieldingScanDistance;

		public final DoubleValue heavyRadiationShieldingReductionFactor;

		public final DoubleValue mediumRadiationShieldingReductionFactor;

		public final DoubleValue lightRadiationShieldingReductionFactor;

		public final ConfigValue<List<String>> veryLowResistanceEquipment;
		public final ConfigValue<List<String>> lowResistanceEquipment;
		public final ConfigValue<List<String>> medResistanceEquipment;
		public final ConfigValue<List<String>> highResistanceEquipment;
		public final ConfigValue<List<String>> veryHighResistanceEquipment;

		public final DoubleValue veryLowResistanceEquipmentBonus;
		public final DoubleValue lowResistanceEquipmentBonus;
		public final DoubleValue medResistanceEquipmentBonus;
		public final DoubleValue highResistanceEquipmentBonus;
		public final DoubleValue veryHighResistanceEquipmentBonus;

		public final DoubleValue maxRadiationResistanceBonus;

		public final DoubleValue passiveDoseReductionMilliremPerHour;

		public final DoubleValue radAwayDoseReductionMilliremPerHour;

		public final IntValue radAwayMaxLevel;

		public final IntValue radAwayDurationTicks;

		public final DoubleValue radXExposureResistancePerLevel;

		public final IntValue radXMaxLevel;

		public final IntValue radXDurationTicks;

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
							55f,
							250f,
							500f,
							500f,
							500f,
							2000f,
							4500f,
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
							4500f,
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


			veryLowResistanceEquipment = builder
					.comment("A list of armor items that give a very low radiation resistance boost.")
					.define("veryLowResistanceEquipment", Arrays.asList(
							"minecraft:leather_helmet",
							"minecraft:leather_chestplate",
							"minecraft:leather_leggings",
							"minecraft:leather_boots"
							));

			lowResistanceEquipment = builder
					.comment("A list of armor items that give a low radiation resistance boost.")
					.define("lowResistanceEquipment", Arrays.asList(
							"minecraft:iron_helmet",
							"minecraft:iron_chestplate",
							"minecraft:iron_leggings",
							"minecraft:iron_boots"
					));

			medResistanceEquipment = builder
					.comment("A list of armor items that give a medium radiation resistance boost.")
					.define("medResistanceEquipment", Arrays.asList(
							"minecraft:golden_helmet",
							"minecraft:golden_chestplate",
							"minecraft:golden_leggings",
							"minecraft:golden_boots"
					));

			highResistanceEquipment = builder
					.comment("A list of armor items that give a high radiation resistance boost.")
					.define("highResistanceEquipment", Arrays.asList(
					));

			veryHighResistanceEquipment = builder
					.comment("A list of armor items that give a high radiation resistance boost.")
					.define("veryHighResistanceEquipment", Arrays.asList(
					));

			veryLowResistanceEquipmentBonus = builder
					.comment("The bonus to radiation resistance that very low resistance equipment gives.")
					.defineInRange("veryLowResistanceEquipmentBonus", 0.05f, 0.0f, 1.0f)
					;

			lowResistanceEquipmentBonus = builder
					.comment("The bonus to radiation resistance that low resistance equipment gives.")
					.defineInRange("lowResistanceEquipmentBonus", 0.10f, 0.0f, 1.0f)
					;

			medResistanceEquipmentBonus = builder
					.comment("The bonus to radiation resistance that medium resistance equipment gives.")
					.defineInRange("medResistanceEquipmentBonus", 0.15f, 0.0f, 1.0f)
					;

			highResistanceEquipmentBonus = builder
					.comment("The bonus to radiation resistance that high resistance equipment gives.")
					.defineInRange("highResistanceEquipmentBonus", 0.20f, 0.0f, 1.0f)
					;

			veryHighResistanceEquipmentBonus = builder
					.comment("The bonus to radiation resistance that very high resistance equipment gives.")
					.defineInRange("veryHighResistanceEquipmentBonus", 0.25f, 0.0f, 1.0f)
					;

			maxRadiationResistanceBonus = builder
					.comment("The max radiation resistance an entity can have.")
					.defineInRange("maxResistanceBonus", 0.95f, 0.0f, 1.0f)
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


