package net.lightskin.farworld.world.underground.layers.plains;

import java.util.Random;
import io.github.opencubicchunks.cubicchunks.api.util.CubePos;
import io.github.opencubicchunks.cubicchunks.api.worldgen.CubePrimer;
import io.github.opencubicchunks.cubicchunks.cubicgen.customcubic.CustomGeneratorSettings;
import io.github.opencubicchunks.cubicchunks.cubicgen.customcubic.structure.IFlexHandlerStructureGenerator;
import net.lightskin.farworld.blocks.FarWorldBlocks;
import net.lightskin.farworld.world.WorldRegister;
import net.lightskin.farworld.world.underground.Layer;
import net.lightskin.farworld.world.underground.OreEntry;
import net.lightskin.farworld.world.underground.generators.maze.MazeCaveGenerator;
import net.lightskin.farworld.world.underground.generators.maze.MazeRavineGenerator;
import net.minecraft.block.BlockStone;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.Biome;

public class PlainsLayer extends Layer{

	public PlainsLayer(int begin_in, int length_in) {
		super(begin_in, length_in);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Biome refrenceBiome() {
		// TODO Auto-generated method stub
		return WorldRegister.plainsReferenceBiome;
	}
	@Override
	public IBlockState fillerBlock() { //like how filler blocks work in dims
		return Blocks.AIR.getDefaultState();
	}
	@Override
	public void special(CubePrimer cube, CubePos pos) {
		if(pos.getMaxBlockY() <= -1872) {
			for (int y = 0; y < 16; y++) {
			    for (int z = 0; z < 16; z++) {
			        for (int x = 0; x < 16; x++) {
			            cube.setBlockState(x, y, z, Blocks.BEDROCK.getDefaultState());
			        }
			    }
			}
		}
	}
	@Override
	public IFlexHandlerStructureGenerator refrenceCaves() {
		return new MazeCaveGenerator(); //this doesn't matter there are no caves here
	}
	@Override
	public IFlexHandlerStructureGenerator refrenceRavines(CustomGeneratorSettings conf) {
		return new MazeRavineGenerator(conf); //same it also doesn't matter
	}
}
