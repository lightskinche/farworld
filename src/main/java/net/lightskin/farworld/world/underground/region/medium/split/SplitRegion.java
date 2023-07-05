package net.lightskin.farworld.world.underground.region.medium.split;

import net.lightskin.farworld.world.WorldRegister;
import net.lightskin.farworld.world.underground.OreEntry;
import net.lightskin.farworld.world.underground.Region;
import net.lightskin.farworld.world.underground.Section;
import net.lightskin.farworld.world.underground.region.medium.split.sections.UnheldSection;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.Biome;

public class SplitRegion extends Region{
	private static Section[] sections = {
			new UnheldSection(2,2)
	};
	@Override
	public OreEntry[] regionalOres() {
		// TODO Auto-generated method stub
		return null;
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
		return WorldRegister.splitRefrenceBiome;
	}

	@Override
	public IBlockState regionalFillerBlock() {
		// TODO Auto-generated method stub
		return Blocks.STONE.getDefaultState();
	}

	@Override
	public IBlockState[] regionDisabledBlocks() {
		// TODO Auto-generated method stub
		return null;
	}

}
