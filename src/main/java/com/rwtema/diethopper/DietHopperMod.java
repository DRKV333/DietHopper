package com.rwtema.diethopper;

import net.minecraft.block.Block;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid = DietHopperMod.MODID, version = DietHopperMod.VERSION, acceptedMinecraftVersions = "[1.7.10,)")
public class DietHopperMod {
    public static final String MODID = "diethopper";
    public static final String VERSION = "1.1";

    @Mod.EventHandler
    public void preinit(FMLPreInitializationEvent event) {
        // TODO: Replace the vanilla hopper instead of adding a new one.
        GameRegistry.registerBlock(new BlockDietHopper(), "hopper");
    }
}
