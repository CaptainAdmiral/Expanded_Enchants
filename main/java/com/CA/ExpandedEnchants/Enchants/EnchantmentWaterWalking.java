package com.CA.ExpandedEnchants.Enchants;

import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.util.ResourceLocation;

public class EnchantmentWaterWalking extends EnchantmentEE {

	public EnchantmentWaterWalking(int par1, int par2) {
		super(par1, new ResourceLocation("waterWalking"), par2, EnumEnchantmentType.ARMOR_FEET);
		this.setName("waterWalking");
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
