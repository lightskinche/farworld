package net.lightskin.farworld.world.biomes;

import net.lightskin.farworld.blocks.FarWorldBlocks;
import net.lightskin.farworld.world.underground.CaveBiomeDecorator;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.BiomeProperties;

public class MuskagBiome extends Biome{
	public MuskagBiome(String name) {
		super(new BiomeProperties(name)
            	.setBaseHeight(0F)
            	.setHeightVariation(0.5F)
            	.setSnowEnabled()
            	.setTemperature(-2.0F)
    			);
		this.topBlock = Blocks.SNOW.getDefaultState();
		this.fillerBlock = FarWorldBlocks.hardStone.getDefaultState();
		this.decorator = new CaveBiomeDecorator();
	}
}
