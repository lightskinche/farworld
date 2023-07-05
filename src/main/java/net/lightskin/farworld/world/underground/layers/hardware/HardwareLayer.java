package net.lightskin.farworld.world.underground.layers.hardware;

import net.lightskin.farworld.world.WorldRegister;
import net.lightskin.farworld.world.underground.Layer;
import net.minecraft.world.biome.Biome;

public class HardwareLayer extends Layer{

	public HardwareLayer(int begin_in, int length_in) {
		super(begin_in, length_in);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Biome refrenceBiome() {
		// TODO Auto-generated method stub
		return WorldRegister.hardwareRefrenceBiome;
	}

}
