package net.lightskin.farworld.world.underground;

import java.util.ArrayList;

import io.github.opencubicchunks.cubicchunks.api.world.ICube;
import net.lightskin.farworld.world.underground.manager.LayerManager;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;

//regions -> layers -> cave biomes
public abstract class Region {
	public Region() {
		
	}
	//so all layers in a region can have the same ores if a certain ore makes an apperance there
	public abstract OreEntry[] regionalOres();
	public abstract Section[] regionalSections();
	public Section getSection(int y) {
		for(Section a: regionalSections()) {
			if(a.beginHeight() >= y && a.endHeight() <= y)
				return a;
		}
		return null;
	}
	public abstract double regionalStability();
	public abstract Biome refrenceBiome();
	public abstract IBlockState regionalFillerBlock();
 	public abstract IBlockState[] regionDisabledBlocks();
 	//will max between this and layer will be chosen for section to add to
	public float getTemperatureLavaUnits() { //air temp in lu-- lava units
		return 0.05f; //recall that 0.5 = temp of fire, 0 = 'normal'
	}
 	
	public void fill(ICube cube) {
		Layer layer = LayerManager.getLayer(cube.getY());
		if(regionDisabledBlocks() != null || layer != null || regionalFillerBlock() != Blocks.STONE.getDefaultState()) //if we have actual reason to 'fill'
		for(int i = 0; i < 16; i++) 
			for(int j = 0; j < 16; j++)
				for(int l = 0; l < 16; l++) {
					if(regionDisabledBlocks() != null) //not all layers have disabled blocks
						for(IBlockState block : regionDisabledBlocks())
							if(cube.getBlockState(new BlockPos(i,j + cube.getY() * 16,l)) == block)
								cube.setBlockState(new BlockPos(i,j + cube.getY() * 16,l), regionalFillerBlock());
					if(layer != null)
						if(layer.disabledBlocks() != null) //not all layers have disabled blocks
							for(IBlockState block : layer.disabledBlocks())
								if(cube.getBlockState(new BlockPos(i,j + cube.getY() * 16,l)) == block)
									cube.setBlockState(new BlockPos(i,j + cube.getY() * 16,l), regionalFillerBlock());
					if(cube.getBlockState(new BlockPos(i,j + cube.getY() * 16,l)) == Blocks.STONE.getDefaultState()) //replace overworld filler block, stone
						cube.setBlockState(new BlockPos(i,j + cube.getY() * 16,l), regionalFillerBlock());
				}
	}
}
