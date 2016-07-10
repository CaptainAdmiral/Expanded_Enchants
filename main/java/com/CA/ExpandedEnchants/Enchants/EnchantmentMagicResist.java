package com.CA.ExpandedEnchants.Enchants;

import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.util.ResourceLocation;

public class EnchantmentMagicResist extends EnchantmentEE {

	public EnchantmentMagicResist(int par1, int par2) {
		super(par1, new ResourceLocation("magicResist"), par2, EnumEnchantmentType.ARMOR);
		this.setName("magicResist");
	}
	
	@Override
	public int getMinEnchantability(int par1){
		return par1*6+1;
	}
	
	@Override
	public int getMaxEnchantability(int par1){
		return this.getMinEnchantability(par1) + 50;
	}
	
	@Override
	public int getMaxLevel() {
		return 4;
	}
}
