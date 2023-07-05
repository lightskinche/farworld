package net.lightskin.farworld.sound;

import net.lightskin.farworld.FarWorld;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.Sound;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.util.ResourceLocation;

public class MusicTable {
	public static FarWorldMusicalSound defaultDesperation = new FarWorldMusicalSound("desperate", "At Death's Door");
	
	public static FarWorldMusicalSound SAS = new FarWorldMusicalSound("stone","Stone and Stone");
	
	public static FarWorldMusicalSound muskagBackground = new FarWorldMusicalSound("overworld/muskag/background","Tragedy of Snow White");
	public static FarWorldMusicalSound muskagDesperation = new FarWorldMusicalSound("overworld/muskag/desperation","Cold Fate");
	public static FarWorldMusicalSound muskagNight = new FarWorldMusicalSound("overworld/muskag/night", "The Darkest Night");
	//not working yet
	//public static FarWorldMusicalSound muskagMobAttack = new FarWorldMusicalSound("muskag_mobattack", "X"); //unnamed because they are just sounds
	//public static FarWorldMusicalSound muskagMobAttackPlayer = new FarWorldMusicalSound("muskag_mobattackplayer", "X");
	
	public static FarWorldMusicalSound abyssalZoneBackground = new FarWorldMusicalSound("underground/abyss/abyssalzone/background", "Ode to Ocean");
	public static FarWorldMusicalSound abyssalZoneDesperation= new FarWorldMusicalSound("underground/abyss/abyssalzone/desperation", "As Death Closes In");
	public static FarWorldMusicalSound oceanicAbyssDesperation = new FarWorldMusicalSound("overworld/oceanicabyss/desperation", "Kept Death Waiting Long Enough");
	
	public static SoundHandler sh;
	public static FarWorldMusicalSound last = null;
	public static String displaySongTitle = "None";
	public static void tryPlayMusic(FarWorldMusicalSound song) {
		sh = Minecraft.getMinecraft().getSoundHandler();
		if(last != song) //display song name since we haven't heard before
			displaySongTitle = song.displayname;
		if(!sh.isSoundPlaying(song)) {
			sh.stopSounds();
			sh.playSound(song);
		}
		last = song;
	}
}
