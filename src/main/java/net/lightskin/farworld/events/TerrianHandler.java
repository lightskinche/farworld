package net.lightskin.farworld.events;

import net.lightskin.farworld.world.type.FarWorldType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class TerrianHandler {
	public TerrianHandler() {
		MinecraftForge.TERRAIN_GEN_BUS.register(this);
	}
	@SubscribeEvent
	public void WinTheWar(DecorateBiomeEvent.Decorate event) {
	    // Replace 'YourWorldType' with the actual class name of your world type
	    if (event.getWorld().getWorldType() instanceof FarWorldType) {
	        if (event.getType() == DecorateBiomeEvent.Decorate.EventType.SHROOM) {
	            event.setResult(net.minecraftforge.fml.common.eventhandler.Event.Result.DENY);
	        }
	    }
	}
}
