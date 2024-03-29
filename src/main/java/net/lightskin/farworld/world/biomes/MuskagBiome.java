package net.lightskin.farworld.world.biomes;

import net.lightskin.farworld.FarWorld;
import net.lightskin.farworld.blocks.FarWorldBlocks;
import net.lightskin.farworld.sound.FarWorldMusicalSound;
import net.lightskin.farworld.sound.MusicTable;
import net.lightskin.farworld.world.MusicalBiomeAboveGround;
import net.lightskin.farworld.world.MusicalBiomeBase;
import net.lightskin.farworld.world.underground.CaveBiomeDecorator;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.BiomeProperties;

public class MuskagBiome extends OverworldBiomeSpecial implements MusicalBiomeAboveGround{
	public MuskagBiome() {
		super(new BiomeProperties("Muskag")
            	.setBaseHeight(0.0F)
            	.setHeightVariation(0.0F)
            	.setSnowEnabled()
            	.setTemperature(-2.0F)
            	.setWaterColor(0xFF0000) //for testing, this is red water
    			);
		this.topBlock = Blocks.SNOW.getDefaultState();
		this.fillerBlock = FarWorldBlocks.hardStone.getDefaultState();
		this.setRegistryName(FarWorld.MODID,"muskag");
	}
	@Override
	public void decorate(World world, java.util.Random random, BlockPos pos) {
		float x = pos.getX();
		float y = pos.getY();
		float z = pos.getZ();
		//create tree
		world.setBlockState(new BlockPos(x, y, z), Blocks.LOG.getDefaultState());
		world.setBlockState(new BlockPos(x, y + 1, z), Blocks.LOG.getDefaultState());
		world.setBlockState(new BlockPos(x, y + 2, z), Blocks.LOG.getDefaultState());
		world.setBlockState(new BlockPos(x - 1, y + 1, z), Blocks.LOG.getDefaultState());
		world.setBlockState(new BlockPos(x + 1, y + 1, z), Blocks.LOG.getDefaultState());
		world.setBlockState(new BlockPos(x - 1, y + 2, z), Blocks.LOG.getDefaultState());
		world.setBlockState(new BlockPos(x + 1, y + 2, z), Blocks.LOG.getDefaultState());
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
	public FarWorldMusicalSound nightMusic() {
		// TODO Auto-generated method stub
		return MusicTable.muskagNight;
	}
	@Override
	public FarWorldMusicalSound backgroundMusic() {
		// TODO Auto-generated method stub
		return MusicTable.muskagBackground;
	}
	@Override
	public FarWorldMusicalSound desperationMusic() {
		// TODO Auto-generated method stub
		return MusicTable.muskagDesperation;
	}
}
