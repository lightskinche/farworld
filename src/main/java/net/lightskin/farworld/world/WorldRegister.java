package net.lightskin.farworld.world;

import net.lightskin.farworld.FarWorld;
import net.lightskin.farworld.world.biomes.FarWorldOverworldBiomes;
import net.lightskin.farworld.world.biomes.MuskagBiome;
import net.lightskin.farworld.world.type.FarWorldType;
import net.lightskin.farworld.world.underground.CaveBiome;
import net.lightskin.farworld.world.underground.biomes.*;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.BiomeManager.BiomeEntry;
import net.minecraftforge.common.BiomeManager.BiomeType;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class WorldRegister {
	public static final int testBiomeID = 187;
	public static TestBiome testBiome = new TestBiome("Test", 0.2f);
	//ocean abyss
	public static CaveBiome abyssalZoneBiome = new CaveBiome("Abyssal Zone",0.0f);
	//cave biomes
	public static CaveBiome layer = new CaveBiome("layer biome", 0.2f);
	public static CaveBiome region = new CaveBiome("region biome", 0.2f);
	public static CaveBiome section = new CaveBiome("sectional biome", 0.2f);
	public static CaveBiome low = new CaveBiome("low sectional biome", 0.2f);
	//the hardpoint
	
	//the heat
	
	//TODO: fix biome glitch where "deep ocean" is clearly one of these
	public WorldRegister() {
		//BiomeManager.addBiome(BiomeType.COOL, new BiomeEntry(testBiome, testBiomeID));
		ForgeRegistries.BIOMES.register(testBiome.setRegistryName(FarWorld.MODID, "testBiome"));
		ForgeRegistries.BIOMES.register(abyssalZoneBiome.setRegistryName(FarWorld.MODID,"abyssalZoneBiome"));
		//true cave biomes start registry here
		ForgeRegistries.BIOMES.register(layer.setRegistryName(FarWorld.MODID, "aBiome"));
		ForgeRegistries.BIOMES.register(region.setRegistryName(FarWorld.MODID, "bBiome"));
		ForgeRegistries.BIOMES.register(section.setRegistryName(FarWorld.MODID, "cBiome"));
		ForgeRegistries.BIOMES.register(low.setRegistryName(FarWorld.MODID, "dBiome"));
		//the hardpoint
		/*//BiomeManager.addBiome(BiomeType.COOL, new BiomeEntry(hardBiome, hardBiomeID));
		ForgeRegistries.BIOMES.register(hardBiome.setRegistryName(FarWorld.MODID, "hardBiome"));
		//BiomeManager.addBiome(BiomeType.COOL, new BiomeEntry(constrictedBiome, constrictedBiomeID));
		ForgeRegistries.BIOMES.register(constrictedBiome.setRegistryName(FarWorld.MODID, "constrictedBiome"));	
		//BiomeManager.addBiome(BiomeType.COOL, new BiomeEntry(crackedConstrictedBiome, crackedConstrictedBiomeID));
		ForgeRegistries.BIOMES.register(crackedConstrictedBiome.setRegistryName(FarWorld.MODID, "crackedConstrictedBiome"));	
		//BiomeManager.addBiome(BiomeType.COOL, new BiomeEntry(compressedBiome, compressedBiomeID));
		ForgeRegistries.BIOMES.register(compressedBiome.setRegistryName(FarWorld.MODID, "compressedBiome"));
		//BiomeManager.addBiome(BiomeType.COOL, new BiomeEntry(hardCompressedBiome, hardCompressedBiomeID));
		ForgeRegistries.BIOMES.register(hardCompressedBiome.setRegistryName(FarWorld.MODID, "hardCompressedBiome"));
		
		//BiomeManager.addBiome(BiomeType.COOL, new BiomeEntry(thresholdBiome, thresholdBiomeID));
		ForgeRegistries.BIOMES.register(thresholdBiome.setRegistryName(FarWorld.MODID, "thresholdBiome"));*/
		
		//the heat
		/*//BiomeManager.addBiome(BiomeType.COOL, new BiomeEntry(heatBiome, heatBiomeID));
		ForgeRegistries.BIOMES.register(heatBiome.setRegistryName(FarWorld.MODID, "heatBiome"));
		//BiomeManager.addBiome(BiomeType.COOL, new BiomeEntry(burningBiome, burningBiomeID));
		ForgeRegistries.BIOMES.register(burningBiome.setRegistryName(FarWorld.MODID, "burningBiome"));
		//BiomeManager.addBiome(BiomeType.COOL, new BiomeEntry(latticeBiome, latticeBiomeID));
		ForgeRegistries.BIOMES.register(latticeBiome.setRegistryName(FarWorld.MODID, "latticeBiome"));
		//BiomeManager.addBiome(BiomeType.COOL, new BiomeEntry(moltenBiome, moltenBiomeID));
		ForgeRegistries.BIOMES.register(moltenBiome.setRegistryName(FarWorld.MODID, "moltenBiome"));
		//BiomeManager.addBiome(BiomeType.COOL, new BiomeEntry(fantasticalGroveBiome, fantasticalGroveBiomeID));
		ForgeRegistries.BIOMES.register(fantasticalGroveBiome.setRegistryName(FarWorld.MODID, "fantasticalGroveBiome"));
		//BiomeManager.addBiome(BiomeType.COOL, new BiomeEntry(pointBiome, pointBiomeID));
		ForgeRegistries.BIOMES.register(pointBiome.setRegistryName(FarWorld.MODID, "pointBiome"));
		
		//BiomeManager.addBiome(BiomeType.COOL, new BiomeEntry(barrierBiome, barrierBiomeID));
		ForgeRegistries.BIOMES.register(barrierBiome.setRegistryName(FarWorld.MODID, "barrierBiome"));*/
	}
}
