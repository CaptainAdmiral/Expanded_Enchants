package com.CA.ExpandedEnchants.Enchants;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentArrowKnockback;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.util.ResourceLocation;

public class EnchantmentFireSpeed extends EnchantmentEE {

	public EnchantmentFireSpeed(int par1, int par2) {
		super(par1, new ResourceLocation("fireSpeed"), par2, EnumEnchantmentType.BOW);
		this.setName("fireSpeed");
	}
	
	public int getMinEnchantability(int par1){
		return 10;
	}
	
	public int getMaxEnchantability(int par1){
		return 50;
	}
	
	@Override
	public int getMaxLevel() {
		return 2;
	}
	
	@Override
	public boolean canApplyTogether(Enchantment enchantment) {
		return enchantment instanceof EnchantmentArrowKnockback? false : super.canApplyTogether(enchantment);
	}
}
