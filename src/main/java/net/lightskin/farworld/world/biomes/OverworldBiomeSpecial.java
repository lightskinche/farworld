package net.lightskin.farworld.world.biomes;

import java.util.Random;

import org.apache.logging.log4j.Level;

import net.lightskin.farworld.FarWorld;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeDecorator;

public class OverworldBiomeSpecial extends Biome{

	public OverworldBiomeSpecial(BiomeProperties p_i46713_1_) {
		super(p_i46713_1_);
		this.flowers = null;
		this.decorator = new OverworldBiomeDecorator(); this.decorator.mushroomsPerChunk = 0;
		// TODO Auto-generated constructor stub
	}
	@Override
	public BiomeDecorator createBiomeDecorator() {
		this.decorator = new OverworldBiomeDecorator(); this.decorator.mushroomsPerChunk = 0;
		return this.decorator;
	}
	@Override
	public void decorate(World a, Random b, BlockPos c) {
		FarWorld.logger.log(Level.INFO, "Decorate(World,Random,BlockPos) was called");
		//nothing
	}
	//blocks to not have, default is those silly mushrooms i hate
	public IBlockState[] getBlockBlacklist() {
		return new IBlockState[] {Blocks.RED_MUSHROOM.getDefaultState(),Blocks.BROWN_MUSHROOM.getDefaultState(),Blocks.RED_MUSHROOM_BLOCK.getDefaultState(),Blocks.BROWN_MUSHROOM_BLOCK.getDefaultState()};
	}

}
