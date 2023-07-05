package net.lightskin.farworld.world.underground.region.ocean.abyss;

import io.github.opencubicchunks.cubicchunks.api.world.ICube;
import net.lightskin.farworld.blocks.FarWorldBlocks;
import net.lightskin.farworld.world.WorldRegister;
import net.lightskin.farworld.world.underground.CaveBiome;
import net.lightskin.farworld.world.underground.OreEntry;
import net.lightskin.farworld.world.underground.Region;
import net.lightskin.farworld.world.underground.Section;
import net.lightskin.farworld.world.underground.region.ocean.abyss.sections.AbyssalMainSection;
import net.lightskin.farworld.world.underground.region.test.sections.TestSection;
import net.minecraft.block.BlockStone;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.Biome;

public class AbyssRegion extends Region{
	private Section[] sections = new Section[] {new AbyssalMainSection(4, 20)};
	@Override
	public OreEntry[] regionalOres() {
		// TODO Auto-generated method stub
		return new OreEntry[] {};
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
		return WorldRegister.abyssalZoneBiome;
	}

	@Override
	public IBlockState regionalFillerBlock() {
		// TODO Auto-generated method stub
		return Blocks.WATER.getDefaultState();
	}

	@Override
	public IBlockState[] regionDisabledBlocks() {
		// TODO Auto-generated method stub
		return new IBlockState[] {Blocks.STONE.getDefaultState(),
				Blocks.COAL_ORE.getDefaultState(),
				Blocks.IRON_ORE.getDefaultState(),
				Blocks.STONE.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.ANDESITE),
				Blocks.STONE.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.GRANITE),
				Blocks.STONE.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.DIORITE),
				Blocks.COBBLESTONE.getDefaultState(),
				Blocks.MOSSY_COBBLESTONE.getDefaultState(),
				Blocks.MOB_SPAWNER.getDefaultState(),
				Blocks.GRAVEL.getDefaultState(),
				Blocks.DIRT.getDefaultState(),
				Blocks.CLAY.getDefaultState(),
				Blocks.SAND.getDefaultState(),
				Blocks.BEDROCK.getDefaultState(),
				Blocks.AIR.getDefaultState(),
				FarWorldBlocks.hardStone.getDefaultState()}; //too lazy
	}

}
