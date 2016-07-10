package com.CA.ExpandedEnchants.Enchants;

import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.util.ResourceLocation;

public class EnchantmentNetherDamage extends EnchantmentEE{

	public EnchantmentNetherDamage(int par1, int par2) {
		super(par1, new ResourceLocation("netherDamage"), par2, EnumEnchantmentType.WEAPON);
		this.setName("netherDamage");
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
