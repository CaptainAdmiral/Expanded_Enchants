package com.CA.ExpandedEnchants.Enchants;

import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.util.ResourceLocation;

public class EnchantmentFireResist extends EnchantmentEE {
	
	public EnchantmentFireResist(int par1, int par2) {
		super(par1, new ResourceLocation("fireResist"), par2, EnumEnchantmentType.ARMOR_TORSO);
		this.setName("fireResist");
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
