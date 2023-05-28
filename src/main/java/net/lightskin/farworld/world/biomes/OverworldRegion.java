package net.lightskin.farworld.world.biomes;

import net.minecraft.world.biome.Biome;

//y is actually z
public class OverworldRegion {
	private int x1, y1, x2, y2;
	private Biome biome;
	private boolean isCircular = false;
	public OverworldRegion(int ax1, int ay1, int ax2, int ay2, Biome abiome) {
		x1 = ax1; y1 = ay1; x2 = ax2; y2 = ay2;
		biome = abiome;
	}
	public OverworldRegion(int ax1, int ay1, int rad, Biome abiome) {
		x1 = ax1; y1 = ay1; x2 = rad;
		isCircular = true;
		biome = abiome;
	}
	public Biome returnBiome() {
		return biome;
	}
	public int[] getBounding() {
		return new int[] {x1,y1,x2,y2};
	}
	public boolean getCircular() {
		return isCircular;
	}
}
