package net.lightskin.farworld.world.biomes;

import net.lightskin.farworld.FarWorld;
import net.lightskin.farworld.blocks.FarWorldBlocks;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.Biome.BiomeProperties;

public class OceanicAbyssBiome extends OverworldBiomeSpecial {

	public OceanicAbyssBiome() {
		super(new BiomeProperties("Oceanic Abyss")
            	.setBaseHeight(-1.99999F)
            	.setHeightVariation(0.0F)
            	.setRainDisabled()
            	.setTemperature(1.0F));
		this.spawnableMonsterList = null;
		this.topBlock = FarWorldBlocks.hardStone.getDefaultState();
		this.fillerBlock = FarWorldBlocks.hardStone.getDefaultState();
		this.setRegistryName(FarWorld.MODID,"oceanic_abyss");
	}

}
