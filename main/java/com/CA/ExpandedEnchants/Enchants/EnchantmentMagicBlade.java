package com.CA.ExpandedEnchants.Enchants;

import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.util.ResourceLocation;

public class EnchantmentMagicBlade extends EnchantmentEE {

	public EnchantmentMagicBlade(int par1, ResourceLocation res, int par2, EnumEnchantmentType par3EnumEnchantmentType) {
		super(par1, res, par2, par3EnumEnchantmentType);
	}
	
	@Override
	public int getMinEnchantability(int par1){
		return par1*6;
	}
	
	@Override
	public int getMaxEnchantability(int par1){
		return this.getMinEnchantability(par1) + 50;
	}
	
	@Override
	public int getMaxLevel() {
		return 5;
	}
}
