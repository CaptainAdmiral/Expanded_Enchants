package com.CA.ExpandedEnchants.Inventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.WeightedRandom;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.CA.ExpandedEnchants.EEMain;
import com.CA.ExpandedEnchants.Enchants.EnchantmentEE;

public class ContainerXPTable extends Container
{
   
    public IInventory tableInventory = new InventoryBasic("XP", true, 1)
    {
        private static final String __OBFID = "CL_00001746";
   
        
        @Override
        public int getInventoryStackLimit()
        {
            return 1;
        }
	
        @Override
        public void markDirty()
        {
            ContainerXPTable.this.onCraftMatrixChanged(this);
        }
    };
    
    private World worldPointer;
    private int posX;
    private int posY;
    private int posZ;
    private Random rand = new Random();

    public long nameSeed;

    public int[] enchantLevels = new int[3];
    private static final String __OBFID = "CL_00001745";

    public ContainerXPTable(InventoryPlayer par1InventoryPlayer, World par2World, int par3, int par4, int par5) {
    	this.worldPointer = par2World;
        this.posX = par3;
        this.posY = par4;
        this.posZ = par5;
        this.addSlotToContainer(new Slot(this.tableInventory, 0, 25, 47)
        {
            private static final String __OBFID = "CL_00001747";

            public boolean isItemValid(ItemStack par1ItemStack)
            {
                return true;
            }
        });
        int l;

        for (l = 0; l < 3; ++l)
        {
            for (int i1 = 0; i1 < 9; ++i1)
            {
                this.addSlotToContainer(new Slot(par1InventoryPlayer, i1 + l * 9 + 9, 8 + i1 * 18, 84 + l * 18));
            }
        }

        for (l = 0; l < 9; ++l)
        {
            this.addSlotToContainer(new Slot(par1InventoryPlayer, l, 8 + l * 18, 142));
        }
    }

    @Override
    public void detectAndSendChanges()
    {
        super.detectAndSendChanges();

        for (int i = 0; i < this.crafters.size(); ++i)
        {
            ICrafting icrafting = (ICrafting)this.crafters.get(i);
            icrafting.sendProgressBarUpdate(this, 0, this.enchantLevels[0]);
            icrafting.sendProgressBarUpdate(this, 1, this.enchantLevels[1]);
            icrafting.sendProgressBarUpdate(this, 2, this.enchantLevels[2]);
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void updateProgressBar(int par1, int par2)
    {
        if (par1 >= 0 && par1 <= 2)
        {
            this.enchantLevels[par1] = par2;
        }
        else
        {
            super.updateProgressBar(par1, par2);
        }
    }


    @Override
    public void onCraftMatrixChanged(IInventory par1IInventory)
    {
        if (par1IInventory == this.tableInventory)
        {
            ItemStack itemstack = par1IInventory.getStackInSlot(0);

            if (itemstack != null && itemstack.isItemEnchantable())
            {
                this.nameSeed = this.rand.nextLong();

                if (!this.worldPointer.isRemote) {

                    for (int i = 0; i < 3; ++i)
                    {
                        this.enchantLevels[i] = EnchantmentHelper.calcItemStackEnchantability(this.rand, i, 18, itemstack);
                    }

                    this.detectAndSendChanges();
                }
            }
            else
            {
                for (int i = 0; i < 3; ++i)
                {
                    this.enchantLevels[i] = 0;
                }
            }
        }
    }


    @Override
    public boolean enchantItem(EntityPlayer par1EntityPlayer, int par2)
    {
        ItemStack itemstack = this.tableInventory.getStackInSlot(0);

        if (this.enchantLevels[par2] > 0 && itemstack != null && (par1EntityPlayer.experienceLevel >= this.enchantLevels[par2] || par1EntityPlayer.capabilities.isCreativeMode))
        {
            if (!this.worldPointer.isRemote)
            {
                List list = this.buildEnchantmentList(this.rand, itemstack, this.enchantLevels[par2], par1EntityPlayer);
                if(par1EntityPlayer.dimension == -1) {
                	par1EntityPlayer.attackEntityFrom(DamageSource.magic, 15 + rand.nextInt(5));
                }
                boolean flag = itemstack.getItem() == Items.book;

                if (list != null)
                {
                    par1EntityPlayer.addExperienceLevel(-this.enchantLevels[par2]);

                    if (flag)
                    {
                    	itemstack = new ItemStack(Items.enchanted_book);
                    }

                    int j = flag && list.size() > 1 ? this.rand.nextInt(list.size()) : -1;

                    for (int k = 0; k < list.size(); ++k)
                    {
                        EnchantmentData enchantmentdata = (EnchantmentData)list.get(k);

                        if (!flag || k != j)
                        {
                            if (flag)
                            {
                                Items.enchanted_book.addEnchantment(itemstack, enchantmentdata);
                            }
                            else
                            {
                                itemstack.addEnchantment(enchantmentdata.enchantmentobj, enchantmentdata.enchantmentLevel);
                            }
                        }
                    }

                    this.onCraftMatrixChanged(this.tableInventory);
                }
            }

            return true;
        }
        else
        {
            return false;
        }
    }
    
    private List buildEnchantmentList(Random par0Random, ItemStack par1ItemStack, int par2, EntityPlayer player) {
        int enchantNumber = 15;
    	Item item = par1ItemStack.getItem();
        int j = item.getItemEnchantability();

        if (j <= 0)
        {
            return null;
        }
        else
        {
            j /= 2;
            j = 1 + par0Random.nextInt((j >> 1) + 1) + par0Random.nextInt((j >> 1) + 1);
            int k = j + par2;
            float f = (par0Random.nextFloat() + par0Random.nextFloat() - 1.0F) * 0.15F;
            int l = (int)((float)k * (1.0F + f) + 0.5F);

            if (l < 1)
            {
                l = 1;
            }

            ArrayList arraylist = null;
            Map map = this.mapEnchantmentData(l, par1ItemStack, player);

            if (map != null && !map.isEmpty())
            {
                EnchantmentData enchantmentdata = (EnchantmentData)WeightedRandom.getRandomItem(par0Random, map.values());

                if (enchantmentdata != null)
                {
                    arraylist = new ArrayList();
                    arraylist.add(enchantmentdata);

                    for (int i1 = l; player.dimension == -1? par0Random.nextInt((int) enchantNumber / 2) <= i1 : par0Random.nextInt(enchantNumber) <= i1; i1 >>= 1)
                    {
                        Iterator iterator = map.keySet().iterator();

                        while (iterator.hasNext())
                        {
                            Integer integer = (Integer)iterator.next();
                            boolean flag = true;
                            Iterator iterator1 = arraylist.iterator();

                            while (true)
                            {
                                if (iterator1.hasNext())
                                {
                                    EnchantmentData enchantmentdata1 = (EnchantmentData)iterator1.next();

                                    Enchantment e1 = enchantmentdata1.enchantmentobj;
                                    Enchantment e2 = Enchantment.getEnchantmentById(integer.intValue());
                                    if (e1.canApplyTogether(e2) && e2.canApplyTogether(e1)) //Forge BugFix: Let Both enchantments veto being together
                                    {
                                        continue;
                                    }

                                    flag = false;
                                }

                                if (!flag)
                                {
                                    iterator.remove();
                                }

                                break;
                            }
                        }

                        if (!map.isEmpty())
                        {
                            EnchantmentData enchantmentdata2 = (EnchantmentData)WeightedRandom.getRandomItem(par0Random, map.values());
                            arraylist.add(enchantmentdata2);
                        }
                    }
                }
            }

            return arraylist;
        }
    }
    
    private Map mapEnchantmentData(int par0, ItemStack par1ItemStack, EntityPlayer player) {
        Item item = par1ItemStack.getItem();
        HashMap hashmap = null;
        boolean flag = par1ItemStack.getItem() == Items.book;
        Enchantment[] aenchantment = Enchantment.enchantmentsBookList;
        int j = aenchantment.length;

        for (int k = 0; k < j; ++k)
        {
            Enchantment enchantment = aenchantment[k];

            if (enchantment == null) continue;
            EnchantmentEE enchantmentEE = null;
            if(enchantment instanceof EnchantmentEE) {
            	enchantmentEE = (EnchantmentEE) enchantment;
            }
            if (enchantment.canApplyAtEnchantingTable(par1ItemStack) || ((item == Items.book) && enchantment.isAllowedOnBooks()) || (enchantmentEE != null && enchantmentEE.isNetherEnchant() && player.dimension == -1 && enchantment.canApply(par1ItemStack)))
            {
                for (int l = enchantment.getMinLevel(); l <= enchantment.getMaxLevel(); ++l)
                {
                    if (par0 >= enchantment.getMinEnchantability(l) && par0 <= enchantment.getMaxEnchantability(l))
                    {
                        if (hashmap == null)
                        {
                            hashmap = new HashMap();
                        }

                        hashmap.put(Integer.valueOf(enchantment.effectId), new EnchantmentData(enchantment, l));
                    }
                }
            }
        }

        return hashmap;
    }


    @Override
    public void onContainerClosed(EntityPlayer par1EntityPlayer)
    {
        super.onContainerClosed(par1EntityPlayer);

        if (!this.worldPointer.isRemote)
        {
            ItemStack itemstack = this.tableInventory.getStackInSlotOnClosing(0);

            if (itemstack != null)
            {
                par1EntityPlayer.dropPlayerItemWithRandomChoice(itemstack, false);
            }
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer par1EntityPlayer) {
        return this.worldPointer.getBlockState(new BlockPos(this.posX, this.posY, this.posZ)).getBlock() != EEMain.xpTable ? false : par1EntityPlayer.getDistanceSq((double)this.posX + 0.5D, (double)this.posY + 0.5D, (double)this.posZ + 0.5D) <= 64.0D;
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2)
    {
        ItemStack itemstack = null;
        Slot slot = (Slot)this.inventorySlots.get(par2);

        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (par2 == 0)
            {
                if (!this.mergeItemStack(itemstack1, 1, 37, true))
                {
                    return null;
                }
            }
            else
            {
                if (((Slot)this.inventorySlots.get(0)).getHasStack() || !((Slot)this.inventorySlots.get(0)).isItemValid(itemstack1))
                {
                    return null;
                }

                if (itemstack1.hasTagCompound() && itemstack1.stackSize == 1)
                {
                    ((Slot)this.inventorySlots.get(0)).putStack(itemstack1.copy());
                    itemstack1.stackSize = 0;
                }
                else if (itemstack1.stackSize >= 1)
                {
                    ((Slot)this.inventorySlots.get(0)).putStack(new ItemStack(itemstack1.getItem(), 1, itemstack1.getItemDamage()));
                    --itemstack1.stackSize;
                }
            }

            if (itemstack1.stackSize == 0)
            {
                slot.putStack((ItemStack)null);
            }
            else
            {
                slot.onSlotChanged();
            }

            if (itemstack1.stackSize == itemstack.stackSize)
            {
                return null;
            }

            slot.onPickupFromSlot(par1EntityPlayer, itemstack1);
        }

        return itemstack;
    }
    }