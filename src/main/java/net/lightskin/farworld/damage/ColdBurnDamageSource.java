package net.lightskin.farworld.damage;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

//TODO: test the death message here
public class ColdBurnDamageSource extends DamageSource{

	public ColdBurnDamageSource() {
		super("cold_burn_damage");
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public ITextComponent getDeathMessage(EntityLivingBase entityLivingBaseIn) {
		return new TextComponentString(entityLivingBaseIn.getName() + " died from frostbite");
	}
	
	@Override
	public boolean isUnblockable() {
		return true;
	}

}
