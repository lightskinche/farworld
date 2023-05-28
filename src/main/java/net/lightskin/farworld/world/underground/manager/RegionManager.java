package net.lightskin.farworld.world.underground.manager;

import java.util.HashMap;
import io.github.opencubicchunks.cubicchunks.api.util.Coords;
import io.github.opencubicchunks.cubicchunks.api.util.CubePos;
import io.github.opencubicchunks.cubicchunks.api.world.ICube;
import net.lightskin.farworld.FarWorld;
import net.lightskin.farworld.world.biomes.RegionEnforcer;
import net.lightskin.farworld.world.type.FarWorldTerrianGenerator;
import net.lightskin.farworld.world.underground.CaveBiome;
import net.lightskin.farworld.world.underground.Region;
import net.lightskin.farworld.world.underground.gradient.StabilityGradient;
import net.lightskin.farworld.world.underground.region.AbyssRegion;
import net.lightskin.farworld.world.underground.region.TestRegion;
import net.minecraft.init.Biomes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.biome.Biome.TempCategory;

public class RegionManager{
	public static HashMap<TempSuperCategory, Region[]> regionMap = new HashMap<TempSuperCategory, Region[]>();
	static {
		regionMap.put(TempSuperCategory.MEDIUM, new Region[] {});
		regionMap.put(TempSuperCategory.COLD, new Region[] {});
		regionMap.put(TempSuperCategory.WARM, new Region[] {});
		regionMap.put(TempSuperCategory.OCEAN, new Region[] {new TestRegion()}); //ocean has radiation i guess
		regionMap.put(TempSuperCategory.ABYSS, new Region[] {new AbyssRegion()});
	}
	/* dont use this one public static Region getRegion(ICube cube) {
		TempCategory a = cube.getWorld().getBiome(Coords.getCubeCenter(cube)).getTempCategory();
		Region closest = null;
		double stability = FarWorldTerrianGenerator.stability.getValue(cube), margin = 99999;
		if(regionMap.get(a) != null) {
		for(Region r : regionMap.get(a)) {
			double dif = Math.abs(r.regionalStability() - stability);
			if(dif < margin) {
				margin = dif;
				closest = r;
			}
		}
		
		return closest;
		}
		else
			return null;
	}*/
	
	public static Region getRegion(CubePos pos, World world, double stability, double pressure) {
		TempCategory c = world.getBiome(new BlockPos((pos.getX() + 7) * 16, 64, (pos.getZ() + 7) * 8)).getTempCategory();
		TempSuperCategory a = TempSuperCategory.NONE;
		Biome tmpa = RegionEnforcer.enforce(pos.getX() * 16, pos.getX() * 16);
		if(RegionEnforcer.specialCategory.get(tmpa) != null)
			a = RegionEnforcer.specialCategory.get(tmpa);
		else if(c == TempCategory.COLD)
			a = TempSuperCategory.COLD;
		else if(c == TempCategory.MEDIUM)
			a = TempSuperCategory.MEDIUM;
		else if(c == TempCategory.OCEAN)
			a = TempSuperCategory.OCEAN;
		else if(c == TempCategory.WARM)
			a = TempSuperCategory.WARM;
		Region closest = null;
		double margin = 99999;
		/*FarWorld.logger.info("x: " + pos.getX() * 16 + " y: " + pos.getY() + " z: " + pos.getZ() + " stab: " + stability +
				" pres: " + pressure);*/ //remove logging for now
		if(regionMap.get(a) != null) {
		for(Region r : regionMap.get(a)) {
			double dif = Math.abs(r.regionalStability() - stability);
			if(dif < margin) {
				margin = dif;
				closest = r;
			}
		}
		
		return closest;
		}
		else
			return null;
	}
	public enum TempSuperCategory {
		//vanilla
		COLD,
		MEDIUM,
		OCEAN,
		WARM,
		//custom
		NONE,
		ABYSS
	}
}
