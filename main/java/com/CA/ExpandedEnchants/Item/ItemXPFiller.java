package com.CA.ExpandedEnchants.Item;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

import com.CA.ExpandedEnchants.EEMain;

public class ItemXPFiller extends Item {

	public ItemXPFiller() {
		super();
		this.setMaxStackSize(1);
	}

/*	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
		if(!par3EntityPlayer.inventory.hasItem(Items.bucket)) return super.onItemRightClick(par1ItemStack, par2World, par3EntityPlayer);
		if(par3EntityPlayer.experienceLevel < 30 && !par3EntityPlayer.capabilities.isCreativeMode) return super.onItemRightClick(par1ItemStack, par2World, par3EntityPlayer);
		par3EntityPlayer.inventory.consumeInventoryItem(Items.bucket);
		if (!par3EntityPlayer.capabilities.isCreativeMode) par1ItemStack.stackSize--;
		par3EntityPlayer.addExperienceLevel(-30);
		par3EntityPlayer.inventory.addItemStackToInventory(new ItemStack(EEMain.xpBucket, 1));
		return super.onItemRightClick(par1ItemStack, par2World, par3EntityPlayer);
	}*/
	
	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
		if(!par3EntityPlayer.inventory.hasItem(Item.getItemFromBlock(Blocks.gold_block))) return super.onItemRightClick(par1ItemStack, par2World, par3EntityPlayer);
		if(par3EntityPlayer.experienceLevel < 30 && !par3EntityPlayer.capabilities.isCreativeMode) return super.onItemRightClick(par1ItemStack, par2World, par3EntityPlayer);
		par3EntityPlayer.inventory.consumeInventoryItem(Item.getItemFromBlock(Blocks.gold_block));
		if (!par3EntityPlayer.capabilities.isCreativeMode) par1ItemStack.stackSize--;
		par3EntityPlayer.addExperienceLevel(-30);
		par3EntityPlayer.inventory.addItemStackToInventory(new ItemStack(EEMain.xpBlock, 1));
		return super.onItemRightClick(par1ItemStack, par2World, par3EntityPlayer);
	}

	@Override
	public boolean hasEffect(ItemStack par1ItemStack) {
		return true;
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean something) {
		list.add(EnumChatFormatting.AQUA + "Right click to enchant a block of gold with 30 lvls of XP");
		super.addInformation(stack, player, list, something);
	}
}
