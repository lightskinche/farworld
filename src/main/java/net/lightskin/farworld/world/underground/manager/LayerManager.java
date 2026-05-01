package net.lightskin.farworld.world.underground.manager;

import net.lightskin.farworld.world.underground.Layer;
import net.lightskin.farworld.world.underground.layers.*;
import net.lightskin.farworld.world.underground.layers.arena.ArenaLayer;
import net.lightskin.farworld.world.underground.layers.falls.FallsLayer;
import net.lightskin.farworld.world.underground.layers.hardware.HardwareLayer;
import net.lightskin.farworld.world.underground.layers.heat.HeatLayer;
import net.lightskin.farworld.world.underground.layers.maze.MazeLayer;
import net.lightskin.farworld.world.underground.layers.plains.PlainsLayer;

/*
 * TODO:
fix rain not showing up anywhere
fix worldgen being comically slow
muskag -> polar desert -> alps, cross do there
bedrock wall -> oceanic abyss
golden palace at y = +1 billion
biomes that can set fog thickness and color (white for polar stuff, black for most caves, and red or something if fake-nether layer made)
 * 
 */
//FAR WORLD should be at -62500000 (chunk Y), aka -1 billion block Y.
public class LayerManager {
	//REMEMBER: it's (START, DEPTH)
	public static Layer[] layerList = {
			new HardwareLayer(2,2),
			new MazeLayer(0,32),
			new FallsLayer(-32,63), //aka Substratum
			new ArenaLayer(-95,20),
			new PlainsLayer(-115,8)//,
			//new HeatLayer(-111,6)
			};
	
	public static Layer getLayer(int y) {
		for (Layer l : layerList){
			if(l.beginHeight() >= y && l.endHeight() <= y)
				return l;
		}
		return null;
	}
}
