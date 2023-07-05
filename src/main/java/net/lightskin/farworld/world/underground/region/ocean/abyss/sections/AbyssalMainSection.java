package net.lightskin.farworld.world.underground.region.ocean.abyss.sections;

import java.util.HashMap;

import io.github.opencubicchunks.cubicchunks.api.world.ICube;
import net.lightskin.farworld.blocks.FarWorldBlocks;
import net.lightskin.farworld.world.WorldRegister;
import net.lightskin.farworld.world.underground.CaveBiome;
import net.lightskin.farworld.world.underground.OreEntry;
import net.lightskin.farworld.world.underground.Region;
import net.lightskin.farworld.world.underground.Section;
import net.lightskin.farworld.world.underground.manager.RegionManager;
import net.lightskin.farworld.world.underground.region.ocean.abyss.AbyssRegion;
import net.lightskin.farworld.world.underground.region.test.TestRegion;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.Biome;

public class AbyssalMainSection extends Section{
	private CaveBiome[] biomes = new CaveBiome[] {WorldRegister.abyssalZoneBiome};
	public AbyssalMainSection(int begin_in, int length_in) {
		super(begin_in, length_in);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Region sectionalRegion() {
		// TODO Auto-generated method stub
		return new AbyssRegion();
	}

	@Override
	public CaveBiome sectionalBiome(double pressure) {
		// TODO Auto-generated method stub
		return biomes[0];
	}

	@Override
	public CaveBiome[] sectionalBiomes() {
		// TODO Auto-generated method stub
		return biomes;
	}
	
	@Override
	public IBlockState sectionalFillerBlock(double pressure) {
		return Blocks.WATER.getDefaultState();
	}

	@Override
	public HashMap<Biome, OreEntry[]> sectionalOres() {
		// TODO Auto-generated method stub
		HashMap<Biome, OreEntry[]> a = new HashMap<Biome, OreEntry[]>();
		//a.put(WorldRegister.section, new OreEntry[] {new OreEntry(FarWorldBlocks.tinOre.getDefaultState(),5,5,256f / 32f / (256f / ICube.SIZE))});
		//a.put(WorldRegister.low, new OreEntry[] {new OreEntry(Blocks.BOOKSHELF.getDefaultState(),5,5,256f / 32f / (256f / ICube.SIZE))});
		return a;
	}

}
