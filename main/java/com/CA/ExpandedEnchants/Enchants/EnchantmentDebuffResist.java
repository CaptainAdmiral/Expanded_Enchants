package com.CA.ExpandedEnchants.Enchants;

import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.util.ResourceLocation;

public class EnchantmentDebuffResist extends EnchantmentEE {

	public EnchantmentDebuffResist(int par1, int par2) {
		super(par1, new ResourceLocation("debuffResist"), par2, EnumEnchantmentType.ARMOR);
		this.setName("debuffResist");
	}
	
	@Override
	public int getMinEnchantability(int par1){
		return par1 * 6;
	}
	
	@Override
	public int getMaxEnchantability(int par1){
		return this.getMinEnchantability(par1) + 50;
	}
	
	@Override
	public int getMaxLevel() {
		return 4;
	}
}
