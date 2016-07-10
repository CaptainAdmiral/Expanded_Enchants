package com.CA.ExpandedEnchants;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraftforge.common.ChestGenHooks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.brewing.BrewingRecipe;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;

import com.CA.ExpandedEnchants.Block.BlockFluidXP;
import com.CA.ExpandedEnchants.Block.BlockXPTable;
import com.CA.ExpandedEnchants.Block.BlockXPTableBase;
import com.CA.ExpandedEnchants.Block.BlockXp;
import com.CA.ExpandedEnchants.Enchants.EnchantmentAttackSpeed;
import com.CA.ExpandedEnchants.Enchants.EnchantmentAuraSlow;
import com.CA.ExpandedEnchants.Enchants.EnchantmentBlindingTip;
import com.CA.ExpandedEnchants.Enchants.EnchantmentBossDamage;
import com.CA.ExpandedEnchants.Enchants.EnchantmentCrippleBlade;
import com.CA.ExpandedEnchants.Enchants.EnchantmentCrippleTip;
import com.CA.ExpandedEnchants.Enchants.EnchantmentCritChance;
import com.CA.ExpandedEnchants.Enchants.EnchantmentDamageCap;
import com.CA.ExpandedEnchants.Enchants.EnchantmentDebuffResist;
import com.CA.ExpandedEnchants.Enchants.EnchantmentDisableAI;
import com.CA.ExpandedEnchants.Enchants.EnchantmentEggDrop;
import com.CA.ExpandedEnchants.Enchants.EnchantmentEnder;
import com.CA.ExpandedEnchants.Enchants.EnchantmentFireResist;
import com.CA.ExpandedEnchants.Enchants.EnchantmentFireSpeed;
import com.CA.ExpandedEnchants.Enchants.EnchantmentFirecloak;
import com.CA.ExpandedEnchants.Enchants.EnchantmentFurnaceDig;
import com.CA.ExpandedEnchants.Enchants.EnchantmentHeadDrop;
import com.CA.ExpandedEnchants.Enchants.EnchantmentHealthRegen;
import com.CA.ExpandedEnchants.Enchants.EnchantmentHeavyTip;
import com.CA.ExpandedEnchants.Enchants.EnchantmentIgnoreDamageChance;
import com.CA.ExpandedEnchants.Enchants.EnchantmentJump;
import com.CA.ExpandedEnchants.Enchants.EnchantmentLifeSteal;
import com.CA.ExpandedEnchants.Enchants.EnchantmentMagicBlade;
import com.CA.ExpandedEnchants.Enchants.EnchantmentMagicResist;
import com.CA.ExpandedEnchants.Enchants.EnchantmentNetherDamage;
import com.CA.ExpandedEnchants.Enchants.EnchantmentNightVision;
import com.CA.ExpandedEnchants.Enchants.EnchantmentOreDig;
import com.CA.ExpandedEnchants.Enchants.EnchantmentPoisonBlade;
import com.CA.ExpandedEnchants.Enchants.EnchantmentPoisonTip;
import com.CA.ExpandedEnchants.Enchants.EnchantmentSpeed;
import com.CA.ExpandedEnchants.Enchants.EnchantmentStoneDig;
import com.CA.ExpandedEnchants.Enchants.EnchantmentTimeStats;
import com.CA.ExpandedEnchants.Enchants.EnchantmentWaterRegen;
import com.CA.ExpandedEnchants.Enchants.EnchantmentWaterWalking;
import com.CA.ExpandedEnchants.Enchants.EnchantmentWitherBlade;
import com.CA.ExpandedEnchants.Enchants.EnchantmentWitherTip;
import com.CA.ExpandedEnchants.Entity.EntityPotionEE;
import com.CA.ExpandedEnchants.Item.ItemPotionEE;
import com.CA.ExpandedEnchants.Item.ItemXPBook;
import com.CA.ExpandedEnchants.Item.ItemXPBucket;
import com.CA.ExpandedEnchants.Item.ItemXPFiller;
import com.CA.ExpandedEnchants.Potions.PotionEE;
import com.google.common.collect.Lists;

@Mod(modid = EEMain.MODID, version = EEMain.VERSION)
public class EEMain {
	//	|| 
	//What was I doing? crafting item (brewing event), buff icon (renderInventoryEffect - Class; Potion), dispensability, all the potion effects, funky brewing stand (maybe crafter from drops from like a funky green blaze boss).
	//TO FIX: Json block state files for textures now
	//TO DO: Refined XP texture cycles through color hues, water walking
	//book explaining mod
	//PLANNED ENCHANTS: Mines entire tree for more damage to item (Impact) (N)
	//Deal AOE damage on hit (Cleave) Deal AOE lightning damage on arrow hit (Static)
	
	//PLANNED POTIONS: Anti gravity, Floaty, Small, Big, Flight, No Jump, Snared, Neurotoxin (makes chat gibberish), Something Colorfull, increased damage taken,
	//travel potion (ender teleport system), return to bed potion, climb up walls, walk through walls, disguises, aphrodesiac, age increase,
	//age decerase, invincibility, cleanse potions, aggression potion (turns passive mobs hostile).

	public static final String MODID = "expandedenchants";
	public static final String VERSION = "1.0";

	public static final int XP_BUCKET_VOLUME = 200;

	@Instance(MODID)
	public static EEMain instance;

	@SidedProxy(modId=MODID, clientSide="com.CA.ExpandedEnchants.Client.ClientProxy", serverSide="com.CA.ExpandedEnchants.ComProxy")
	public static ComProxy comProxy;

	public static Enchantment[] enchantmentList = new Enchantment[256];
	public static int[] disabledList = {};

	public static CreativeTabs tabEE = new CreativeTabsEE("EE");

	public static final Fluid xpFluid = new Fluid("xp", new ResourceLocation(EEMain.MODID, "blocks/xpFluid_still"), new ResourceLocation(EEMain.MODID, "blocks/xpFluid_flow")).setLuminosity(8).setViscosity(2000);

	public static Item potion;
	
	public static Item xpBook;
	public static Item xpBucket;
	public static Item goldenEye;

	public static Block xpFluidBlock;
	public static Block xpTableBase;
	public static Block xpTable;
	public static Block xpBlock;

	public static DamageSource xpDamage;

	private static int curId = 200;
	private static int curPotId = 33;

	private int magicBladeId;
	public static Enchantment magicBlade;
	private int magicTipId;
	public static Enchantment magicTip;
	private int healthRegenId;
	public static Enchantment healthRegen;
	private int speedId;
	public static Enchantment speed;
	private int jumpId;
	public static Enchantment jump;
	private int firecloakId;
	public static Enchantment firecloak;
	private int nightVisionId;
	public static Enchantment nightVision;
	private int magicResistId;
	public static Enchantment magicResist;
	private int debuffResistId;
	public static Enchantment debuffResist;
	private int witherBladeId;
	public static Enchantment witherBlade;
	private int poisonBladeId;
	public static Enchantment poisonBlade;
	private int crippleBladeId;
	public static Enchantment crippleBlade;
	private int witherTipId;
	public static Enchantment witherTip;
	private int poisonTipId;
	public static Enchantment poisonTip;
	private int crippleTipId;
	public static Enchantment crippleTip;
	private int blindingTipId;
	public static Enchantment blindingTip;
	private int lifeStealId;
	public static Enchantment lifeSteal;
	private int waterWalkingId;
	public static Enchantment waterWalking;
	private int enderId;
	public static Enchantment ender;
	private int furnaceDigId;
	public static Enchantment furnaceDig;
	private int attackSpeedId;
	public static Enchantment attackSpeed;
	private int eggDropId;
	public static Enchantment eggDrop;
	private int auraSlowId;
	public static Enchantment auraSlow;
	private int ignoreDamageChanceId;
	public static Enchantment ignoreDamageChance;
	private int damageCapId;
	public static Enchantment damageCap;
	private int waterRegenId;
	public static Enchantment waterRegen;
	private int nightStatsId;
	public static Enchantment nightStats;
	private int dayStatsId;
	public static Enchantment dayStats;
	private int critChanceId;
	public static Enchantment critChance;
	private int critChanceTipId;
	public static Enchantment critChanceTip;
	private int bossDamageId;
	public static Enchantment bossDamage;
	private int bossDamageTipId;
	public static Enchantment bossDamageTip;
	private int netherDamageId;
	public static Enchantment netherDamage;
	private int oreDigId;
	public static Enchantment oreDig;
	private int stoneDigId;
	public static Enchantment stoneDig;
	private int headDropId;
	public static Enchantment headDrop;
	private int disableAIId;
	public static Enchantment disableAI;
	private int fireSpeedId;
	public static Enchantment fireSpeed;
	private int fireResistId;
	public static Enchantment fireResist;
	private int heavyTipId;
	public static Enchantment heavyTip;
	
	private int fastFallId;
	public static Potion fastFall;
	
	private int stickyId;
	public static Potion sticky;

	private static int getNextId() {
		return curId++;
	}
	
	private static int getNextPotId() {
		return curPotId++;
	}

	@EventHandler
	public void preInit(FMLPreInitializationEvent e) {
		Configuration config = new Configuration(e.getSuggestedConfigurationFile());
		config.load();

		config.addCustomCategoryComment("Enchantments", "Enchantment Ids");
		config.addCustomCategoryComment("Enchantments", "Id's must be a positive number less than 256");

		magicBladeId = config.get("Enchantments", "Arcane Edge", getNextId()).getInt();
		magicTipId = config.get("Enchantments", "Runic Tip", getNextId()).getInt();
		healthRegenId = config.get("Enchantments", "Survivalist", getNextId()).getInt();
		speedId = config.get("Enchantments", "Fleetfooted", getNextId()).getInt();
		jumpId = config.get("Enchantments", "Springheeled", getNextId()).getInt();
		firecloakId = config.get("Enchantments", "Sear", getNextId()).getInt();
		nightVisionId = config.get("Enchantments", "Perception", getNextId()).getInt();
		magicResistId = config.get("Enchantments", "Spell Protection", getNextId()).getInt();
		debuffResistId = config.get("Enchantments", "Resilience", getNextId()).getInt();
		witherBladeId = config.get("Enchantments", "Impure", getNextId()).getInt();
		poisonBladeId = config.get("Enchantments", "Venomous Edge", getNextId()).getInt();
		crippleBladeId = config.get("Enchantments", "Crippling", getNextId()).getInt();
		witherTipId = config.get("Enchantments", "Cursed", getNextId()).getInt();
		poisonTipId = config.get("Enchantments", "Vilepoint", getNextId()).getInt();
		crippleTipId = config.get("Enchantments", "Frostqueens Claim", getNextId()).getInt();
		blindingTipId = config.get("Enchantments", "Abyssal", getNextId()).getInt();
		lifeStealId = config.get("Enchantments", "Bloodthirster", getNextId()).getInt();
		waterWalkingId = config.get("Enchantments", "Tidewalker", getNextId()).getInt();
		enderId = config.get("Enchantments", "Ender", getNextId()).getInt();
		furnaceDigId = config.get("Enchantments", "Molten", getNextId()).getInt();
		attackSpeedId = config.get("Enchantments", "Swift Strikes", getNextId()).getInt();
		eggDropId = config.get("Enchantments", "Essence Reaver", getNextId()).getInt();
		auraSlowId = config.get("Enchantments", "Frozen", getNextId()).getInt();
		ignoreDamageChanceId = config.get("Enchantments", "Juggernaught", getNextId()).getInt();
		damageCapId = config.get("Enchantments", "Sturdy", getNextId()).getInt();
		waterRegenId = config.get("Enchantments", "Faron's Blessing", getNextId()).getInt();
		dayStatsId = config.get("Enchantments", "Solar", getNextId()).getInt();
		nightStatsId = config.get("Enchantments", "Lunar", getNextId()).getInt();
		critChanceId = config.get("Enchantments", "Savage", getNextId()).getInt();
		critChanceTipId = config.get("Enchantments", "Critical", getNextId()).getInt();
		bossDamageId = config.get("Enchantments", "Dragonslayer", getNextId()).getInt();
		bossDamageTipId = config.get("Enchantments", "Dragonsbane", getNextId()).getInt();
		netherDamageId = config.get("Enchantments", "Demonblade", getNextId()).getInt();
		oreDigId = config.get("Enchantments", "Averice", getNextId()).getInt();
		stoneDigId = config.get("Enchantments", "Mountainbreaker", getNextId()).getInt();
		headDropId = config.get("Enchantments", "Decapitator", getNextId()).getInt();
		disableAIId= config.get("Enchantments", "Neurotoxin", getNextId()).getInt();
		fireSpeedId= config.get("Enchantments", "Rapidfire", getNextId()).getInt();
		fireResistId= config.get("Enchantments", "Demonborn", getNextId()).getInt();
		heavyTipId= config.get("Enchantments", "Skykiller", getNextId()).getInt();
		
		config.addCustomCategoryComment("Disable Enchantments", "Please add each enchantment id you wish to disable on a seperate line");
		this.disabledList = config.get("Disable Enchantments", "DISABLED ID'S", this.disabledList).getIntList();
		
		config.addCustomCategoryComment("Potions", "Potion Ids");
		config.addCustomCategoryComment("Potions", "Id's must be a positive number less than 64");
		
		fastFallId = config.get("Potions", "Heavy", getNextPotId()).getInt();
		stickyId = config.get("Potions", "Climbing", getNextPotId()).getInt();
		
		config.save();
	}

	static void modifyAllEnchantmentArrays(Field[] field, Object object) throws Exception {
		for (Field f : field) {
			if (Modifier.isStatic(f.getModifiers()) && Modifier.isFinal(f.getModifiers()) && Modifier.isPublic(f.getModifiers()) && f.getType().isArray()) {
				f.setAccessible(true);

				Field modifiersField = Field.class.getDeclaredField("modifiers");
				modifiersField.setAccessible(true);
				modifiersField.setInt(f, f.getModifiers() & ~Modifier.FINAL);

				f.set(null, object);
			}
		}
	}

	@EventHandler
	public void init(FMLInitializationEvent e) {
		magicBlade = new EnchantmentMagicBlade(this.magicBladeId, new ResourceLocation("magicBlade"), 7, EnumEnchantmentType.WEAPON).setName("magicBlade");
		magicTip = new EnchantmentMagicBlade(this.magicTipId, new ResourceLocation("magicTip"), 7, EnumEnchantmentType.BOW).setName("magicTip");
		healthRegen = new EnchantmentHealthRegen(this.healthRegenId, 6);
		speed = new EnchantmentSpeed(this.speedId, 5);
		jump = new EnchantmentJump(this.jumpId, 5);
		firecloak = new EnchantmentFirecloak(this.firecloakId, 6);
		nightVision = new EnchantmentNightVision(this.nightVisionId, 2);
		magicResist = new EnchantmentMagicResist(this.magicResistId, 5);
		debuffResist = new EnchantmentDebuffResist(this.debuffResistId, 5);
		witherBlade = new EnchantmentWitherBlade(this.witherBladeId, 2);
		poisonBlade = new EnchantmentPoisonBlade(this.poisonBladeId, 5);
		crippleBlade = new EnchantmentCrippleBlade(this.crippleBladeId, 4);
		witherTip = new EnchantmentWitherTip(this.witherTipId, 2);
		poisonTip = new EnchantmentPoisonTip(this.poisonTipId, 5);
		crippleTip = new EnchantmentCrippleTip(this.crippleTipId, 5);
		blindingTip = new EnchantmentBlindingTip(this.blindingTipId, 4);
		lifeSteal = new EnchantmentLifeSteal(this.lifeStealId, 2);
		waterWalking = new EnchantmentWaterWalking(this.waterWalkingId, 2);
		ender = new EnchantmentEnder(this.enderId, 6);
		furnaceDig = new EnchantmentFurnaceDig(this.furnaceDigId, 6);
		attackSpeed = new EnchantmentAttackSpeed(this.attackSpeedId, 7);
		eggDrop = new EnchantmentEggDrop(this.eggDropId, 1);
		auraSlow = new EnchantmentAuraSlow(this.auraSlowId, 5);
		ignoreDamageChance = new EnchantmentIgnoreDamageChance(this.ignoreDamageChanceId, 4);
		damageCap = new EnchantmentDamageCap(this.damageCapId, 5);
		waterRegen = new EnchantmentWaterRegen(this.waterRegenId, 3);
		dayStats = new EnchantmentTimeStats(this.dayStatsId, new ResourceLocation("dayStats"), 1).setName("dayStats");
		nightStats = new EnchantmentTimeStats(this.nightStatsId, new ResourceLocation("nightStats"), 1).setName("nightStats");
		critChance = new EnchantmentCritChance(this.critChanceId, new ResourceLocation("critChance"), 7, EnumEnchantmentType.WEAPON).setName("critChance");
		critChanceTip = new EnchantmentCritChance(this.critChanceTipId, new ResourceLocation("critChanceTip"), 7, EnumEnchantmentType.BOW).setName("critChanceTip");
		bossDamage = new EnchantmentBossDamage(this.bossDamageId, new ResourceLocation("bossDamage"), 3, EnumEnchantmentType.WEAPON).setName("bossDamage");
		bossDamageTip = new EnchantmentBossDamage(this.bossDamageTipId, new ResourceLocation("bossDamageTip"), 3, EnumEnchantmentType.BOW).setName("bossDamageTip");
		netherDamage = new EnchantmentNetherDamage(this.netherDamageId, 5);
		oreDig = new EnchantmentOreDig(this.oreDigId, 5);
		stoneDig = new EnchantmentStoneDig(this.stoneDigId, 4);
		headDrop = new EnchantmentHeadDrop(this.headDropId, 1);
		disableAI = new EnchantmentDisableAI(this.disableAIId, 3);
		fireSpeed = new EnchantmentFireSpeed(this.fireSpeedId, 6);
		fireResist = new EnchantmentFireResist(this.fireResistId, 2);
		heavyTip = new EnchantmentHeavyTip(this.heavyTipId, 2);
		
		ArrayList var0 = Lists.newArrayList();
		Enchantment[] var1 = Enchantment.enchantmentsBookList;
		int var2 = var1.length;

		for (int var3 = 0; var3 < var2; ++var3) {
			Enchantment var4 = var1[var3];

			if (var4 != null)
			{
				var0.add(var4);
			}
		}

		var1 = this.enchantmentList;
		var2 = var1.length;

		for (int var3 = 0; var3 < var2; ++var3) {
			Enchantment var4 = var1[var3];

			if (var4 != null)
			{
				var0.add(var4);
			}
		}
		
		ArrayList disEnchList = new ArrayList();
		for(int i : this.disabledList) {
			disEnchList.add(Enchantment.getEnchantmentById(i));
		}
		
		var0.removeAll(disEnchList);

		try {
			System.out.println("Expanded Enchants: Applying reflect modifiers for EnchantmentList[]");
			System.out.println("(And yes, it's a janky workaround)");
			this.modifyAllEnchantmentArrays(Enchantment.class.getDeclaredFields(), (Enchantment[])var0.toArray(new Enchantment[var0.size()]));
			System.out.println("Success!");
			System.out.println("(And no, I'm not proud of what I've done here)");
		} catch (NoSuchFieldException e1) {
			e1.printStackTrace();
		} catch (SecurityException e1) {
			e1.printStackTrace();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		
		Potion[] potionTypes;
		for (Field f : Potion.class.getDeclaredFields()) {
			f.setAccessible(true);
			try {
				if (f.getName().equals("potionTypes") || f.getName().equals("field_76425_a")) {
					Field modfield = Field.class.getDeclaredField("modifiers");
					modfield.setAccessible(true);
					modfield.setInt(f, f.getModifiers() & ~Modifier.FINAL);

					potionTypes = (Potion[])f.get(null);
					final Potion[] newPotionTypes = new Potion[potionTypes.length + 32];
					System.arraycopy(potionTypes, 0, newPotionTypes, 0, potionTypes.length);
					f.set(null, newPotionTypes);
				}
			}
			catch (Exception e1) {
				System.err.println("Error on applying reflect modifiers to potion array length");
				System.err.println(e1);
			}
		}
		
		int potEntityId = EntityRegistry.findGlobalUniqueEntityId();
		EntityRegistry.registerGlobalEntityID(EntityPotionEE.class, "potionEE", potEntityId);
		EntityRegistry.registerModEntity(EntityPotionEE.class, "potionEE", potEntityId, this.instance, 50, 10, true);
		
		fastFall = new PotionEE(this.fastFallId, new ResourceLocation("fire_resistance"), false, 14981690).setPotionName("potion.fastFall");
		ItemPotionEE.registerPotion(this.fastFall, 200, true, true);
		BrewingRecipeRegistry.addRecipe(new ItemStack(Items.milk_bucket, 1, 0), new ItemStack(Blocks.obsidian), new ItemStack(this.potion, 1, ItemPotionEE.getMetadata(this.fastFall, 0)));
		
		sticky = new PotionEE(this.stickyId, new ResourceLocation("fire_resistance"), false, 14981690).setPotionName("potion.sticky");
		ItemPotionEE.registerPotion(this.sticky, 2400, true, false);
		
		TextureMap texturemap = Minecraft.getMinecraft().getTextureMapBlocks();

		xpDamage = new DamageSource("xp").setDamageBypassesArmor().setMagicDamage();
		
		potion = new ItemPotionEE().setUnlocalizedName("potion");
		GameRegistry.registerItem(potion, potion.getUnlocalizedName().substring(5));
		
		xpBook = new ItemXPBook().setUnlocalizedName("xpBook").setCreativeTab(this.tabEE);
		goldenEye = new ItemXPFiller().setUnlocalizedName("goldenEye").setCreativeTab(this.tabEE);

		xpTableBase = new BlockXPTableBase().setUnlocalizedName("xpTableBase").setHardness(5.0F).setResistance(2000.0F).setCreativeTab(this.tabEE);
		xpTable = new BlockXPTable().setUnlocalizedName("xpTable").setHardness(5.0F).setResistance(2000.0F).setCreativeTab(this.tabEE);
		xpBlock = new BlockXp().setUnlocalizedName("xpBlock").setHardness(3.0F).setLightLevel(0.8F).setResistance(2000.0F).setCreativeTab(tabEE);

		GameRegistry.registerItem(goldenEye, goldenEye.getUnlocalizedName().substring(5));
		GameRegistry.addRecipe(new ItemStack(goldenEye), "iii", "iji", "iii", 'i', Items.gold_ingot, 'j', Items.ender_pearl);

		GameRegistry.registerBlock(xpBlock, xpBlock.getUnlocalizedName().substring(5));
		GameRegistry.addShapelessRecipe(new ItemStack(this.xpBlock), new ItemStack(this.xpBucket), new ItemStack(Blocks.gold_block));

		FluidRegistry.registerFluid(xpFluid);
		xpFluidBlock = new BlockFluidXP(xpFluid, Material.water).setUnlocalizedName("xpFluid");
		GameRegistry.registerBlock(xpFluidBlock, xpFluidBlock.getUnlocalizedName().substring(5));
		xpFluid.setUnlocalizedName(xpFluidBlock.getUnlocalizedName());

		xpBucket = new ItemXPBucket(xpFluidBlock).setUnlocalizedName("xpBucket").setCreativeTab(this.tabEE);

		GameRegistry.registerItem(xpBucket, xpBucket.getUnlocalizedName().substring(5));
		FluidContainerRegistry.registerFluidContainer(FluidRegistry.getFluidStack(xpFluid.getName(), FluidContainerRegistry.BUCKET_VOLUME), new ItemStack(xpBucket), new ItemStack(Items.bucket));
		GameRegistry.addShapelessRecipe(new ItemStack(this.xpBucket), new ItemStack(this.xpBlock), new ItemStack(Items.bucket));
		
		GameRegistry.registerBlock(xpTableBase, xpTableBase.getUnlocalizedName().substring(5));
		GameRegistry.addRecipe(new ItemStack(this.xpTableBase), "iji", "klk", "kkk", 'i', Items.diamond, 'j', Items.emerald, 'k', Blocks.obsidian, 'l', Blocks.gold_block);
		GameRegistry.registerBlock(xpTable, xpTable.getUnlocalizedName().substring(5));

		GameRegistry.registerItem(xpBook, xpBook.getUnlocalizedName().substring(5));

		if(e.getSide() == Side.CLIENT) 
		{
			RenderItem renderItem = Minecraft.getMinecraft().getRenderItem();

			renderItem.getItemModelMesher().register(Item.getItemFromBlock(this.xpBlock), 0, new ModelResourceLocation(this.MODID + ":" + "xpBlock", "inventory"));
			renderItem.getItemModelMesher().register(Item.getItemFromBlock(this.xpTableBase), 0, new ModelResourceLocation(this.MODID + ":" + "xpTableBase", "inventory"));
			renderItem.getItemModelMesher().register(Item.getItemFromBlock(this.xpTable), 0, new ModelResourceLocation(this.MODID + ":" + "xpTableBase", "inventory"));

			renderItem.getItemModelMesher().register(this.potion, 0, new ModelResourceLocation(this.MODID + ":" + "potion", "inventory"));
			renderItem.getItemModelMesher().register(this.goldenEye, 0, new ModelResourceLocation(this.MODID + ":" + "goldenEye", "inventory"));
			renderItem.getItemModelMesher().register(this.xpBook, 0, new ModelResourceLocation(this.MODID + ":" + "xpBook", "inventory"));
			renderItem.getItemModelMesher().register(this.xpBucket, 0, new ModelResourceLocation(this.MODID + ":" + "xpBucket", "inventory"));
		}

		int arrowId = EntityRegistry.findGlobalUniqueEntityId();

		ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(new ItemStack(this.xpBook), 0, 0, 5));
		ChestGenHooks.addItem(ChestGenHooks.MINESHAFT_CORRIDOR, new WeightedRandomChestContent(new ItemStack(this.xpBook), 0, 0, 5));
		ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(new ItemStack(this.xpBook), 0, 0, 5));
		ChestGenHooks.addItem(ChestGenHooks.PYRAMID_DESERT_CHEST, new WeightedRandomChestContent(new ItemStack(this.xpBook), 0, 0, 5));
		ChestGenHooks.addItem(ChestGenHooks.PYRAMID_JUNGLE_CHEST, new WeightedRandomChestContent(new ItemStack(this.xpBook), 0, 0, 5));
		ChestGenHooks.addItem(ChestGenHooks.NETHER_FORTRESS, new WeightedRandomChestContent(new ItemStack(this.xpBook), 0, 0, 7));
		ChestGenHooks.addItem(ChestGenHooks.STRONGHOLD_LIBRARY, new WeightedRandomChestContent(new ItemStack(this.xpBook), 0, 0, 7));

		MinecraftForge.EVENT_BUS.register(new EEHooks());
		NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());
//		comProxy.RegisterEntityRenders();
		//		comProxy.RegisterRenderHooks();
	}
}