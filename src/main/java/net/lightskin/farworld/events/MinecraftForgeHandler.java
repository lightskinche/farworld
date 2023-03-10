package net.lightskin.farworld.events;

import org.apache.logging.log4j.Level;

import io.github.opencubicchunks.cubicchunks.api.world.IColumn;
import io.github.opencubicchunks.cubicchunks.api.world.ICube;
import net.lightskin.farworld.FarWorld;
import net.lightskin.farworld.world.underground.Layer;
import net.lightskin.farworld.world.underground.manager.LayerManager;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ChunkProviderServer;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.client.event.sound.PlaySoundSourceEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.ChunkWatchEvent;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class MinecraftForgeHandler {
	public MinecraftForgeHandler() {
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	/*@SubscribeEvent
	public void LayerTickWorld(ChunkWatchEvent.Watch event) { //test if this works
		Chunk a = event.getChunkInstance();
		IColumn column = (IColumn)a;
		for(ICube cube : column.getLoadedCubes()) {
			Layer tmp = LayerManager.getLayer(cube.getY()); //make sure that .getY() isn't acting wonky, that it gives CUBE COORDS
			if(tmp != null) {
				tmp.tickCube(cube);
			}
		}
	}*/
	@SubscribeEvent
	public void onSoundPlayed(PlaySoundEvent event) {
	  if (event.getSound().getCategory() == SoundCategory.MUSIC) {
		  //FarWorld.logger.printf(Level.INFO, "Trying to play %s.\n", event.getSound().getSoundLocation().toString());
		  //disable logging for now, we know this gets called
		  if(CommonHandler.curMusic != null && CommonHandler.curMusic != event.getSound()) {
			  event.setResultSound(null);
		  }
	  }
	}
	//TODO: add fog handling here so that we can set fog and stuff (see weather2)
}
