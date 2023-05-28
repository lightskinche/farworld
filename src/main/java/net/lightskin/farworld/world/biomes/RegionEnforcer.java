package net.lightskin.farworld.world.biomes;

import java.util.HashMap;

import io.github.opencubicchunks.cubicchunks.api.world.ICube;
import net.lightskin.farworld.world.underground.Region;
import net.lightskin.farworld.world.underground.manager.RegionManager;
import net.lightskin.farworld.world.underground.manager.RegionManager.TempSuperCategory;
import net.minecraft.world.biome.Biome;

//not related to region manager except it kinda is
public class RegionEnforcer {
	public static OverworldRegion[] enforces = {
			//in chunk coords
			new OverworldRegion(0,0,1000,null),
			new OverworldRegion(0,0,2000,FarWorldOverworldBiomes.muskagBiome),
			new OverworldRegion(12500,-50000000,15000,50000000,FarWorldOverworldBiomes.oceanicAbyssBiome),
			new OverworldRegion(10000,-50000000,20000,50000000,FarWorldOverworldBiomes.oceanicThresholdBiome)
			};
	public static HashMap<OverworldBiomeSpecial,TempSuperCategory> specialCategory = new HashMap<OverworldBiomeSpecial, TempSuperCategory>();
	
	public static void setRules() {
		specialCategory.put(FarWorldOverworldBiomes.muskagBiome,TempSuperCategory.COLD); //change idk doing water rn
		specialCategory.put(FarWorldOverworldBiomes.oceanicThresholdBiome, TempSuperCategory.OCEAN);
		specialCategory.put(FarWorldOverworldBiomes.oceanicAbyssBiome, TempSuperCategory.ABYSS);
	}
	private static boolean getInCircle(int locx, int locz, int centerx, int centerz, int rad) {
		if(Math.sqrt(Math.pow(locx - centerx, 2) + Math.pow(locz - centerz, 2)) <= rad)
			return true;
		return false;
	}
	public static Biome enforce(ICube cube) {
		for(int i = 0; i < enforces.length; i++) {
			OverworldRegion currentEnforce = enforces[i];
			int[] bounding = currentEnforce.getBounding();
			//in area
			if(!currentEnforce.getCircular()) {
			if(cube.getX() > bounding[0] && cube.getX() < bounding[2] && cube.getZ() > bounding[1] && cube.getZ() < bounding[3]) {
				return currentEnforce.returnBiome();
			}
			}
			else if(getInCircle(cube.getX(), cube.getZ(), bounding[0], bounding[1], bounding[2]))
				return currentEnforce.returnBiome();
		}
		return null;
	}
	public static Biome enforce(int x, int z) {
		for(int i = 0; i < enforces.length; i++) {
			OverworldRegion currentEnforce = enforces[i];
			int[] bounding = currentEnforce.getBounding();
			//in area
			if(!currentEnforce.getCircular()) {
			if(x > bounding[0] && x < bounding[2] && z > bounding[1] && z < bounding[3]) {
				return currentEnforce.returnBiome();
			}
			}
			else if(getInCircle(x,z,bounding[0],bounding[1],bounding[2]))
				return currentEnforce.returnBiome();
		}
		return null;
	}
}
