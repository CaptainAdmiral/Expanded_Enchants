package com.CA.ExpandedEnchants.Item;

import java.util.List;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

import com.CA.ExpandedEnchants.EEMain;

public class ItemXPBook extends Item {
	
	public ItemXPBook() {
		super();
		this.setMaxStackSize(1);
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
		if(par3EntityPlayer.capabilities.isCreativeMode) return super.onItemRightClick(par1ItemStack, par2World, par3EntityPlayer);
		par1ItemStack.stackSize--;
		par3EntityPlayer.addExperience(1395);
		return super.onItemRightClick(par1ItemStack, par2World, par3EntityPlayer);
	}
	
	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
		if(world.getBlockState(pos).getBlock() == EEMain.xpTableBase && side == EnumFacing.UP) {
			stack.stackSize--;
			world.setBlockState(pos, EEMain.xpTable.getDefaultState());
		}
		return super.onItemUse(stack, player, world, pos, side, hitX, hitY, hitZ);
	}
	
	@Override
	public boolean hasEffect(ItemStack par1ItemStack) {
		return true;
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean something) {
		list.add(EnumChatFormatting.AQUA + "Right click to gain 30lvls worth of XP!");
		super.addInformation(stack, player, list, something);
	}
}
