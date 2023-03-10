package net.lightskin.farworld.world.underground;

import io.github.opencubicchunks.cubicchunks.api.util.CubePos;
import io.github.opencubicchunks.cubicchunks.api.world.ICube;
import io.github.opencubicchunks.cubicchunks.cubicgen.customcubic.CustomGeneratorSettings;
import io.github.opencubicchunks.cubicchunks.cubicgen.customcubic.builder.IBuilder;
import io.github.opencubicchunks.cubicchunks.cubicgen.customcubic.builder.NoiseSource;
import net.lightskin.farworld.world.type.FarWorldTerrianGenerator;

//TODO: find a way to customize gradients
public class Gradient {
	private long seed;
	
	public Gradient(long seed_in) {
		seed = seed_in;
	}
	
	public double getValue(ICube cube) {
		return getValue(new CubePos(cube.getX(),cube.getY(),cube.getZ()));
	}
	public double getValue(CubePos cube) {
		return SimplexNoise.noise(cube.getX() / 0.5f, cube.getY() / 0.5f, cube.getZ() / 0.5f);
	}
}
