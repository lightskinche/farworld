package net.lightskin.farworld.world.underground.layers.test;

import io.github.opencubicchunks.cubicchunks.api.util.CubePos;
import io.github.opencubicchunks.cubicchunks.api.world.ICube;
import io.github.opencubicchunks.cubicchunks.api.worldgen.CubePrimer;
import io.github.opencubicchunks.cubicchunks.cubicgen.customcubic.CustomGeneratorSettings;
import io.github.opencubicchunks.cubicchunks.cubicgen.customcubic.structure.IFlexHandlerStructureGenerator;
import net.lightskin.farworld.blocks.FarWorldBlocks;
import net.lightskin.farworld.sound.MusicTable;
import net.lightskin.farworld.world.WorldRegister;

import net.lightskin.farworld.world.underground.FarWorldCaveGenerator;
import net.lightskin.farworld.world.underground.Layer;
import net.lightskin.farworld.world.underground.OreEntry;
import net.lightskin.farworld.world.underground.region.test.biomes.TestBiome;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.audio.ISound;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.Biome;

public class TestLayer extends Layer{
	public TestLayer(int begin_in, int length_in) {
		super(begin_in, length_in);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Biome refrenceBiome() {
		// TODO Auto-generated method stub
		return WorldRegister.layer;
	}

	@Override
	public void special(CubePrimer cube, CubePos pos) {
		//now we may do as we please here, this is a test
		/*for(int i = 0; i < 16; i++) 
			for(int j = 0; j < 16; j++)
				for(int l = 0; l < 16; l++)
					cube.setBlockState(i, j, l, Blocks.AIR.getDefaultState());
		*/
	}
	
	@Override
	public IBlockState fillerBlock() { //like how filler blocks work in dims
		return Blocks.BEDROCK.getDefaultState();
	}
	
	@Override
	public OreEntry[] layerOres() {
		return new OreEntry[] {new OreEntry(Blocks.CLAY.getDefaultState(),9,2,256f / 32f / (256f / ICube.SIZE))};
	}

}
