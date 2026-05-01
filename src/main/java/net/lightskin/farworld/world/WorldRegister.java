package net.lightskin.farworld.world;

import net.lightskin.farworld.FarWorld;
import net.lightskin.farworld.world.biomes.FarWorldOverworldBiomes;
import net.lightskin.farworld.world.biomes.MuskagBiome;
import net.lightskin.farworld.world.type.FarWorldType;
import net.lightskin.farworld.world.underground.CaveBiome;
import net.lightskin.farworld.world.underground.layers.arena.ArenaReferenceBiome;
import net.lightskin.farworld.world.underground.layers.falls.FallsReferenceBiome;
import net.lightskin.farworld.world.underground.layers.hardware.HardwareRefrenceBiome;
import net.lightskin.farworld.world.underground.layers.heat.HeatReferenceBiome;
import net.lightskin.farworld.world.underground.layers.maze.MazeReferenceBiome;
import net.lightskin.farworld.world.underground.layers.plains.PlainsReferenceBiome;
import net.lightskin.farworld.world.underground.region.medium.split.SplitRefrenceBiome;
import net.lightskin.farworld.world.underground.region.medium.split.biomes.BlackenedBiome;
import net.lightskin.farworld.world.underground.region.medium.split.biomes.GravelPatchBiome;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.BiomeManager.BiomeEntry;
import net.minecraftforge.common.BiomeManager.BiomeType;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class WorldRegister {
	//layer references
	public static HardwareRefrenceBiome hardwareRefrenceBiome = new HardwareRefrenceBiome();
	public static FallsReferenceBiome fallsReferenceBiome = new FallsReferenceBiome();
	public static HeatReferenceBiome heatReferenceBiome = new HeatReferenceBiome();
	public static MazeReferenceBiome mazeReferenceBiome = new MazeReferenceBiome();
	public static ArenaReferenceBiome arenaReferenceBiome = new ArenaReferenceBiome();
	public static PlainsReferenceBiome plainsReferenceBiome = new PlainsReferenceBiome();
	//region references
	public static SplitRefrenceBiome splitRefrenceBiome = new SplitRefrenceBiome();

	//biome dump
	public static CaveBiome blackenedBiome = new BlackenedBiome();
	public static CaveBiome gravelPatchBiome = new GravelPatchBiome();
	
	public WorldRegister() {
		//layer refrences
		ForgeRegistries.BIOMES.register(hardwareRefrenceBiome.setRegistryName(FarWorld.MODID,"hardwareRefrenceBiome"));
		ForgeRegistries.BIOMES.register(fallsReferenceBiome.setRegistryName(FarWorld.MODID,"fallsReferenceBiome"));
		ForgeRegistries.BIOMES.register(heatReferenceBiome.setRegistryName(FarWorld.MODID,"heatReferenceBiome"));
		ForgeRegistries.BIOMES.register(mazeReferenceBiome.setRegistryName(FarWorld.MODID, "mazeReferenceBiome"));
		ForgeRegistries.BIOMES.register(arenaReferenceBiome.setRegistryName(FarWorld.MODID, "arenaReferenceBiome"));
		ForgeRegistries.BIOMES.register(plainsReferenceBiome.setRegistryName(FarWorld.MODID, "plainsReferenceBiome"));
		//region refrences
		ForgeRegistries.BIOMES.register(splitRefrenceBiome.setRegistryName(FarWorld.MODID,"splitRefrenceBiome"));

		//split region biomes
		ForgeRegistries.BIOMES.register(blackenedBiome.setRegistryName(FarWorld.MODID,"blackenedBiome"));
		ForgeRegistries.BIOMES.register(gravelPatchBiome.setRegistryName(FarWorld.MODID,"gravelPatchBiome"));
	}
}
