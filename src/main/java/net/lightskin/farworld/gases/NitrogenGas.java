package net.lightskin.farworld.gases;

import java.util.Random;

import net.lightskin.farworld.blocks.FarWorldBlocks;
import net.lightskin.farworld.blocks.GasBlockBase;
import net.lightskin.farworld.effects.FarWorldPotions;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class NitrogenGas extends GasBlockBase{

	public NitrogenGas(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void affectEntityLiving(EntityLivingBase entity, IBlockState gasState, Random random) {
		entity.addPotionEffect(new PotionEffect(FarWorldPotions.coldBurnEffect, 60));
	}
	
	@Override
	public boolean canMove(BlockPos desiredPos, IBlockState blockInWay, IBlockState gasState) {
		Block a = blockInWay.getBlock();
		if(a instanceof BlockBush || a == Blocks.SNOW_LAYER)
			return true;
		return false;
	}
	
	@Override
	public float getWeight() {
		return -0.1f; //slightly lighter
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
		else if(!world.isAirBlock(barrierPos)){
			world.setBlockState(barrierPos, Blocks.ICE.getDefaultState());
		}
	}
	
}
