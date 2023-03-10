package net.lightskin.farworld.events;

import io.github.opencubicchunks.cubicchunks.api.world.IColumn;
import io.github.opencubicchunks.cubicchunks.api.world.ICube;
import net.lightskin.farworld.blocks.FarWorldBlocks;
import net.lightskin.farworld.blocks.GasBlockBase;
import net.lightskin.farworld.effects.FarWorldPotions;
import net.lightskin.farworld.world.underground.Layer;
import net.lightskin.farworld.world.underground.manager.LayerManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.MusicTicker;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ChunkProviderServer;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

//ALL EVENTS IN HERE MUST BE ON THE FMLCOMMONHANDLER BUS
public class CommonHandler {
	public CommonHandler() {
		FMLCommonHandler.instance().bus().register(this);
	}
	public static ISound curMusic = null, last = null;
	//TODO: test this
	@SubscribeEvent
	public void LayerTickPlayer(TickEvent.PlayerTickEvent event) {
		EntityPlayer player = event.player;
		if(player.dimension == 0) {
        	Layer tmp = LayerManager.getLayer(player.chunkCoordY);
        	if(tmp != null) {
                tmp.affectPlayer(player);
                curMusic = tmp.getMusic();
        	}
        	else {
        		last = curMusic;
        		curMusic = null;
        	}
		}
		if(player.getHeldItemMainhand().getItem() == FarWorldBlocks.nitrogenOre.ingot) {
			BlockPos plrBlock = new BlockPos((int)player.posX,(int)player.posY,(int)player.posZ);
			World world = player.getEntityWorld();
			if(((GasBlockBase)FarWorldBlocks.nitrogenGas).canMove(plrBlock, world.getBlockState(plrBlock), Blocks.ACACIA_DOOR.getDefaultState()) || world.isAirBlock(plrBlock)) {
				world.setBlockState(plrBlock, FarWorldBlocks.nitrogenGas.getDefaultState());
			}
			player.addPotionEffect(new PotionEffect(FarWorldPotions.coldBurnEffect, 60));
			player.getHeldItemMainhand().damageItem(1, player);
		}
	}
	@SubscribeEvent //messy sound code but works well enough, pitch 8 default btw
	public void ClientTick(TickEvent.ClientTickEvent event) {
	    if (event.phase == TickEvent.Phase.START) {
    		// client tick start,do thihg here
    		SoundHandler sh = Minecraft.getMinecraft().getSoundHandler();
	    	if(curMusic != null) {
	    		if(!sh.isSoundPlaying(curMusic)){
	    			sh.playSound(curMusic);
	    		}
	    	}
	    	if(last != null){
	    		if(sh.isSoundPlaying(last)) {
	    			sh.stopSound(last);
	    		}
	    	}
	    	curMusic = null; last = null;
	    }
	}
}
