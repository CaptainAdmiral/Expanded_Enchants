package com.CA.ExpandedEnchants.Item;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionHelper;
import net.minecraft.stats.StatList;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.CA.ExpandedEnchants.EEMain;
import com.CA.ExpandedEnchants.Entity.EntityPotionEE;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;

public class ItemPotionEE extends ItemPotion {

	private static class PotionData {

		private final Potion POTION;
		private final int BASEDURATION;
		private final boolean HASEXTENDEDDURATION;
		private final boolean HASAMPLIFIER;

		public PotionData(Potion potion, int baseDuration, boolean hasExtendedDuration, boolean hasAmplifier) {
			this.POTION = potion;
			this.BASEDURATION = baseDuration;
			this.HASEXTENDEDDURATION = hasExtendedDuration;
			this.HASAMPLIFIER = hasAmplifier;
		}

		public Potion getPotion() {
			return this.POTION;
		}

		public int getBaseDuration(){
			return this.BASEDURATION;
		}

		public boolean hasExtendedDuration() {
			return this.HASEXTENDEDDURATION;
		}

		public boolean hasAmplifier() {
			return this.HASAMPLIFIER;
		}
	}

	private static List<PotionData> potionsList = new ArrayList<PotionData>();

	public ItemPotionEE() {
		this.setMaxStackSize(1);
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
		this.setCreativeTab(CreativeTabs.tabBrewing);
	}

	/**Registers a potion effect for a metadata subtype*/
	public static void registerPotion(Potion potion, int baseDuration, boolean hasExtendedDuration, boolean hasAmplifier) {
		potionsList.add(new PotionData(potion, baseDuration, hasExtendedDuration, hasAmplifier));
	}
	
	public static int getMetadata(Potion potion, int modifier) {
		Iterator iterator = potionsList.iterator();
		PotionData potionData;
		while(iterator.hasNext()) {
			potionData = (PotionData) iterator.next();
			if(potionData.getPotion() == potion) {
				return potionsList.indexOf(potionData) + modifier;
			}
		}
		return 0;
	}

	@Override
	public List getEffects(ItemStack stack) {
		if (stack.hasTagCompound() && stack.getTagCompound().hasKey("CustomPotionEffects", 9))
		{
			ArrayList arraylist = Lists.newArrayList();
			NBTTagList nbttaglist = stack.getTagCompound().getTagList("CustomPotionEffects", 10);

			for (int i = 0; i < nbttaglist.tagCount(); ++i)
			{
				NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
				PotionEffect potioneffect = PotionEffect.readCustomPotionEffectFromNBT(nbttagcompound);

				if (potioneffect != null)
				{
					arraylist.add(potioneffect);
				}
			}

			return arraylist;
		}
		else return getEffects(stack.getMetadata());
	}

	@Override
	public List getEffects(int meta) {
		int base = Math.floorDiv(meta, 6);
		boolean isLong = (meta+1)%3==0;
		boolean isAmplified = (meta+2)%3==0;

		if(base >= this.potionsList.size()) return null;
		PotionData potionData = this.potionsList.get(base);
		List list = (List) Lists.newArrayList();

		list.add(new PotionEffect(potionData.getPotion().getId(), isLong? potionData.BASEDURATION*2 : isAmplified? potionData.BASEDURATION/2 : potionData.BASEDURATION, isAmplified? 1:0));

		return list;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn) {
		if (isSplash(itemStackIn.getMetadata()))
		{
			if (!playerIn.capabilities.isCreativeMode)
			{
				--itemStackIn.stackSize;
			}

			worldIn.playSoundAtEntity(playerIn, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

			if (!worldIn.isRemote)
			{
				worldIn.spawnEntityInWorld(new EntityPotionEE(worldIn, playerIn, itemStackIn));
			}

			playerIn.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
			return itemStackIn;
		}
		else
		{
			playerIn.setItemInUse(itemStackIn, this.getMaxItemUseDuration(itemStackIn));
			return itemStackIn;
		}
	}

	@SideOnly(Side.CLIENT)
	public int getColorFromDamage(int meta) {
		return PotionHelper.getLiquidColor(meta, false);
	}

	public static boolean isSplash(int meta) {
		return meta%6>=3;
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack) {

		String s = "";

		if (isSplash(stack.getMetadata()))
		{
			s = StatCollector.translateToLocal("potion.prefix.grenade").trim() + " ";
		}

		List list = ((ItemPotionEE) EEMain.potion).getEffects(stack);
		String s1;

		if (list != null && !list.isEmpty())
		{
			s1 = ((PotionEffect)list.get(0)).getEffectName();
			s1 = s1 + ".postfix";
			return s + StatCollector.translateToLocal(s1).trim();
		}
		else
		{
			return "INVALID POTION EFFECT STATE";
		}

	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List tooltip, boolean advanced) {
		List list1 = ((ItemPotionEE) EEMain.potion).getEffects(stack);
		HashMultimap hashmultimap = HashMultimap.create();
		Iterator iterator1;

		if (list1 != null && !list1.isEmpty()) {
			iterator1 = list1.iterator();

			while (iterator1.hasNext()) {
				PotionEffect potioneffect = (PotionEffect)iterator1.next();
				String s1 = StatCollector.translateToLocal(potioneffect.getEffectName()).trim();
				Potion potion = Potion.potionTypes[potioneffect.getPotionID()];
				Map map = potion.getAttributeModifierMap();

				if (map != null && map.size() > 0)
				{
					Iterator iterator = map.entrySet().iterator();

					while (iterator.hasNext())
					{
						Entry entry = (Entry)iterator.next();
						AttributeModifier attributemodifier = (AttributeModifier)entry.getValue();
						AttributeModifier attributemodifier1 = new AttributeModifier(attributemodifier.getName(), potion.getAttributeModifierAmount(potioneffect.getAmplifier(), attributemodifier), attributemodifier.getOperation());
						hashmultimap.put(((IAttribute)entry.getKey()).getAttributeUnlocalizedName(), attributemodifier1);
					}
				}

				if (potioneffect.getAmplifier() > 0)
				{
					s1 = s1 + " " + StatCollector.translateToLocal("potion.potency." + potioneffect.getAmplifier()).trim();
				}

				if (potioneffect.getDuration() > 20)
				{
					s1 = s1 + " (" + Potion.getDurationString(potioneffect) + ")";
				}

				if (potion.isBadEffect())
				{
					tooltip.add(EnumChatFormatting.RED + s1);
				}
				else
				{
					tooltip.add(EnumChatFormatting.GRAY + s1);
				}
			}


			if (!hashmultimap.isEmpty())
			{
				tooltip.add("");
				tooltip.add(EnumChatFormatting.DARK_PURPLE + StatCollector.translateToLocal("potion.effects.whenDrank"));
				iterator1 = hashmultimap.entries().iterator();

				while (iterator1.hasNext())
				{
					Entry entry1 = (Entry)iterator1.next();
					AttributeModifier attributemodifier2 = (AttributeModifier)entry1.getValue();
					double d0 = attributemodifier2.getAmount();
					double d1;

					if (attributemodifier2.getOperation() != 1 && attributemodifier2.getOperation() != 2)
					{
						d1 = attributemodifier2.getAmount();
					}
					else
					{
						d1 = attributemodifier2.getAmount() * 100.0D;
					}

					if (d0 > 0.0D)
					{
						tooltip.add(EnumChatFormatting.BLUE + StatCollector.translateToLocalFormatted("attribute.modifier.plus." + attributemodifier2.getOperation(), new Object[] {ItemStack.DECIMALFORMAT.format(d1), StatCollector.translateToLocal("attribute.name." + (String)entry1.getKey())}));
					}
					else if (d0 < 0.0D)
					{
						d1 *= -1.0D;
						tooltip.add(EnumChatFormatting.RED + StatCollector.translateToLocalFormatted("attribute.modifier.take." + attributemodifier2.getOperation(), new Object[] {ItemStack.DECIMALFORMAT.format(d1), StatCollector.translateToLocal("attribute.name." + (String)entry1.getKey())}));
					}
				}
			}
		} else { 
			tooltip.add(EnumChatFormatting.DARK_PURPLE + "Pls tell me how you got one of these");
			tooltip.add(EnumChatFormatting.DARK_PURPLE + "(You cheeky monkey you)");
		}
		
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item itemIn, CreativeTabs tab, List subItems) {
		for(int i=0; i<this.potionsList.size();i++) {
			PotionData potionData = this.potionsList.get(i);
			for(int j=0; j<6; j++){
				if(j%3==1&&!potionData.HASAMPLIFIER) continue;
				if(j%3==2&&!potionData.HASEXTENDEDDURATION) continue;;
				subItems.add(new ItemStack(itemIn, 1, 6*i+j));
			}
		}    
	}
}
