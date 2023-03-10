package net.lightskin.farworld.effects;

import net.lightskin.farworld.FarWorld;
import net.lightskin.farworld.damage.ColdBurnDamageSource;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ColdBurnEffect extends Potion{
	protected ResourceLocation icon = new ResourceLocation(FarWorld.MODID + ":textures/gui/cold_burn.png");
	protected ColdBurnEffect() {
		super(true, 0xEFEFEF); //bad white effect
		this.setPotionName("effect.cold_burn");
		this.setIconIndex(0, 0);
		this.setRegistryName("cold_burn");
		ForgeRegistries.POTIONS.register(this);
	}
	
	@Override
	public boolean isReady(int duration, int amplfier) {
		return true;
	}
	
	@Override
	public void affectEntity(Entity source, Entity indirectSource, EntityLivingBase entityLivingBaseIn, int amplifier, double health) {
		performEffect(entityLivingBaseIn, amplifier);
	}
	
	@Override
	public void performEffect(EntityLivingBase entityLivingBaseIn, int amplifier) {
		entityLivingBaseIn.attackEntityFrom(FarWorldPotions.coldBurnDamageSource, 4);
	}
	
	@Override
	public boolean hasStatusIcon()
	{
		return false;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void renderHUDEffect(int x, int y, PotionEffect effect, Minecraft mc, float alpha)
	{
		mc.renderEngine.bindTexture(icon);

		Gui.drawModalRectWithCustomSizedTexture(x + 3, y + 3, 0, 0, 18, 18, 18, 18);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void renderInventoryEffect(int x, int y, PotionEffect effect, Minecraft mc)
	{
		mc.renderEngine.bindTexture(icon);

		Gui.drawModalRectWithCustomSizedTexture(x + 6, y + 7, 0, 0, 18, 18, 18, 18);
	}

}
