package net.lightskin.farworld.world.underground.region.medium.split.sections;

import java.util.HashMap;

import io.github.opencubicchunks.cubicchunks.api.world.ICube;
import net.lightskin.farworld.blocks.FarWorldBlocks;
import net.lightskin.farworld.world.WorldRegister;
import net.lightskin.farworld.world.underground.CaveBiome;
import net.lightskin.farworld.world.underground.Layer;
import net.lightskin.farworld.world.underground.OreEntry;
import net.lightskin.farworld.world.underground.Region;
import net.lightskin.farworld.world.underground.Section;
import net.lightskin.farworld.world.underground.manager.LayerManager;
import net.lightskin.farworld.world.underground.region.medium.split.SplitRegion;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;

//WIND WIND SECTION IDK WIND CAVES NOISE DEBUFF
//WIND ERIOSION WINDED SAND IDK
public class UnheldSection extends Section{

	public UnheldSection(int begin_in, int length_in) {
		super(begin_in, length_in);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Region sectionalRegion() {
		// TODO Auto-generated method stub
		return new SplitRegion();
	}

	@Override
	public CaveBiome sectionalBiome(double pressure) {
		if(pressure <= 0.15)
			return WorldRegister.blackenedBiome;
		return WorldRegister.gravelPatchBiome;
	}
	
	@Override
	public void fill(ICube cube, double pressure) {
		Layer layer = LayerManager.getLayer(cube.getY());
		for(int i = 0; i < 16; i++) 
			for(int j = 0; j < 16; j++)
				for(int l = 0; l < 16; l++) {
					//maybe optimize this is worldgen is slower
					if(sectionalDisabledBlocks() != null) //not all sections have disabled blocks
						for(IBlockState block : sectionalDisabledBlocks())
							if(cube.getBlockState(new BlockPos(i,j + cube.getY() * 16,l)) == block)
								cube.setBlockState(new BlockPos(i,j + cube.getY() * 16,l), sectionalFillerBlock(pressure));
					if(sectionalRegion().regionDisabledBlocks() != null) //not all regions have disabled blocks
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
					//special code for this section
					if(cube.getBlockState(new BlockPos(i,j + cube.getY() * 16,l)) != Blocks.AIR.getDefaultState() && cube.getBlockState(new BlockPos(i,j + cube.getY() * 16,l)) != Blocks.CHEST.getDefaultState() && cube.getBlockState(new BlockPos(i,j + cube.getY() * 16,l)) != Blocks.MOB_SPAWNER.getDefaultState()) {
						if((cube.getBlockState(new BlockPos(i,j + cube.getY() * 16 + 1,l)) == Blocks.AIR.getDefaultState() && 
								cube.getBlockState(new BlockPos(i,j + cube.getY() * 16 - 1,l)) == Blocks.AIR.getDefaultState()) ||
								(cube.getBlockState(new BlockPos(i,j + cube.getY() * 16,l + 1)) == Blocks.AIR.getDefaultState() && 
								cube.getBlockState(new BlockPos(i,j + cube.getY() * 16,l - 1)) == Blocks.AIR.getDefaultState()) ||
								(cube.getBlockState(new BlockPos(i + 1,j + cube.getY() * 16,l)) == Blocks.AIR.getDefaultState() && 
								cube.getBlockState(new BlockPos(i - 1,j + cube.getY() * 16,l)) == Blocks.AIR.getDefaultState()))
							cube.setBlockState(new BlockPos(i,j + cube.getY() * 16,l), FarWorldBlocks.fracturedStone.getDefaultState());
					}
				}
	}
	
	@Override
	public IBlockState sectionalFillerBlock(double pressure) {
		if(pressure <= 0.45)
			FarWorldBlocks.hardStone.getDefaultState(); //change later
		return Blocks.STONE.getDefaultState();
	}
	
	@Override
 	public IBlockState[] sectionalDisabledBlocks() {
 		return null;
 	}

	@Override
	public CaveBiome[] sectionalBiomes() {
		// TODO Auto-generated method stub
		return new CaveBiome[] {WorldRegister.blackenedBiome,WorldRegister.gravelPatchBiome};
	}

	@Override
	public HashMap<Biome, OreEntry[]> sectionalOres() {
		HashMap<Biome, OreEntry[]> a = new HashMap<Biome, OreEntry[]>();
		a.put(WorldRegister.blackenedBiome, new OreEntry[] {new OreEntry(FarWorldBlocks.rupturedCoal.getDefaultState(), 10, 5, 0.25f)});
		a.put(WorldRegister.gravelPatchBiome, new OreEntry[] {new OreEntry(Blocks.GRAVEL.getDefaultState(), 15, 7, 0.25f)});
		return a;
	}

}
