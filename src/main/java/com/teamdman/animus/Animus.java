package com.teamdman.animus;

import com.teamdman.animus.client.gui.GuiHandler;
import com.teamdman.animus.handlers.AnimusSounds;
import com.teamdman.animus.handlers.EventHandler;
import com.teamdman.animus.proxy.CommonProxy;
import com.teamdman.animus.registry.*;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

@Mod(modid = Constants.Mod.MODID, name = Constants.Mod.NAME, version = Constants.Mod.VERSION, dependencies = Constants.Mod.DEPEND)
public class Animus {
	@Mod.Instance(Constants.Mod.MODID)
	public static Animus instance;
	public static boolean thaumcraftLoaded = false;
	
	@SidedProxy(clientSide = "com.teamdman.animus.proxy.ClientProxy", serverSide = "com.teamdman.animus.proxy.ServerProxy")
	public static CommonProxy proxy;
	//	public static CreativeTabs tabMain = BloodMagic.TAB_BM;
	public static final CreativeTabs tabMain = new CreativeTabs(Constants.Mod.MODID) {
		@Override
		public ItemStack createIcon() {
			return AnimusItems.ALTARDIVINER.getDefaultInstance();
		}
	};

	@Mod.EventHandler
	public void preinit(FMLPreInitializationEvent event) {
		AnimusPotions.init();
		AnimusTiles.init();
		AnimusEntities.init();
		AnimusRecipes.init();
		proxy.preInit(event);
		MinecraftForge.EVENT_BUS.register(new EventHandler());
		thaumcraftLoaded = Loader.isModLoaded("thaumcraft");
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
		NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());
		AnimusSounds.init();
		proxy.init(event);
	}

	@Mod.EventHandler
	public void postinit(FMLPostInitializationEvent event) {
		proxy.postInit(event);
	}
}
