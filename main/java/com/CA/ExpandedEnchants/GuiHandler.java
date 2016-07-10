package com.CA.ExpandedEnchants;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityEnchantmentTable;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

import com.CA.ExpandedEnchants.Client.Gui.GuiXPTable;
import com.CA.ExpandedEnchants.Inventory.ContainerXPTable;

public class GuiHandler implements IGuiHandler {

	@Override
    public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
	    TileEntity tileEntity = world.getTileEntity(new BlockPos(x,y,z));
	    if(tileEntity instanceof TileEntityEnchantmentTable){
	    	return new ContainerXPTable(player.inventory, world, x, y, z);
	    }
	    return null;
    }

	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
	    TileEntity tileEntity = world.getTileEntity(new BlockPos(x,y,z));
	    if(tileEntity instanceof TileEntityEnchantmentTable){
	    	return new GuiXPTable(player.inventory, world, x, y, z, ((TileEntityEnchantmentTable) tileEntity).hasCustomName() ? ((TileEntityEnchantmentTable) tileEntity).getName() : null);
	    }
	    return null;
	}
}
