package net.lightskin.farworld.world;

import net.lightskin.farworld.sound.FarWorldMusicalSound;

//adds custom music for differnet situations in this biome
public interface MusicalBiomeBase {
	public abstract FarWorldMusicalSound mobAttackMusic(); //when a player is attacks a mob this will be more of an orchestral hit then 'music'
	public abstract FarWorldMusicalSound mobAttackPlayerMusic(); //when a player is attacked by a mob
	public abstract FarWorldMusicalSound backgroundMusic(); //when a player is just in the biome (and healthy)
	public abstract FarWorldMusicalSound desperationMusic(); //when a player is about to die in the biome (low health)
}
