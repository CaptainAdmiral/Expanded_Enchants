package com.CA.ExpandedEnchants.Enchants;

import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.util.ResourceLocation;

public class EnchantmentBossDamage extends EnchantmentEE {

	public EnchantmentBossDamage(int par1, ResourceLocation par2ResourceLocation, int par3, EnumEnchantmentType par4EnumEnchantmentType) {
		super(par1, par2ResourceLocation, par3, par4EnumEnchantmentType);
	}

	@Override
	public int getMinEnchantability(int par1){
		return 30;
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
	public boolean isNetherEnchant() {
		return true;
	}
}
