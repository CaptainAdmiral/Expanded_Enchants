package com.CA.ExpandedEnchants;


import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CreativeTabsEE extends CreativeTabs{

	public CreativeTabsEE(String lable) {
		super(lable);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Item getTabIconItem() {
		return Item.getItemFromBlock(EEMain.xpTable);
	}
	
	@Override
	public String getTranslatedTabLabel() {
		return "Expanded Enchants";
	}
}
