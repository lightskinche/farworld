package net.lightskin.farworld.world.underground.region.ocean.abyss;

import net.lightskin.farworld.sound.FarWorldMusicalSound;
import net.lightskin.farworld.sound.MusicTable;
import net.lightskin.farworld.world.MusicalBiomeBase;
import net.lightskin.farworld.world.underground.CaveBiome;

public class AbyssRefrenceBiome extends CaveBiome implements MusicalBiomeBase{

	public AbyssRefrenceBiome(String name, float temp) {
		super(name, temp);
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
		return MusicTable.abyssalZoneBackground;
	}

	@Override
	public FarWorldMusicalSound desperationMusic() {
		// TODO Auto-generated method stub
		return MusicTable.abyssalZoneDesperation;
	}

}
