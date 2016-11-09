package com.teamdman.animus;

import com.teamdman.animus.client.gui.GuiHandler;
import com.teamdman.animus.handlers.EventHandler;
import com.teamdman.animus.proxy.CommonProxy;
import com.teamdman.animus.registry.AnimusBlocks;
import com.teamdman.animus.registry.AnimusItems;
import com.teamdman.animus.registry.AnimusRecipes;
import com.teamdman.animus.registry.AnimusRituals;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

import java.io.File;
import java.util.Locale;

// TODO: Sigil that eats for you

@Mod(modid = Animus.MODID, name = Animus.NAME, version = Animus.VERSION, dependencies = Animus.DEPENDENCIES, guiFactory = "com.teamdman.animus.client.gui.config.ConfigGuiFactory")
public class Animus {
    public static final String MODID = "animus";
    public static final String DOMAIN = MODID.toLowerCase(Locale.ENGLISH) + ":";
    public static final String NAME = "Animus";
    public static final String VERSION = "@VERSION@";
    public static final String DEPENDENCIES = "required-after:BloodMagic;required-after:guideapi;after:Waila";

    @SidedProxy(clientSide = "com.teamdman.animus.proxy.ClientProxy", serverSide = "com.teamdman.animus.proxy.ServerProxy")
    public static CommonProxy proxy;

    public static CreativeTabs tabMain = new CreativeTabs(MODID) {
        @Override
        public Item getTabIconItem() {
            return AnimusItems.altarDiviner;
        }
    };

    // init blocks and items
    @Mod.EventHandler
    public void preinit(FMLPreInitializationEvent event) {
        AnimusConfig.init(new File(event.getModConfigurationDirectory(), Animus.MODID + ".cfg"));
        AnimusItems.init();
        AnimusBlocks.init();
        AnimusRecipes.init();
        proxy.preInit(event);
        MinecraftForge.EVENT_BUS.register(new EventHandler());
//        Legacy, might not need
//        FMLCommonHandler.instance().bus().register(new EventHandler());
    }

    // mod setup, register recipes
    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
		NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());
        AnimusRituals.init();
        proxy.init(event);
    }

    // mod interaction
    @Mod.EventHandler
    public void postinit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }


}
