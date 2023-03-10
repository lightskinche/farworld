package net.lightskin.farworld.world.biomes;

import net.lightskin.farworld.world.underground.Layer;
import net.lightskin.farworld.world.underground.manager.LayerManager;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeDecorator;

public class MuskagBiomeDecorator extends BiomeDecorator{

	@Override
	public void decorate(World world, java.util.Random random, Biome biome, BlockPos pos) {
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
	
}
