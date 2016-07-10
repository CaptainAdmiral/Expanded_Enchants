package com.CA.ExpandedEnchants.Enchants;

import com.CA.ExpandedEnchants.EEMain;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class EnchantmentEE extends Enchantment {

	protected EnchantmentEE(int par1, ResourceLocation par2ResourceLocation, int par3, EnumEnchantmentType par3EnumEnchantmentType) {
		super(par1, par2ResourceLocation, par3, par3EnumEnchantmentType);
		EEMain.enchantmentList[par1] = this;
	}
	
	public boolean isNetherEnchant() {
		return false;
	}
	
	@Override
	public boolean canApply(ItemStack p_92089_1_) {
		return super.canApplyAtEnchantingTable(p_92089_1_);
	}
	
	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack) {
		return isNetherEnchant()? false : super.canApplyAtEnchantingTable(stack);
	}
}
