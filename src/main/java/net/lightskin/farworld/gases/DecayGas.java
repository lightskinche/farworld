package net.lightskin.farworld.gases;

import net.lightskin.farworld.blocks.FarWorldBlocks;
import net.lightskin.farworld.blocks.GasBlockBase;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class DecayGas extends GasBlockBase{

	public DecayGas(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}
	@Override
	public boolean canMove(BlockPos desiredPos, IBlockState blockInWay, IBlockState gasState) {
		Block a = blockInWay.getBlock();
		if(a == FarWorldBlocks.hardStone || a instanceof BlockBush || a == Blocks.SNOW_LAYER)
			return true;
		return false;
	}
	
	@Override
	public float getWeight() {
		return -1; //light
	}
	
	@Override
	public float getSpeed() {
		return 0.95f;
	}
	
	@Override
	public float getActingSpeed() {
		return 0;
	}
	
	@Override
	public void affectBarrier(World world, IBlockState barrier, BlockPos barrierPos, IBlockState gasState) {
		if(barrier.getBlock() == Blocks.GRASS) {
			world.setBlockState(barrierPos, Blocks.DIRT.getDefaultState());
			return;
		}
		else if(barrier.getBlock() == Blocks.DIRT) {
			world.setBlockState(barrierPos, Blocks.STONE.getDefaultState());
			return;
		}
		else if(barrier.getBlock() == Blocks.STONE) {
			world.setBlockState(barrierPos, FarWorldBlocks.hardStone.getDefaultState());
			return;
		}
	}
}
