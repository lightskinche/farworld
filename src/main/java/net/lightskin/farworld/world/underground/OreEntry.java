package net.lightskin.farworld.world.underground;

import net.minecraft.block.state.IBlockState;

public class OreEntry {
	public IBlockState ore;
	public int size, attempts;
	public float probability;
	public OreEntry(IBlockState in_ore, int in_size, int in_attempts, float in_probability) {
		ore = in_ore;
		size = in_size;
		attempts = in_attempts;
		probability = in_probability;
	}
}
