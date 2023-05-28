package net.lightskin.farworld.world.biomes;

import java.util.Random;

import org.apache.logging.log4j.Level;

import net.lightskin.farworld.FarWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeDecorator;

public class OverworldBiomeDecorator extends BiomeDecorator {
	public OverworldBiomeDecorator() {
		
	}
	@Override
	public void decorate(World a, Random r, Biome b, BlockPos c) {
		FarWorld.logger.log(Level.INFO,"test");
	}
}
