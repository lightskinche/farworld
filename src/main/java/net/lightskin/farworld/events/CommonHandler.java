package net.lightskin.farworld.events;

import io.github.opencubicchunks.cubicchunks.api.world.IColumn;
import io.github.opencubicchunks.cubicchunks.api.world.ICube;
import net.lightskin.farworld.blocks.FarWorldBlocks;
import net.lightskin.farworld.blocks.GasBlockBase;
import net.lightskin.farworld.effects.FarWorldPotions;
import net.lightskin.farworld.sound.FarWorldMusicalSound;
import net.lightskin.farworld.sound.MusicTable;
import net.lightskin.farworld.world.MusicalBiomeAboveGround;
import net.lightskin.farworld.world.MusicalBiomeBase;
import net.lightskin.farworld.world.underground.Layer;
import net.lightskin.farworld.world.underground.manager.LayerManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.MusicTicker;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ChunkProviderServer;
import net.minecraftforge.client.event.EntityViewRenderEvent;
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
	@SideOnly(Side.CLIENT)
	public static FarWorldMusicalSound cur_music;
	@SideOnly(Side.CLIENT)
	public static Biome cur_biome;
	//public static boolean underground = false;
	//TODO: test this
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void LayerTickPlayer(TickEvent.PlayerTickEvent event) {
		EntityPlayer player = event.player;
		if(player.dimension == 0) {
        	Layer tmp = LayerManager.getLayer(player.chunkCoordY);
        	if(tmp != null) {
                tmp.affectPlayer(player);
        	}
        	cur_music = null;
        	Biome tmpa = player.getEntityWorld().getBiome(new BlockPos(player.posX,player.posY,player.posZ));
        	cur_biome = tmpa;
        	if(tmpa instanceof MusicalBiomeBase) {
        		if(player.getHealth() <= player.getMaxHealth() / 5)
        			cur_music = ((MusicalBiomeBase)tmpa).desperationMusic();
        		else if(player.getEntityWorld().calculateSkylightSubtracted(0) < 7) //don't know why this is the other way around, but it works
        			cur_music = ((MusicalBiomeBase)tmpa).backgroundMusic();
        		else if(tmpa instanceof MusicalBiomeAboveGround)
        			cur_music = ((MusicalBiomeAboveGround)tmpa).nightMusic();
        	}
        	else if(player.getHealth() <= player.getMaxHealth() / 5)
        		cur_music = MusicTable.defaultDesperation;
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
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onFogDensity(EntityViewRenderEvent.FogDensity event) {
	    if (event.getEntity().posY < 0) {
	        // We set a high density, but we need to control WHERE it starts.
	        // Because 1.12.2 fixed-function pipeline fog is radial, 
	        // to get 'vertical' fog, you actually need a custom shader or 
	        // to manipulate the Fog Start/End based on the camera angle.
	        
	        event.setDensity(0.5F); 
	        event.setCanceled(true);
	    }
	}

	/*@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onRenderFog(EntityViewRenderEvent.RenderFogEvent event) {
	    if (event.getEntity().posY < 0) {
	        float limit = 48.0F; // 3 chunks
	        
	        // This is the trick: 
	        // We set the fog to be linear and end at our vertical limit.
	        GlStateManager.setFog(GlStateManager.FogMode.LINEAR);
	        
	        // To make it feel "Vertical", we must ensure horizontal distance 
	        // is ignored. In standard GL, this is impossible without a shader.
	        // HOWEVER, you can approximate it by adjusting the FogEnd based on Pitch:
	        
	        double pitch = Math.toRadians(Math.abs(event.getEntity().rotationPitch));
	        float verticalFactor = (float) Math.sin(pitch);
	        
	        // When looking straight up/down, fog ends at 48.
	        // When looking at the horizon, fog ends at infinity (disappears).
	        float dynamicEnd = limit / (verticalFactor + 0.001F);
	        
	        GlStateManager.setFogStart(dynamicEnd * 0.5F);
	        GlStateManager.setFogEnd(dynamicEnd);
	    }
	}*/

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onFogColor(EntityViewRenderEvent.FogColors event) {
	    double playerY = event.getEntity().posY;
	    if (playerY <= 0) {
	        //player is in the first (real) layer, so let's make the fog completely black
	        event.setRed(0.0F);
	        event.setGreen(0.0F);
	        event.setBlue(0.0F);
	    }
	}
	/*@SideOnly(Side.CLIENT)
	@SubscribeEvent //messy sound code but works well enough, pitch 8 default btw
	public void ClientTick(TickEvent.ClientTickEvent event) {
	    if (event.phase == TickEvent.Phase.START) {
    		// client tick start,do thihg here
	    	if(cur_music != null)
	    		MusicTable.tryPlayMusic(cur_music);
	    	else {
	    		if(MusicTable.last != null)
	    			MusicTable.sh.stopSound(MusicTable.last);
	    		MusicTable.last = null;
	    	}
	    }
	}*/
	
	@SubscribeEvent //slarb
	@SideOnly(Side.CLIENT)
	public void ClientTick(TickEvent.ClientTickEvent event) {
		Minecraft mc = Minecraft.getMinecraft();
	    if (event.phase == TickEvent.Phase.START && mc.world != null && mc.player != null) {
	        if (mc.player.posY < 0) {
	            mc.world.setWorldTime(19000L);
	        }
	    }
	}
}
