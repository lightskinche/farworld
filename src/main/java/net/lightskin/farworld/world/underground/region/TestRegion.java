package net.lightskin.farworld.world.underground.region;

import io.github.opencubicchunks.cubicchunks.api.world.ICube;
import net.lightskin.farworld.blocks.FarWorldBlocks;
import net.lightskin.farworld.world.WorldRegister;
import net.lightskin.farworld.world.underground.CaveBiome;
import net.lightskin.farworld.world.underground.OreEntry;
import net.lightskin.farworld.world.underground.Region;
import net.lightskin.farworld.world.underground.Section;
import net.lightskin.farworld.world.underground.sections.TestSection;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.Biome;

public class TestRegion extends Region{
	private Section[] sections = new Section[] {new TestSection(0, 20)};
	@Override
	public OreEntry[] regionalOres() {
		// TODO Auto-generated method stub
		return new OreEntry[] {new OreEntry(Blocks.BRICK_BLOCK.getDefaultState(), 9, 5, 256f / 32f / (256f / ICube.SIZE))};
	}

	@Override
	public Section[] regionalSections() {
		// TODO Auto-generated method stub
		return sections;
	}

	@Override
	public double regionalStability() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Biome refrenceBiome() {
		// TODO Auto-generated method stub
		return WorldRegister.region;
	}

	@Override
	public IBlockState regionalFillerBlock() {
		// TODO Auto-generated method stub
		return FarWorldBlocks.hardStone.getDefaultState();
	}

	@Override
	public IBlockState[] regionDisabledBlocks() {
		// TODO Auto-generated method stub
		return new IBlockState[] {Blocks.COAL_ORE.getDefaultState()};
	}

}
