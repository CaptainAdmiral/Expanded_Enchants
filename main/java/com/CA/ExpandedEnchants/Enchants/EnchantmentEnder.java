package com.CA.ExpandedEnchants.Enchants;

import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class EnchantmentEnder extends EnchantmentEE {

    public EnchantmentEnder(int par1, int par2) {
        super(par1, new ResourceLocation("ender"), par2, EnumEnchantmentType.DIGGER);
        this.setName("ender");
    }

    @Override
    public int getMinEnchantability(int par1) {
        return 15;
    }

    @Override
    public int getMaxEnchantability(int par1) {
        return super.getMinEnchantability(par1) + 50;
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }

    @Override
    public boolean canApply(ItemStack par1ItemStack) {
        return par1ItemStack.getItem() == Items.shears ? true : super.canApply(par1ItemStack);
    }
}