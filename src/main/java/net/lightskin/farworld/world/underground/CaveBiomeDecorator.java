package net.lightskin.farworld.world.underground;

import io.github.opencubicchunks.cubicchunks.api.util.CubePos;
import io.github.opencubicchunks.cubicchunks.cubicgen.asm.mixin.common.accessor.IBiomeProvider;
import net.lightskin.farworld.world.underground.manager.LayerManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.gen.layer.GenLayer;

//this probably wont ever be called because of the lateness in which we override the biomes, test this
public class CaveBiomeDecorator extends BiomeDecorator{
	
	@Override
	public void decorate(World worldIn, java.util.Random random, Biome biome, BlockPos pos) {
		Layer tmp = LayerManager.getLayer(pos.getY() / 16);
		if(tmp != null)
			tmp.decorate(worldIn, random, biome, pos);
	}

}
