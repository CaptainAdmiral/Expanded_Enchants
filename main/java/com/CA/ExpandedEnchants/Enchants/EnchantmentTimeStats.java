package com.CA.ExpandedEnchants.Enchants;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.util.ResourceLocation;

public class EnchantmentTimeStats extends EnchantmentEE{

	public EnchantmentTimeStats(int par1, ResourceLocation res, int par2) {
		super(par1, res, par2, EnumEnchantmentType.ARMOR_TORSO);
	}

	@Override
	public int getMinEnchantability(int par1){
		return 10 + par1*10;
	}
	
	@Override
	public int getMaxEnchantability(int par1){
		return 50;
	}
	
	@Override
	public int getMaxLevel() {
		return 2;
	}
	
	@Override
	public boolean canApplyTogether(Enchantment enchantment) {
		return enchantment instanceof EnchantmentTimeStats? false : super.canApplyTogether(enchantment);
	}
}
