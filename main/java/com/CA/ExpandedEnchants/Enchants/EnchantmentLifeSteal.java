package com.CA.ExpandedEnchants.Enchants;

import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.util.ResourceLocation;

public class EnchantmentLifeSteal extends EnchantmentEE {

	public EnchantmentLifeSteal(int par1, int par2) {
		super(par1, new ResourceLocation("lifeSteal"), par2, EnumEnchantmentType.WEAPON);
		this.setName("lifeSteal");
	}
	
	@Override
	public boolean isNetherEnchant() {
		return true;
	}
	
	@Override
	public int getMinEnchantability(int par1){
		return par1*5+10;
	}
	
	@Override
	public int getMaxEnchantability(int par1){
		return this.getMinEnchantability(par1) + 10;
	}
	
	@Override
	public int getMaxLevel() {
		return 4;
	}
}
