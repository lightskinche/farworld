package net.lightskin.farworld.world.underground.manager;

import net.lightskin.farworld.world.underground.Layer;
import net.lightskin.farworld.world.underground.layers.*;
import net.lightskin.farworld.world.underground.layers.falls.FallsLayer;
import net.lightskin.farworld.world.underground.layers.hardware.HardwareLayer;

public class LayerManager {
	public static Layer[] layerList = {
			new HardwareLayer(2,2),
			new FallsLayer(0,12)
			};
	
	public static Layer getLayer(int y) {
		for (Layer l : layerList){
			if(l.beginHeight() >= y && l.endHeight() <= y)
				return l;
		}
		return null;
	}
}
