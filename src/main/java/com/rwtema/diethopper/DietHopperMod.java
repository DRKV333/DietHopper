package com.rwtema.diethopper;

import java.lang.RuntimeException;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.ExistingSubstitutionException;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid = DietHopperMod.MODID, version = DietHopperMod.VERSION, acceptedMinecraftVersions = "[1.7.10,)")
public class DietHopperMod {
    public static final String MODID = "diethopper";
    public static final String VERSION = "1.1";

    @Mod.EventHandler
    public void preinit(FMLPreInitializationEvent event) {
        try {
            Block hopper = new BlockDietHopper().setHardness(3.0F).setResistance(8.0F).setStepSound(Block.soundTypeWood).setBlockName("hopper").setBlockTextureName("hopper");

            GameRegistry.addSubstitutionAlias("minecraft:hopper", GameRegistry.Type.BLOCK, hopper);
            GameRegistry.addSubstitutionAlias("minecraft:hopper", GameRegistry.Type.ITEM, new ItemBlock(hopper));
        } catch (ExistingSubstitutionException e) {
            throw new RuntimeException(e);
        }
    }
}
