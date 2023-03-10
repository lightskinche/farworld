package net.lightskin.farworld.blocks;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import net.lightskin.farworld.FarWorld;
import net.lightskin.farworld.te.GasTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class GasBlockBase extends Block{
	public GasBlockBase(String name) { //assumes a gas block is being created
		super(Material.AIR);
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(CreativeTabs.MATERIALS);
		ForgeRegistries.BLOCKS.register(this);
		ForgeRegistries.ITEMS.register(new ItemBlock(this).setRegistryName(this.getRegistryName()));
		{//checks if on client before loading any models 
			try {
				java.lang.reflect.Method m = this.getClass().getDeclaredMethod("loadCustomModel", null);
				loadCustomModel();//find a way to make this block invisible
			} catch (Exception e) {
				return;
			}
		}
	}
	//for wrapper in te
	public GasBlockBase() {
		super(Material.AIR);
	}
	
	//for renderering
	//default behavior insists on slightly transparent gas, this can be overriden if lag is caused
	@Override
	public boolean shouldSideBeRendered(IBlockState state, IBlockAccess access, BlockPos pos, EnumFacing face) {
		//if there isn't a gas block next to this block, then render normally else dont render that side (to avoid double faces)
		return access.getBlockState(pos.offset(face)).getBlock() == this ? false : super.shouldSideBeRendered(state, access, pos, face);
	}
	
    @Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }
    
    @Override
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.TRANSLUCENT;
    }

    @Override
    public boolean isTranslucent(IBlockState state)
    {
        return true;
    }
	
	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState b, IBlockAccess a, BlockPos n)
    {
		return null;
    }
	
	@Override
	public boolean hasTileEntity() {
		return true;
	}
	
	@Override
	public boolean hasTileEntity(IBlockState a) {
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new GasTileEntity(this);
	}
	
	//doesn't make sense to stand on gas
	@Override
	public boolean isCollidable() {
		return false;
	}
	
	//feel free to override this if its more convient than using affectEntity
	@Override
	public boolean causesSuffocation(IBlockState state) {
		return false;
	}
	
	public float getWeight() {
		return 0; //for reference, "air" has a weight of 0, -1 would be lighter than air and would be above, 1 would be heavier than air
	}
	//chance of moving per tick, lower number means higher chance which makes the gas appear to move faster
	public float getSpeed() {
		return 0;
	}
	
	//chance of acting per tick, lower number means higher chance which makes the gas appear to act on **entities** faster
	//getSpeed still determines how fast blocks are affected
	public float getActingSpeed() {
		return 0.75f;
	}
	
	//if a block can be removed and replaced with this gas; imagine allowing bushes to be destroyed (can always move into air)
	public boolean canMove(BlockPos desiredPos, IBlockState blockInWay, IBlockState gasState) {
		return false; //so that we don't eat the world by default, other gases have to intentionally change this for that to occur
	}
	
	//if a block cannot be removed and replaced (a barrier), how can this block be affected instead? i.e wood becoming deteriorated and then said wood may be removed
	//alternatively this can be used to check for something like fire to ignite fire to the gas or something
	public void affectBarrier(World world, IBlockState barrier, BlockPos barrierPos, IBlockState gasState) {
		
	}
	
	//e.g kill players, give effects, all that
	public void affectEntityLiving(EntityLivingBase entity, IBlockState gasState, Random random) {
		if(entity != null) //debug
			FarWorld.logger.info(entity.getName()); //debug
	}
	
	//e.g destroy dropped items or destroy signs or something //will enable this later
	public void affectEntity(Entity entity, IBlockState gasState, Random random) {
		
	}

	public class StatePosition{
		public IBlockState state;
		public BlockPos pos;
		public StatePosition(IBlockState state_in, BlockPos pos_in) {
			state = state_in;
			pos = pos_in;
		}
		public StatePosition() {
			state = null;
			pos = null;
		}
		public void setFromPos(BlockPos pos_in, World world) {
			pos = pos_in;
			state = world.getBlockState(pos);
		}
	}
	
	private boolean handleGas(StatePosition gasHandle, BlockPos curGasPos) {
		if(gasHandle.pos.getY() == curGasPos.getY()){
			return false; //cant move 
		}
		GasBlockBase gasOther = (GasBlockBase)gasHandle.state.getBlock();
		if(gasHandle.pos.getY() > curGasPos.getY()) {
			return gasOther.getWeight() > getWeight(); //if the gas above us weights more, we can move above it
		}
		else{
			return gasOther.getWeight() < getWeight(); //if the gas below us weights less than us, we can move below it
		}
	}
	
	private boolean handleAir(BlockPos airPos, BlockPos curGasPos) {
		if(airPos.getY() == curGasPos.getY())
			return true;
		else {
		if(airPos.getY() > curGasPos.getY() && getWeight() < 0)
			return true;
		else if(airPos.getY() < curGasPos.getY() && getWeight() > 0)
			return true;
		}
		return false;
	}
	
	public BlockPos update(World world, BlockPos pos, IBlockState block, Random random) {
		if(random.nextFloat() > getActingSpeed()) {
		//first check if any entities can be affected
		List<EntityLivingBase> livingEntities = world.getEntitiesWithinAABB(EntityLivingBase.class, FULL_BLOCK_AABB.offset(pos));
		for(EntityLivingBase living : livingEntities) {
			affectEntityLiving(living, block, random);
		}
		}
		//now attempt to move
		if(random.nextFloat() < getSpeed() && random.nextFloat() < getSpeed() && random.nextFloat() < getSpeed() && random.nextFloat() < getSpeed()) //higher number means higher chance of not moving, thus lower "speed" increases odds of moving
			return null; //no movement this frame
		StatePosition[] moveLocations = new StatePosition[] {new StatePosition(),new StatePosition(),new StatePosition(),new StatePosition(),new StatePosition(),new StatePosition()}; 
		//left, right, forward, back, up, down, no diagonal movement
		moveLocations[0].setFromPos(new BlockPos(pos.getX() - 1, pos.getY(), pos.getZ()), world);
		moveLocations[1].setFromPos(new BlockPos(pos.getX() + 1, pos.getY(), pos.getZ()), world);
		moveLocations[2].setFromPos(new BlockPos(pos.getX(), pos.getY(), pos.getZ() + 1), world);
		moveLocations[3].setFromPos(new BlockPos(pos.getX(), pos.getY(), pos.getZ() - 1), world);
		moveLocations[4].setFromPos(new BlockPos(pos.getX(), pos.getY() + 1, pos.getZ()), world);
		moveLocations[5].setFromPos(new BlockPos(pos.getX(), pos.getY() - 1, pos.getZ()), world);
		
		//so that there is no bias for order
		List<StatePosition> tmp = Arrays.asList(moveLocations);
		Collections.shuffle(tmp);
		moveLocations = tmp.toArray(moveLocations);
		
		int mark = -1;
		int priority = -1;
		//3 is air above or below
		//2 is air on any of the sides
		//1 is a block above or below (could be a gas, doesn't matter) 
		//0 is a block on any of the sides
		//-1 is no movement
		//first pass, examine move locations
		for(int i = 0; i < moveLocations.length; i++) {
			StatePosition state = moveLocations[i];
			boolean isAir = world.isAirBlock(state.pos);; //has priority over anything that is just "canMove"
			boolean isGas = state.state.getBlock() instanceof GasBlockBase; //make sure to not delete this block, move it in place of this
			//if this is a gas get if we are able to move otherwise if this is air then we know we can move otherwise check if we can move
			boolean canMove = isGas? handleGas(state, pos) : (isAir ? handleAir(state.pos,pos) : canMove(state.pos,state.state,block));
			if(canMove) {
			if(isAir) { 
				//air above or below (that we canMove) takes the most prioity
				if(state.pos.getY() != pos.getY()) {
					mark = i;
					priority = 3;
				}
				else if(priority < 1) { //air must be on side
					mark = i;
					priority = 1;
				}
			}
			else {
				if(state.pos.getY() != pos.getY() && priority < 2) {
					mark = i;
					priority = 2;
				}
				else if(priority < 0) {
					mark = i;
					priority = 0;
				}
			}
			}
			else if(random.nextFloat() > getActingSpeed() && random.nextFloat() > getActingSpeed())
				affectBarrier(world, state.state, state.pos, block);
		}
		if(mark != -1) {
			if(!(moveLocations[mark].state.getBlock() instanceof GasBlockBase)) //if we moved air or some other block out the way, then just set air behind us, no need to move the block to where we were
				world.setBlockState(pos, Blocks.AIR.getDefaultState()); //put air in our place to avoid duplicating this gas
			else //we moved into a gas, place that gas where we were before to avoid losing it
				world.setBlockState(pos, moveLocations[mark].state);
			world.setBlockState(moveLocations[mark].pos, block); //now move that block out the way
			return moveLocations[mark].pos; //we have successfully moved
		}
		//if we get here than we were not able to move (build pressure?)
		return null;
	}
	
	@SideOnly(Side.CLIENT)
	private void loadCustomModel() {
		Item tmp = Item.getItemFromBlock(this);
		ModelLoader.setCustomModelResourceLocation(tmp, 0, new ModelResourceLocation(tmp.getRegistryName(), "inventory"));
	}
}
