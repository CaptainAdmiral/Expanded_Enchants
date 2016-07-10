package com.CA.ExpandedEnchants.Enchants;

import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.util.ResourceLocation;

public class EnchantmentEggDrop extends EnchantmentEE {

	public EnchantmentEggDrop(int par1, int par2) {
		super(par1, new ResourceLocation("eggDrop"), par2, EnumEnchantmentType.WEAPON);
		this.setName("eggDrop");
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
