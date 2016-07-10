package com.CA.ExpandedEnchants.Item;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;
import net.minecraftforge.fluids.IFluidHandler;

public class ItemXPBucket extends ItemBucket {

	public ItemXPBucket(Block block) {
		super(block);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		MovingObjectPosition movingobjectposition = this.getMovingObjectPositionFromPlayer(world, player, false);
		if (movingobjectposition == null) {
			return stack;
		}
		else if(movingobjectposition.typeOfHit == MovingObjectType.BLOCK) {
			if(world.getTileEntity(new BlockPos(movingobjectposition.hitVec.xCoord, movingobjectposition.hitVec.yCoord, movingobjectposition.hitVec.zCoord)) instanceof IFluidHandler) return stack;
		}
		return super.onItemRightClick(stack, world, player);
	}

	@Override
	public void addInformation(ItemStack p_77624_1_, EntityPlayer p_77624_2_, List list, boolean p_77624_4_) {
		super.addInformation(p_77624_1_, p_77624_2_, list, p_77624_4_);
		list.add(EnumChatFormatting.AQUA + "Pure XP!");
		list.add(EnumChatFormatting.RED + "BUGGED: Fluid rendering disabled by forge 1.8");
		list.add(EnumChatFormatting.RED + "(The fluid will be invisible)");
	}
}
