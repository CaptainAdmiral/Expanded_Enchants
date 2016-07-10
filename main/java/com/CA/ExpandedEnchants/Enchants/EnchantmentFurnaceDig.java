package com.CA.ExpandedEnchants.Enchants;

import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.util.ResourceLocation;

public class EnchantmentFurnaceDig extends EnchantmentEE {

    public EnchantmentFurnaceDig(int par1, int par2) {
        super(par1, new ResourceLocation("furnaceDig"), par2, EnumEnchantmentType.DIGGER);
        this.setName("furnaceDig");
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
}