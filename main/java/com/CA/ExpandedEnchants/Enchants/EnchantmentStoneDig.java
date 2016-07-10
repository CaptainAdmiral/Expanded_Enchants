package com.CA.ExpandedEnchants.Enchants;

import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.util.ResourceLocation;

public class EnchantmentStoneDig extends EnchantmentEE{
	
	public EnchantmentStoneDig(int par1, int par2) {
		super(par1, new ResourceLocation("stoneDig"), par2, EnumEnchantmentType.DIGGER);
		this.setName("stoneDig");
	}
	
	@Override
	public int getMinEnchantability(int par1){
		return par1 * 10;
	}
	
	@Override
	public int getMaxEnchantability(int par1){
		return this.getMinEnchantability(par1) + 50;
	}
	
	@Override
	public int getMaxLevel() {
		return 3;
	}
	
	@Override
	public boolean isNetherEnchant() {
		return true;
	}
}
