package net.lightskin.farworld.world.underground.manager;

import net.lightskin.farworld.world.underground.Layer;
import net.lightskin.farworld.world.underground.layers.*;
import net.lightskin.farworld.world.underground.layers.hardware.HardwareLayer;

public class LayerManager {
	public static Layer[] layerList = {
			//new TestLayer(20, 8),
			//new TestLayer(2, 20)
			//layer 1 should be asbestos and dust ores
			//standard vanilla
			new HardwareLayer(2,2)
			};
	
	public static Layer getLayer(int y) {
		for (Layer l : layerList){
			if(l.beginHeight() >= y && l.endHeight() <= y)
				return l;
		}
		return null;
	}
}
