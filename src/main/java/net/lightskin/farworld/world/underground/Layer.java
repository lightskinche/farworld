package net.lightskin.farworld.world.underground;

import java.util.Map;

import io.github.opencubicchunks.cubicchunks.api.util.CubePos;
import io.github.opencubicchunks.cubicchunks.api.world.ICube;
import io.github.opencubicchunks.cubicchunks.api.worldgen.CubePrimer;
import io.github.opencubicchunks.cubicchunks.cubicgen.customcubic.CustomGeneratorSettings;
import io.github.opencubicchunks.cubicchunks.cubicgen.customcubic.structure.IFlexHandlerStructureGenerator;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.audio.ISound;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

//notes
//256f / 32f / (256f / ICube.SIZE) - COMMON
//
public abstract class Layer {
	protected int begin = 0;
	protected int length = 0;
	
	public Layer(int begin_in,int length_in) {
		begin = begin_in;
		length = length_in;
	}

	public int beginHeight() {
		// TODO Auto-generated method stub
		return begin;
	}

	public int endHeight() {
		// TODO Auto-generated method stub
		return begin - length;
	}
	
	public IBlockState[] disabledBlocks() { //list of default blocks (granite, coal, iron) that have to be manually overriden
		return null;
	}
	
	public IBlockState fillerBlock() { //like how filler blocks work in dims
		return Blocks.STONE.getDefaultState();
	}
	public void fill(ICube cube) {
		
		if(disabledBlocks() != null || fillerBlock() != Blocks.STONE.getDefaultState()) //if we have an actual reason to 'fill' anything
		for(int i = 0; i < 16; i++) 
			for(int j = 0; j < 16; j++)
				for(int l = 0; l < 16; l++) {
					if(disabledBlocks() != null) //not all layers have disabled blocks
						for(IBlockState block : disabledBlocks())
							if(cube.getBlockState(new BlockPos(i,j + cube.getY() * 16,l)) == block)
								cube.setBlockState(new BlockPos(i,j + cube.getY() * 16,l), fillerBlock());
					if(cube.getBlockState(new BlockPos(i,j + cube.getY() * 16,l)) == Blocks.STONE.getDefaultState()) //replace overworld filler block, stone
						cube.setBlockState(new BlockPos(i,j + cube.getY() * 16,l), fillerBlock());
				}
	}
	
	public float getTemperatureLavaUnits() { //air temp in lu-- lava units
		return 0.05f; //recall that 0.5 = temp of fire, 0 = a cool day
	}
	
	public void decorate(World worldIn, java.util.Random random, Biome biome, BlockPos pos) {
		//nothing, can have a selector switch for biome; makes life easier since less files (decorators) will have to be managed
	}
	
	//tick functions, get called every tick
	
	public void affectPlayer(EntityPlayer player) {
		//does nothing by default, can be used to apply potion effects or otherwise, though
	}
	
	public void tickCube(ICube cube) { //doesnt work right now
		//does nothing by default, can be used to have custom behavior such as the evaporation of water; layers truly are their own dimensions now
	}
	
	//(end tick functions)
	
	public void special(CubePrimer cube, CubePos pos) {
		//does nothing by default, can be overriden to create structures or "hard" sections (no ores or caves)
	}
	public abstract Biome refrenceBiome(); //needs to be overriden
	
	public OreEntry[] layerOres() {
		return null;
	}
	//default is normal caves
	
	public IFlexHandlerStructureGenerator refrenceCaves() {
		return new FarWorldCaveGenerator();
	}
	public IFlexHandlerStructureGenerator refrenceRavines(CustomGeneratorSettings conf) {
		return new FarWorldRavineGenerator(conf);
	}
}
