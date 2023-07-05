package net.lightskin.farworld.world.biomes;

import net.lightskin.farworld.FarWorld;
import net.lightskin.farworld.blocks.FarWorldBlocks;
import net.lightskin.farworld.sound.FarWorldMusicalSound;
import net.lightskin.farworld.sound.MusicTable;
import net.lightskin.farworld.world.MusicalBiomeBase;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.Biome.BiomeProperties;

public class OceanicAbyssBiome extends OverworldBiomeSpecial implements MusicalBiomeBase{

	public OceanicAbyssBiome() {
		super(new BiomeProperties("Oceanic Abyss")
            	.setBaseHeight(-1.99999F)
            	.setHeightVariation(1.0F)
            	.setRainDisabled()
            	.setTemperature(1.0F));
		this.spawnableMonsterList = null;
		this.topBlock = FarWorldBlocks.hardStone.getDefaultState();
		this.fillerBlock = FarWorldBlocks.hardStone.getDefaultState();
		this.setRegistryName(FarWorld.MODID,"oceanic_abyss");
	}

	@Override
	public FarWorldMusicalSound mobAttackMusic() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FarWorldMusicalSound mobAttackPlayerMusic() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FarWorldMusicalSound backgroundMusic() {
		// TODO Auto-generated method stub
		return MusicTable.abyssalZoneBackground;
	}

	@Override
	public FarWorldMusicalSound desperationMusic() {
		// TODO Auto-generated method stub
		return MusicTable.oceanicAbyssDesperation;
	}

}
