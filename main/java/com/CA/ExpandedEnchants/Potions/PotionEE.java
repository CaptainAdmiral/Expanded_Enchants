package com.CA.ExpandedEnchants.Potions;

import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;

/**Potion does not have a visible constructor */
public class PotionEE extends Potion {

	/**Visible constructor, otherwise functionally identical to potion*/
	public PotionEE(int potionID, ResourceLocation location, boolean badEffect, int potionColor) {
		super(potionID, location, badEffect, potionColor);
	}
}
