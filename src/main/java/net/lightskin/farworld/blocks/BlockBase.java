package net.lightskin.farworld.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.ForgeRegistry;

public class BlockBase extends Block{

	public BlockBase(String name, Material material) {
		super(material);
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(CreativeTabs.MATERIALS);
		ForgeRegistries.BLOCKS.register(this);
		ForgeRegistries.ITEMS.register(new ItemBlock(this).setRegistryName(this.getRegistryName()));
		{//checks if on client before loading any models
			try {
				java.lang.reflect.Method m = this.getClass().getDeclaredMethod("loadCustomModel", null);
				loadCustomModel();
			} catch (Exception e) {
				return;
			}
		}
	}
	
	@SideOnly(Side.CLIENT)
	private void loadCustomModel() {
		Item tmp = Item.getItemFromBlock(this);
		ModelLoader.setCustomModelResourceLocation(tmp, 0, new ModelResourceLocation(tmp.getRegistryName(), "inventory"));
	}
	
}
