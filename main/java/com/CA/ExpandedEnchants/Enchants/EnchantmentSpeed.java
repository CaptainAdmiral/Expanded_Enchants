package com.CA.ExpandedEnchants.Enchants;

import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.util.ResourceLocation;

public class EnchantmentSpeed extends EnchantmentEE {

	public EnchantmentSpeed(int par1, int par2) {
		super(par1, new ResourceLocation("speed"), par2, EnumEnchantmentType.ARMOR_FEET);
		this.setName("speed");
	}
	
	@Override
	public int getMinEnchantability(int par1){
		return 10+10*par1;
	}
	
	@Override
	public int getMaxEnchantability(int par1){
		return this.getMinEnchantability(par1)+50;
	}
	
	@Override
	public int getMaxLevel() {
		return 2;
	}
}
