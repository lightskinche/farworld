package net.lightskin.farworld.world.underground.region.medium.split.biomes;

import net.lightskin.farworld.sound.FarWorldMusicalSound;
import net.lightskin.farworld.sound.MusicTable;
import net.lightskin.farworld.world.MusicalBiomeBase;
import net.lightskin.farworld.world.underground.CaveBiome;

public class GravelPatchBiome extends CaveBiome implements MusicalBiomeBase{

	public GravelPatchBiome() {
		super("Gravel Patch", 1.0f);
		// TODO Auto-generated constructor stub
	}

	@Override
	public FarWorldMusicalSound mobAttackMusic() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FarWorldMusicalSound mobAttackPlayerMusic() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FarWorldMusicalSound backgroundMusic() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FarWorldMusicalSound desperationMusic() {
		// TODO Auto-generated method stub
		return MusicTable.muskagNight;
	}

}
