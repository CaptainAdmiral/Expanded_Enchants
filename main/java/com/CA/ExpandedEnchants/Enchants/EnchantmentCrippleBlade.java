package com.CA.ExpandedEnchants.Enchants;

import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.util.ResourceLocation;

public class EnchantmentCrippleBlade extends EnchantmentEE {
	
	public EnchantmentCrippleBlade(int par1, int par2) {
		super(par1, new ResourceLocation("crippleBlade"), par2, EnumEnchantmentType.WEAPON);
		this.setName("crippleBlade");
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
}
