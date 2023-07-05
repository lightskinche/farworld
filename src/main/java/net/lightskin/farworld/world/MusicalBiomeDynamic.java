package net.lightskin.farworld.world;

import java.util.HashMap;

import net.lightskin.farworld.sound.FarWorldMusicalSound;

public interface MusicalBiomeDynamic extends MusicalBiomeBase{
	public abstract HashMap<String,FarWorldMusicalSound> damageSourceMusic();
}
