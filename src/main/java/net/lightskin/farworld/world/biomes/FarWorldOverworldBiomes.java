package net.lightskin.farworld.world.biomes;

import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.BiomeManager.BiomeEntry;
import net.minecraftforge.common.BiomeManager.BiomeType;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class FarWorldOverworldBiomes {
	public static MuskagBiome muskagBiome;
	public FarWorldOverworldBiomes() {
		muskagBiome = new MuskagBiome();
		
		//reg
		ForgeRegistries.BIOMES.register(FarWorldOverworldBiomes.muskagBiome);
	}
}
