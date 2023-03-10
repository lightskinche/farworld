package net.lightskin.farworld.world.underground.manager;

import net.lightskin.farworld.world.underground.Layer;
import net.lightskin.farworld.world.underground.layers.*;

public class LayerManager {
	public static Layer[] layerList = {
			new TestLayer(0, 20)
			//the hardpoint

			//the heat
			};
	
	public static Layer getLayer(int y) {
		for (Layer l : layerList){
			if(l.beginHeight() > y && l.endHeight() < y)
				return l;
		}
		return null;
	}
}
