package com.CA.ExpandedEnchants.Enchants;

import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.util.ResourceLocation;

public class EnchantmentStraightArrow extends EnchantmentEE {

	public EnchantmentStraightArrow(int par1, int par2) {
		super(par1, new ResourceLocation("straightArrow"), par2, EnumEnchantmentType.BOW);
		this.setName("straightArrow");
	}
	
	@Override
	public int getMinEnchantability(int par1){
		return 20;
	}
	
	@Override
	public int getMaxEnchantability(int par1){
		return 50;
	}
	
	@Override
	public int getMaxLevel() {
		return 1;
	}
}
