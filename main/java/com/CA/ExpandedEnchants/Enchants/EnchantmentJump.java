package com.CA.ExpandedEnchants.Enchants;

import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.util.ResourceLocation;

public class EnchantmentJump extends EnchantmentEE {

	public EnchantmentJump(int par1, int par2) {
		super(par1, new ResourceLocation("jump"), par2, EnumEnchantmentType.ARMOR_FEET);
		this.setName("jump");
	}
	
	@Override
	public int getMinEnchantability(int par1){
		return 10*par1;
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
