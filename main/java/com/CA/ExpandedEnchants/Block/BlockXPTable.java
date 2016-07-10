package com.CA.ExpandedEnchants.Block;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.BlockEnchantmentTable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.CA.ExpandedEnchants.EEMain;

public class BlockXPTable extends BlockEnchantmentTable {
	
	@Override
	public ArrayList<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
		ArrayList list = new ArrayList();
		list.add(new ItemStack(EEMain.xpTableBase));
		list.add(new ItemStack(EEMain.xpBook));
		return list;
	}
	
	public boolean isStructureComplete(World world, BlockPos pos) {
    	boolean isStructureComplete = false;
    	for(int i=0; i<=3; i++) {
  			if(world.getBlockState(pos.add(3, -1, i)).getBlock() != EEMain.xpFluidBlock && world.getBlockState(pos.add(3, -1, i)).getBlock() != EEMain.xpBlock || world.getBlockState(pos.add(3, -1, i)).getBlock() != EEMain.xpBlock && !world.getBlockState(pos.add(3, -1, i)).getValue(BlockFluidClassic.LEVEL).equals(0)) break;
  			if(world.getBlockState(pos.add(3, -1, -i)).getBlock() != EEMain.xpFluidBlock && world.getBlockState(pos.add(3, -1, -i)).getBlock() != EEMain.xpBlock || world.getBlockState(pos.add(3, -1, -i)).getBlock() != EEMain.xpBlock && !world.getBlockState(pos.add(3, -1, -i)).getValue(BlockFluidClassic.LEVEL).equals(0)) break;
  			if(world.getBlockState(pos.add(-3, -1, +i)).getBlock() != EEMain.xpFluidBlock && world.getBlockState(pos.add(-3, -1, i)).getBlock() != EEMain.xpBlock || world.getBlockState(pos.add(-3, -1, i)).getBlock() != EEMain.xpBlock && !world.getBlockState(pos.add(-3, -1, i)).getValue(BlockFluidClassic.LEVEL).equals(0)) break;
  			if(world.getBlockState(pos.add(-3, -1, -i)).getBlock() != EEMain.xpFluidBlock && world.getBlockState(pos.add(-3, -1, -i)).getBlock() != EEMain.xpBlock || world.getBlockState(pos.add(-3, -1, -i)).getBlock() != EEMain.xpBlock && !world.getBlockState(pos.add(-3, -1, -i)).getValue(BlockFluidClassic.LEVEL).equals(0)) break;
  			
  			if(world.getBlockState(pos.add(i, -1, 3)).getBlock() != EEMain.xpFluidBlock && world.getBlockState(pos.add(i, -1, 3)).getBlock() != EEMain.xpBlock || world.getBlockState(pos.add(i, -1, 3)).getBlock() != EEMain.xpBlock && !world.getBlockState(pos.add(i, -1, 3)).getValue(BlockFluidClassic.LEVEL).equals(0)) break;
  			if(world.getBlockState(pos.add(i, -1, -3)).getBlock() != EEMain.xpFluidBlock && world.getBlockState(pos.add(i, -1, -3)).getBlock() != EEMain.xpBlock || world.getBlockState(pos.add(i, -1, -3)).getBlock() != EEMain.xpBlock && !world.getBlockState(pos.add(i, -1, -3)).getValue(BlockFluidClassic.LEVEL).equals(0)) break;
  			if(world.getBlockState(pos.add(-i, -1, 3)).getBlock() != EEMain.xpFluidBlock && world.getBlockState(pos.add(-i, -1, 3)).getBlock() != EEMain.xpBlock || world.getBlockState(pos.add(-i, -1, 3)).getBlock() != EEMain.xpBlock && !world.getBlockState(pos.add(-i, -1, 3)).getValue(BlockFluidClassic.LEVEL).equals(0)) break;
  			if(world.getBlockState(pos.add(-i, -1, -3)).getBlock() != EEMain.xpFluidBlock && world.getBlockState(pos.add(-i, -1, -3)).getBlock() != EEMain.xpBlock || world.getBlockState(pos.add(-i, -1, -3)).getBlock() != EEMain.xpBlock && !world.getBlockState(pos.add(-i, -1, -3)).getValue(BlockFluidClassic.LEVEL).equals(0)) break;
  			isStructureComplete = i==3;
  		}
    	return isStructureComplete;
	}
    
    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ) {
    	
    	if(isStructureComplete(world, pos) || player.capabilities.isCreativeMode) {
    		player.openGui(EEMain.instance, 0, world, pos.getX(), pos.getY(), pos.getZ());
    		return true;
    	}
    	
    	Random rand = new Random();
    	for(int i=0; i<= 20; i++) {
    		world.spawnParticle(EnumParticleTypes.ENCHANTMENT_TABLE, pos.getX() + 0.5D + rand.nextGaussian(), pos.getY() + 1 + rand.nextGaussian(), pos.getZ() + 0.5D + rand.nextGaussian(), rand.nextGaussian() * 2, rand.nextGaussian() * 2, rand.nextGaussian() * 2);
    		world.spawnParticle(EnumParticleTypes.PORTAL, pos.getX() + 0.5D + rand.nextGaussian(), pos.getY() + 1 + rand.nextGaussian(), pos.getZ() + 0.5D + rand.nextGaussian(), rand.nextGaussian() * 2, rand.nextGaussian() * 2, rand.nextGaussian() * 2);
    	}
    	player.setVelocity(4 * ((player.posX - (pos.getX() + 0.5D)) / Math.pow(player.getDistance(pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D), 2)), (1.2D + rand.nextDouble()) / player.getDistance(pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D), 4 * ((player.posZ - (pos.getZ() + 0.5D)) / Math.pow(player.getDistance(pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D), 2)));
    	player.attackEntityFrom(EEMain.xpDamage, (float) (34 / player.getDistance(pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D)  + rand.nextInt(4)));
    	if(world.isRemote) {
    		player.addChatMessage(new ChatComponentText("The unchanneled power of the alter flows through you"));
    	}
    	return false;
    }
    
	@Override
	public boolean canHarvestBlock(IBlockAccess world, BlockPos pos, EntityPlayer player) {
		return true;
	}
}
