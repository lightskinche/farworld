package net.lightskin.farworld.world.underground;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.BiomeProperties;

//TODO: switch all biomes to cave biomes
public class CaveBiome extends Biome{
	public CaveBiome(String name, float temp) {
		super(new BiomeProperties(name)
            	.setBaseHeight(1.0F)
            	.setHeightVariation(0.2F)
            	.setRainDisabled()
            	.setTemperature(temp)
    			);
		this.decorator = new CaveBiomeDecorator();
	}
}
