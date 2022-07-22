package com.zach2039.whyamiglowing;

import com.zach2039.whyamiglowing.compat.ftbic.FTBICDummyInterop;
import com.zach2039.whyamiglowing.compat.ftbic.IFTBICInterop;
import com.zach2039.whyamiglowing.config.WhyAmIGlowingConfig;
import com.zach2039.whyamiglowing.init.*;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.network.simple.SimpleChannel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;

@Mod(WhyAmIGlowing.MODID)
public class WhyAmIGlowing {

    public static Logger LOGGER = LogManager.getLogger(WhyAmIGlowing.MODID);

    public static final String MODID = "whyamiglowing";
    public static final String NAME = "Why Am I Glowing?";

    public static final SimpleChannel network = ModNetwork.getNetworkChannel();

    public static IFTBICInterop FTBIC_INTEROP;

    public WhyAmIGlowing() {
        WhyAmIGlowingConfig.register(ModLoadingContext.get());

        final IEventBus MOD_BUS = FMLJavaModLoadingContext.get().getModEventBus();

        MOD_BUS.addListener(this::commonSetup);
        MOD_BUS.addListener(this::clientSetup);

        ModItems.initialize(MOD_BUS);
        ModBlocks.initialize(MOD_BUS);
        ModMobEffects.initialize(MOD_BUS);
        ModSoundEvents.initialize(MOD_BUS);

        // Mod Interop
        if (ModList.get().isLoaded("ftbic")) {
            try {
                FTBIC_INTEROP = Class.forName("com.zach2039.whyamiglowing.compat.ftbic.FTBICInteropImpl").asSubclass(IFTBICInterop.class).getConstructor().newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            FTBIC_INTEROP = new FTBICDummyInterop();
        }
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {

        });
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        event.enqueueWork(() -> {

        });
    }

    public static final CreativeModeTab CREATIVE_MODE_TAB = new CreativeModeTab(MODID)
    {
        @Override
        @Nonnull
        public ItemStack makeIcon()
        {
            return new ItemStack(ModItems.GEIGER_COUNTER.get());
        }
    };
}
