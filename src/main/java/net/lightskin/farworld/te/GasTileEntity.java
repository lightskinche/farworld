package net.lightskin.farworld.te;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.util.Random;

import net.lightskin.farworld.FarWorld;
import net.lightskin.farworld.blocks.FarWorldBlocks;
import net.lightskin.farworld.blocks.GasBlockBase;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagByteArray;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

//TODO: get gasses to work on world load
public class GasTileEntity extends TileEntity implements ITickable{
	public GasBlockBase gasBlock = null;
	public boolean mark = false;
	public int tick = 0;
	
	private String gasBlockStorageTag = "gas";
	
	public GasTileEntity(GasBlockBase a) {
		super();
		gasBlock = a;
	}
	
	@Override
	public void update() {
		FarWorld.logger.info("gasBlock = " + gasBlock.getClass().toString());
		tick++;
		if(tick % 2 == 0) {
		if(mark)
			return;
		BlockPos pos = gasBlock.update(this.getWorld(), getPos(), gasBlock.getDefaultState(), new Random());
		if(pos != null) {
			this.setPos(pos);
			this.getWorld().markChunkDirty(pos, this);
			mark = true;
		}
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		gasBlock = (GasBlockBase)ForgeRegistries.BLOCKS.getValue(new ResourceLocation(nbt.getTag(gasBlockStorageTag).toString()));
		FarWorld.logger.info("read: " + gasBlock.getLocalizedName());
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tagComp) {
		tagComp.setTag(gasBlockStorageTag, new NBTTagString(gasBlock.getRegistryName().toString()));
		FarWorld.logger.info("logged: " + gasBlock.getRegistryName().toString());
		return tagComp;
	}
}
