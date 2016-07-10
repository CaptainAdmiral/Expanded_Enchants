package com.CA.ExpandedEnchants.Enchants;

import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.util.ResourceLocation;

public class EnchantmentDisableAI extends EnchantmentEE {
	
	public EnchantmentDisableAI(int par1, int par2) {
		super(par1, new ResourceLocation("disableAI"), par2, EnumEnchantmentType.BOW);
		this.setName("disableAI");
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
		return 1;
	}
	
	@Override
	public boolean isNetherEnchant() {
		return true;
	}
}
