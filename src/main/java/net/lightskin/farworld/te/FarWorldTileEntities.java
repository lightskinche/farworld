package net.lightskin.farworld.te;

import net.lightskin.farworld.blocks.FarWorldBlocks;
import net.lightskin.farworld.blocks.GasBlockBase;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class FarWorldTileEntities {
	
	public FarWorldTileEntities() {
		GameRegistry.registerTileEntity(GasTileEntity.class, new ResourceLocation("gasTileEntity"));
	}
}
