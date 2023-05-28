package net.lightskin.farworld.blocks;

import net.minecraft.block.Block;

import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.lightskin.farworld.gases.*;

public class FarWorldBlocks {
	//gases
	public static Block decayGas;
	public static Block nitrogenGas;
	
	//stone variations
	public static Block hardStone;
	public static Block limestone;
	//?
	public static Block infectedStone;
	public static Block frozenCobblestone;
	public static Block forzenStone;
	
	//ores a
	public static OreBlockBase copperOre;
	public static OreBlockBase tinOre;
	public static OreBlockBase nitrogenOre;
	public static OreBlockBase lithiumOre;
	public FarWorldBlocks() {
		//gases
		decayGas = new DecayGas("decay_gas");
		nitrogenGas = new NitrogenGas("nitrogen_gas");
		//stone varations
		hardStone = new BlockBase("hard_stone", Material.ROCK);
		//ores
		copperOre = new OreBlockBase("copper_ore", Material.ROCK, 10); 
		tinOre = new OreBlockBase("tin_ore", Material.ROCK, 20);
		
		nitrogenOre = new OreBlockBase("nitrogen_ore", Material.ROCK, 50);
		nitrogenOre.setHardness(20).setHarvestLevel("pickaxe", 3);
		nitrogenOre.ingot.setMaxDamage(1024);
		//goofy ahh ore
		lithiumOre = new OreBlockBase("lithium_ore", Material.ROCK, 50);
	}
}
