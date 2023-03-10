package net.lightskin.farworld.effects;

import net.minecraft.potion.Potion;
import net.minecraft.util.DamageSource;
import net.lightskin.farworld.damage.*;

public class FarWorldPotions {
	//damage sources for the potions
	public static DamageSource coldBurnDamageSource;
	
	//potion effects
	public static Potion coldBurnEffect;
	
	public FarWorldPotions() {
		//damage sources
		coldBurnDamageSource = new ColdBurnDamageSource();
		
		//potion effects
		coldBurnEffect = new ColdBurnEffect();
	}
}
