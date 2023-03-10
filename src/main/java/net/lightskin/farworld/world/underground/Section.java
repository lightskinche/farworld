package net.lightskin.farworld.world.underground;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import io.github.opencubicchunks.cubicchunks.api.world.ICube;
import net.lightskin.farworld.world.underground.manager.LayerManager;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;

public abstract class Section {
	protected int begin = 0, length = 0;
	public Section(int begin_in, int length_in) {
		begin = begin_in; 
		length = length_in;
	}
	public int beginHeight() {
		return begin;
	}
	public int endHeight() {
		return begin - length;
	}
	public OreEntry[] parentOres() {
		ArrayList<OreEntry> masterList = new ArrayList<OreEntry>();
		masterList.add(new OreEntry(sectionalRegion().regionalFillerBlock(),11,5,256f / 128f / (256f / ICube.SIZE)));
		for(OreEntry tmp: sectionalRegion().regionalOres())
			masterList.add(tmp);
		Layer last = null; //prevents duplicate ore repitions by getting the same layer over and over; each layer registers its ores once like this
		for(int i = beginHeight(); i >= endHeight(); i--) {
			Layer tmp = LayerManager.getLayer(i);
			if(tmp != null && tmp != last) {
				for(OreEntry ore: tmp.layerOres())
					masterList.add(ore);
				masterList.add(new OreEntry(tmp.fillerBlock(),11,5,256f / 256f / (256f / ICube.SIZE))); //reference layers is rarer
			}
			last = tmp;
		}
		return masterList.toArray(new OreEntry[masterList.size()]);
	}
	public abstract Region sectionalRegion(); //first time we can look up
	public abstract CaveBiome sectionalBiome(double pressure);
	public abstract CaveBiome[] sectionalBiomes();
	public abstract HashMap<Biome,OreEntry[]> sectionalOres();
	public IBlockState sectionalFillerBlock(double pressure) {
		return Blocks.STONE.getDefaultState();
	}
 	public IBlockState[] sectionalDisabledBlocks() {
 		return null;
 	}
 	
	public void fill(ICube cube, double pressure) {
		Layer layer = LayerManager.getLayer(cube.getY());
		for(int i = 0; i < 16; i++) 
			for(int j = 0; j < 16; j++)
				for(int l = 0; l < 16; l++) {
					//maybe optimize this is worldgen is slower
					if(sectionalDisabledBlocks() != null) //not all layers have disabled blocks
						for(IBlockState block : sectionalDisabledBlocks())
							if(cube.getBlockState(new BlockPos(i,j + cube.getY() * 16,l)) == block)
								cube.setBlockState(new BlockPos(i,j + cube.getY() * 16,l), sectionalFillerBlock(pressure));
					if(sectionalRegion().regionDisabledBlocks() != null) //not all layers have disabled blocks
						for(IBlockState block : sectionalRegion().regionDisabledBlocks())
							if(cube.getBlockState(new BlockPos(i,j + cube.getY() * 16,l)) == block)
								cube.setBlockState(new BlockPos(i,j + cube.getY() * 16,l), sectionalFillerBlock(pressure));
					if(layer != null)
						if(layer.disabledBlocks() != null) //not all layers have disabled blocks
							for(IBlockState block : layer.disabledBlocks())
								if(cube.getBlockState(new BlockPos(i,j + cube.getY() * 16,l)) == block)
									cube.setBlockState(new BlockPos(i,j + cube.getY() * 16,l), sectionalFillerBlock(pressure));
					if(cube.getBlockState(new BlockPos(i,j + cube.getY() * 16,l)) == Blocks.STONE.getDefaultState()) //replace overworld filler block, stone
						cube.setBlockState(new BlockPos(i,j + cube.getY() * 16,l), sectionalFillerBlock(pressure)); //OVERRIDE IF SPECIFIC SECTION BIOMES NEED DIFFERENT BLOCKS
				}
	}
}
