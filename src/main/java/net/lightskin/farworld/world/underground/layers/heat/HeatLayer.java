package net.lightskin.farworld.world.underground.layers.heat;

import java.util.Random;
import io.github.opencubicchunks.cubicchunks.api.util.CubePos;
import io.github.opencubicchunks.cubicchunks.api.worldgen.CubePrimer;
import io.github.opencubicchunks.cubicchunks.cubicgen.customcubic.CustomGeneratorSettings;
import io.github.opencubicchunks.cubicchunks.cubicgen.customcubic.structure.IFlexHandlerStructureGenerator;
import net.lightskin.farworld.blocks.FarWorldBlocks;
import net.lightskin.farworld.world.WorldRegister;
import net.lightskin.farworld.world.underground.Layer;
import net.lightskin.farworld.world.underground.OreEntry;
import net.lightskin.farworld.world.underground.generators.heat.HeatCaveGenerator;
import net.lightskin.farworld.world.underground.generators.heat.HeatRavineGenerator;
import net.minecraft.block.BlockStone;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.Biome;

public class HeatLayer extends Layer{

	public HeatLayer(int begin_in, int length_in) {
		super(begin_in, length_in);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Biome refrenceBiome() {
		// TODO Auto-generated method stub
		return WorldRegister.heatReferenceBiome;
	}
	@Override
	public IBlockState fillerBlock() { //like how filler blocks work in dims
		return Blocks.STONE.getDefaultState();
	}
	@Override
	public OreEntry[] layerOres() {
		return new OreEntry[] {new OreEntry(Blocks.LAVA.getDefaultState(), 4, 4, 10)};
	}
	/*@Override
	public void special(CubePrimer cube, CubePos pos) {
		if(SimplexNoise.noise(pos.getX() * 16, pos.getZ() * 16) > -0.8f)
			return;
		Random rand = new Random();
		for (int y = 0; y < 16; y++) {
		    for (int z = 0; z < 16; z++) {
		        for (int x = 0; x < 16; x++) {
		            // Check if the (x, z) point is within the circle
		            double distance = Math.sqrt(Math.pow(x - 8, 2) + Math.pow(z - 8, 2)); // (8, 8) is the center of the circle
		            if (distance <= 7 - rand.nextInt(3)) {
		                cube.setBlockState(x, y, z, Blocks.AIR.getDefaultState());
		            }
		        }
		    }
		}
	}*/
	@Override
	public IFlexHandlerStructureGenerator refrenceCaves() {
		return new HeatCaveGenerator();
	}
	@Override
	public IFlexHandlerStructureGenerator refrenceRavines(CustomGeneratorSettings conf) {
		return new HeatRavineGenerator(conf);
	}
}
