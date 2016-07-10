package com.CA.ExpandedEnchants.Enchants;

import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.util.ResourceLocation;

public class EnchantmentHeavyTip extends EnchantmentEE {
	
	public EnchantmentHeavyTip(int par1, int par2) {
		super(par1, new ResourceLocation("heavyTip"), par2, EnumEnchantmentType.BOW);
		this.setName("heavyTip");
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
	public boolean isNetherEnchant() {
		return true;
	}

}
