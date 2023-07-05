package net.lightskin.farworld;

import net.lightskin.farworld.blocks.FarWorldBlocks;
import net.lightskin.farworld.effects.FarWorldPotions;
import net.lightskin.farworld.events.MinecraftForgeHandler;
import net.lightskin.farworld.sound.MusicTable;
import net.lightskin.farworld.events.CommonHandler;
import net.lightskin.farworld.te.FarWorldTileEntities;
import net.lightskin.farworld.world.WorldRegister;
import net.lightskin.farworld.world.biomes.FarWorldOverworldBiomes;
import net.lightskin.farworld.world.biomes.RegionEnforcer;
import net.lightskin.farworld.world.type.FarWorldType;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.MixinEnvironment.Side;

//tears from heaven for water areas
@Mod(modid = FarWorld.MODID, name = FarWorld.NAME, version = FarWorld.VERSION)
public class FarWorld
{
    public static final String MODID = "farworld";
    public static final String NAME = "Far World";
    public static final String VERSION = "0.7b";

    public static Logger logger;

    static public WorldRegister worldRegister;
    static public FarWorldOverworldBiomes overworldBiomes;
    static public FarWorldBlocks farWorldBlocks;
    static public FarWorldTileEntities farWorldTileEntities;
    static public FarWorldPotions farWorldPotions;
    
    //event handlers
    static public CommonHandler tickHandler;
    static public MinecraftForgeHandler chunkHandler;
    //depths of the fallen
    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
    	farWorldTileEntities = new FarWorldTileEntities();
    	farWorldPotions = new FarWorldPotions();
    	farWorldBlocks = new FarWorldBlocks();
    	overworldBiomes = new FarWorldOverworldBiomes();
    	RegionEnforcer.setRules();
    	worldRegister = new WorldRegister();
    	//farWorldPrimaryChannel = NetworkRegistry.INSTANCE.newSimpleChannel(new ResourceLocation(MODID, "primary").toString());
    	FarWorldType.create();
        logger = event.getModLog();
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
    	tickHandler = new CommonHandler();
    	chunkHandler = new MinecraftForgeHandler();
        // some example code
        logger.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
        
    }
}
