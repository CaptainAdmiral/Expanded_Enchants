package com.CA.ExpandedEnchants.Enchants;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.util.ResourceLocation;

import com.CA.ExpandedEnchants.EEMain;

public class EnchantmentPoisonTip extends EnchantmentEE {
	
	public EnchantmentPoisonTip(int par1, int par2) {
		super(par1, new ResourceLocation("poisonTip"), par2, EnumEnchantmentType.BOW);
		this.setName("poisonTip");
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
	public boolean canApplyTogether(Enchantment par1Enchantment) {
		return par1Enchantment == EEMain.witherTip? false : super.canApplyTogether(par1Enchantment);
	}
}
