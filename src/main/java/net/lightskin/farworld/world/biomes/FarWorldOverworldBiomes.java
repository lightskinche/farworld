package net.lightskin.farworld.world.biomes;

import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.BiomeManager.BiomeEntry;
import net.minecraftforge.common.BiomeManager.BiomeType;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class FarWorldOverworldBiomes {
	public static MuskagBiome muskagBiome;
	public static OceanicAbyssBiome oceanicAbyssBiome;
	public static OceanicThresholdBiome oceanicThresholdBiome;
	public FarWorldOverworldBiomes() {
		muskagBiome = new MuskagBiome();
		oceanicAbyssBiome = new OceanicAbyssBiome();
		oceanicThresholdBiome = new OceanicThresholdBiome();
		
		//reg
		ForgeRegistries.BIOMES.register(FarWorldOverworldBiomes.muskagBiome);
		ForgeRegistries.BIOMES.register(FarWorldOverworldBiomes.oceanicAbyssBiome);
		ForgeRegistries.BIOMES.register(FarWorldOverworldBiomes.oceanicThresholdBiome);
	}
}
