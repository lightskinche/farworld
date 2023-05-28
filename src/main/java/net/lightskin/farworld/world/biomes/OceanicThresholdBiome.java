package net.lightskin.farworld.world.biomes;

import net.lightskin.farworld.FarWorld;
import net.lightskin.farworld.blocks.FarWorldBlocks;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.Biome.BiomeProperties;

public class OceanicThresholdBiome extends OverworldBiomeSpecial {

	public OceanicThresholdBiome() {
		super(new BiomeProperties("Oceanic Threshold")
            	.setBaseHeight(-1.5F)
            	.setHeightVariation(0.25F)
            	.setRainDisabled()
            	.setTemperature(1.0F));
		this.spawnableMonsterList = null;
		this.topBlock = FarWorldBlocks.hardStone.getDefaultState();
		this.fillerBlock = FarWorldBlocks.hardStone.getDefaultState();
		this.setRegistryName(FarWorld.MODID,"oceanic_threshold");
	}

}
