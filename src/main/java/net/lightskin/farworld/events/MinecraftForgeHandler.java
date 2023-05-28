package net.lightskin.farworld.events;

import org.apache.logging.log4j.Level;

import io.github.opencubicchunks.cubicchunks.api.world.IColumn;
import io.github.opencubicchunks.cubicchunks.api.world.ICube;
import net.lightskin.farworld.FarWorld;
import net.lightskin.farworld.world.biomes.OverworldBiomeSpecial;
import net.lightskin.farworld.world.biomes.RegionEnforcer;
import net.lightskin.farworld.world.underground.Layer;
import net.lightskin.farworld.world.underground.manager.LayerManager;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ChunkProviderServer;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.client.event.sound.PlaySoundSourceEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;
import net.minecraftforge.event.world.ChunkWatchEvent;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
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
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onSoundPlayed(PlaySoundEvent event) {
	  if (event.getSound().getCategory() == SoundCategory.MUSIC && !CommonHandler.underground) {
		  //FarWorld.logger.printf(Level.INFO, "Trying to play %s.\n", event.getSound().getSoundLocation().toString());
		  //disable logging for now, we know this gets called
		  if(CommonHandler.curMusic != null && CommonHandler.curMusic != event.getSound()) {
			  event.setResultSound(null);
		  }
	  }
	  event.setResultSound(event.getSound());
	}
	//THEY WON'T WIN THE WAR
	/*@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void ChunkPopulatePost(PopulateChunkEvent.Post post) {
		Biome tmpa = RegionEnforcer.enforce(post.getChunkX() * 16, post.getChunkZ() * 16);
		FarWorld.logger.log(Level.INFO, tmpa.getBiomeName());
		if(tmpa != null) {
			Chunk abb = post.getWorld().getChunkFromChunkCoords(post.getChunkX(), post.getChunkZ());
			for(int i = 0; i < 16; i++) 
				for(int j = 60; j < 90; j++)
					for(int l = 0; l < 16; l++) {
						if(((OverworldBiomeSpecial)tmpa).getBlockBlacklist() != null) //not all layers have disabled blocks
							for(IBlockState block : ((OverworldBiomeSpecial)tmpa).getBlockBlacklist()) {
								FarWorld.logger.log(Level.INFO, block.toString());
								if(abb.getBlockState(new BlockPos(i,j,l)) == block)
									abb.setBlockState(new BlockPos(i,j,l), Blocks.STONE.getDefaultState());
							}
						if(abb.getBlockState(new BlockPos(i,j,l)) == Blocks.SNOW.getDefaultState()) //replace overworld filler block, stone
							abb.setBlockState(new BlockPos(i,j,l), Blocks.BEDROCK.getDefaultState());
					}
		}
	}*/
	//TODO: add fog handling here so that we can set fog and stuff (see weather2)
}
