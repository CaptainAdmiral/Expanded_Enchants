package com.CA.ExpandedEnchants.Enchants;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentKnockback;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.util.ResourceLocation;

public class EnchantmentAttackSpeed extends EnchantmentEE {

	public EnchantmentAttackSpeed(int par1, int par2) {
		super(par1, new ResourceLocation("attackSpeed"), par2, EnumEnchantmentType.WEAPON);
		this.setName("attackSpeed");
	}
	
	public int getMinEnchantability(int par1){
		return par1*6+1;
	}
	
	public int getMaxEnchantability(int par1){
		return this.getMinEnchantability(par1) + 50;
	}
	
	@Override
	public int getMaxLevel() {
		return 4;
	}
	
	@Override
	public boolean canApplyTogether(Enchantment enchantment) {
		return enchantment instanceof EnchantmentKnockback? false : super.canApplyTogether(enchantment);
	}
}
