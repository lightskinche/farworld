package net.lightskin.farworld.world.underground.gradient;

import io.github.opencubicchunks.cubicchunks.api.util.CubePos;
import net.lightskin.farworld.world.underground.Gradient;
import net.lightskin.farworld.world.underground.SimplexNoise;

public class PressureGradient extends Gradient{

	public PressureGradient(long seed) {
		super(seed);
		// TODO Auto-generated constructor stub
	}

	@Override
	public double getValue(CubePos cube) {
		return SimplexNoise.noise(cube.getX() / 10f, cube.getY() / 10f, cube.getZ() / 10f);
	}
}
