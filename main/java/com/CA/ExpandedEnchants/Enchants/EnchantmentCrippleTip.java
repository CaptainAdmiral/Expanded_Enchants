package com.CA.ExpandedEnchants.Enchants;

import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

public class EnchantmentCrippleTip extends EnchantmentEE {
	
	public EnchantmentCrippleTip(int par1, int par2) {
		super(par1, new ResourceLocation("crippleTip"), par2, EnumEnchantmentType.BOW);
		this.setName("crippleTip");
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
}
