package com.CA.ExpandedEnchants.Enchants;

import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.util.ResourceLocation;

public class EnchantmentDamageCap extends EnchantmentEE {

	public EnchantmentDamageCap(int par1, int par2) {
		super(par1, new ResourceLocation("damageCap"), par2, EnumEnchantmentType.ARMOR);
		this.setName("damageCap");
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
}
