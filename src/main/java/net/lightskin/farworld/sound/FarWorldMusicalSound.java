package net.lightskin.farworld.sound;

import net.lightskin.farworld.FarWorld;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.Sound;
import net.minecraft.client.audio.SoundEventAccessor;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;

public class FarWorldMusicalSound implements ISound{

	protected String name = "default";
	protected String displayname = "This Is A Placeholder";
	protected String loc = "null";
	protected float PITCH = 0.5f;
	
	public FarWorldMusicalSound(String filename, String fancyname) {
		name = filename;
		displayname = fancyname;
		loc = FarWorld.MODID + ":music/"+ name;
	}
	
	@Override
	public boolean canRepeat() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public SoundEventAccessor createAccessor(SoundHandler arg0) {
		// TODO Auto-generated method stub
		return new SoundEventAccessor(new ResourceLocation(loc), null);
	}

	@Override
	public AttenuationType getAttenuationType() {
		// TODO Auto-generated method stub
		return AttenuationType.NONE;
	}

	@Override
	public SoundCategory getCategory() {
		// TODO Auto-generated method stub
		return SoundCategory.MUSIC;
	}

	@Override
	public float getPitch() {
		// TODO Auto-generated method stub
		return PITCH;
	}

	@Override
	public int getRepeatDelay() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Sound getSound() {
		// TODO Auto-generated method stub
		return new Sound(loc, (int)(getVolume() * 128), (int)(PITCH * 128), (int)(getVolume() * 128), Sound.Type.FILE, false);
	}

	@Override
	public ResourceLocation getSoundLocation() {
		// TODO Auto-generated method stub
		return new ResourceLocation(loc);
	}

	@Override
	public float getVolume() {
		// TODO Auto-generated method stub
		return Minecraft.getMinecraft().gameSettings.getSoundLevel(SoundCategory.MUSIC);
	}

	@Override
	public float getXPosF() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getYPosF() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getZPosF() {
		// TODO Auto-generated method stub
		return 0;
	}

}
