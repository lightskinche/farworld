package net.lightskin.farworld.world;

import net.lightskin.farworld.world.biomes.FarWorldOverworldBiomes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeCache;
import net.minecraft.world.biome.BiomeProvider;

public class FarWorldBiomeCache extends BiomeCache{

	public FarWorldBiomeCache(BiomeProvider p_i1973_1_) {
		super(p_i1973_1_);
		// TODO Auto-generated constructor stub
	}
	
	@Override
    public Biome getBiome(int x, int z, Biome defaultValue)
    {
        return FarWorldOverworldBiomes.muskagBiome;
    }
	
	

}
