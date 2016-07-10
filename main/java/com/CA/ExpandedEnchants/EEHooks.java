package com.CA.ExpandedEnchants;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import net.minecraft.block.BlockGravel;
import net.minecraft.block.BlockNetherrack;
import net.minecraft.block.BlockOre;
import net.minecraft.block.BlockRedstoneOre;
import net.minecraft.block.BlockStone;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.boss.IBossDisplayData;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed;
import net.minecraftforge.event.entity.player.PlayerUseItemEvent;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EEHooks {

	AttributeModifier speedMod;
	private final UUID SPEEDMODID = UUID.fromString("92313db4-2f8f-4c00-a2da-8365372b5626");
	private final double SPEEDMODAMOUNT = 0.20000000298023224D;

	public EEHooks() {
		speedMod = new AttributeModifier(this.SPEEDMODID, "speedMod", this.SPEEDMODAMOUNT, 2);
	}

	@SubscribeEvent
	public void onBucketFill(FillBucketEvent e) {
		if(e.current.getItem() != Items.bucket) return;

		ItemStack result = fillCustomBucket(e.world, e.target);

		if (result == null) return;

		e.result = result;
		e.setResult(Result.ALLOW);
	}

	private ItemStack fillCustomBucket(World world, MovingObjectPosition pos) {
		BlockPos block = new BlockPos(pos.hitVec.xCoord, pos.hitVec.yCoord, pos.hitVec.zCoord);
		if(world.getBlockState(block).getBlock() != EEMain.xpFluidBlock) return null;

		if(world.getBlockState(block).getBlock() == EEMain.xpFluidBlock) {
			if (world.getBlockState(block).getValue(BlockFluidClassic.LEVEL).equals(0)) {
				world.setBlockToAir(block);
				return new ItemStack(EEMain.xpBucket);
			} else return null;
		} 

		return null;
	}

	private boolean hasSkull(EntityLivingBase entity) {
		if(entity == null) return false;
		return entity instanceof EntitySkeleton || entity instanceof EntityZombie || entity instanceof EntityCreeper || entity instanceof EntityPlayer;
	}

	@SubscribeEvent
	public void onEntityKilled(LivingDropsEvent e) {
		if(e.entityLiving instanceof IBossDisplayData) return;
		//		if(!(e.entityLiving.func_94060_bK() instanceof EntityPlayer)) return;
		if(!(e.source.getEntity() instanceof EntityPlayer)) return;
		if(e.source.isProjectile()) return;
		if(EnchantmentHelper.getEnchantmentLevel(EEMain.eggDrop.effectId, ((EntityLivingBase)e.source.getEntity()).getHeldItem()) > 0){
			if(e.entityLiving.getRNG().nextInt(30) == 0) e.drops.add(new EntityItem(e.entityLiving.worldObj, e.entityLiving.posX, e.entityLiving.posY, e.entityLiving.posZ, new ItemStack(Items.spawn_egg, 1, EntityList.getEntityID(e.entityLiving))));
		}
		if(EnchantmentHelper.getEnchantmentLevel(EEMain.headDrop.effectId, ((EntityLivingBase)e.source.getEntity()).getHeldItem()) > 0 && this.hasSkull(e.entityLiving)){
			if(e.entityLiving.getRNG().nextInt(100 / EnchantmentHelper.getEnchantmentLevel(EEMain.headDrop.effectId, ((EntityLivingBase)e.source.getEntity()).getHeldItem())) == 0) {
				ItemStack stack = null;
				if(e.entityLiving instanceof EntitySkeleton && ((EntitySkeleton)e.entityLiving).getSkeletonType() == 0) {
					stack = new ItemStack(Items.skull, 1, 0);
				} else if(e.entityLiving instanceof EntitySkeleton && ((EntitySkeleton)e.entityLiving).getSkeletonType() == 1) {
					stack = new ItemStack(Items.skull, 1, 1);
				} else if(e.entityLiving instanceof EntityZombie) {
					stack = new ItemStack(Items.skull, 1, 2);
				} else if(e.entityLiving instanceof EntityPlayer) {
					stack = new ItemStack(Items.skull, 1, 3);
					stack.setTagCompound(new NBTTagCompound());
					if(stack.getTagCompound() != null) stack.getTagCompound().setString("SkullOwner", ((EntityPlayer)e.entityLiving).getDisplayName().toString());
				}  else if(e.entityLiving instanceof EntityCreeper) {
					stack = new ItemStack(Items.skull, 1, 4);
				}
				e.drops.add(new EntityItem(e.entityLiving.worldObj, e.entityLiving.posX, e.entityLiving.posY, e.entityLiving.posZ, stack));
			}
		}
	}

	@SubscribeEvent
	public void onBreaking(BreakSpeed e) {
		if(EnchantmentHelper.getEnchantmentLevel(EEMain.oreDig.effectId, e.entityLiving.getHeldItem()) > 0 && e.state.getBlock() instanceof BlockOre || e.state.getBlock() instanceof BlockRedstoneOre) {
			e.newSpeed = e.originalSpeed * 0.5F * (2 + EnchantmentHelper.getEnchantmentLevel(EEMain.oreDig.effectId, e.entityLiving.getHeldItem()));
		}
		if(EnchantmentHelper.getEnchantmentLevel(EEMain.stoneDig.effectId, e.entityLiving.getHeldItem()) > 0 && e.state.getBlock() instanceof BlockStone || e.state.getBlock() instanceof BlockGravel || e.state.getBlock() instanceof BlockNetherrack) {
			e.newSpeed = e.originalSpeed * 0.5F * (2 + EnchantmentHelper.getEnchantmentLevel(EEMain.stoneDig.effectId, e.entityLiving.getHeldItem()));
		}
	}

	@SubscribeEvent
	public void onBlockBroken(BreakEvent e) {
		if(e.getPlayer().getHeldItem() == null) return;

		if(EnchantmentHelper.getEnchantmentLevel(EEMain.ender.effectId, e.getPlayer().getHeldItem()) > 0) {
			e.getPlayer().addExperience(e.getExpToDrop());
			e.setExpToDrop(0);
		}
	}

	@SubscribeEvent
	public void onBlockDrops(HarvestDropsEvent e) {	
		if(e.harvester == null) return;
		if(e.harvester.getHeldItem() == null) return;

		List<ItemStack> list = new ArrayList();
		list.addAll(e.drops);

		if(EnchantmentHelper.getEnchantmentLevel(EEMain.furnaceDig.effectId, e.harvester.getHeldItem()) > 0) {
			for(Iterator iterator = ((ArrayList<ItemStack>) list).iterator(); iterator.hasNext();) {
				ItemStack item = (ItemStack) iterator.next();
				ItemStack cookedItem = FurnaceRecipes.instance().getSmeltingResult(item);
				if(cookedItem != null) {
					e.drops.remove(item);
					e.drops.add(new ItemStack(cookedItem.getItem(), item.stackSize));
				}
			}
		}

		if(EnchantmentHelper.getEnchantmentLevel(EEMain.ender.effectId, e.harvester.getHeldItem()) > 0) {
			for(Iterator iterator = ((ArrayList<ItemStack>) list).iterator(); iterator.hasNext();) {
				ItemStack item = (ItemStack) iterator.next();
				if(e.harvester.inventory.addItemStackToInventory(item)) {
					e.drops.remove(item);
				}
			}
		}
	}

	@SubscribeEvent
	public void onUsingItem(PlayerUseItemEvent.Tick e) {
		for(int i=0; i < EnchantmentHelper.getEnchantmentLevel(EEMain.fireSpeed.effectId, e.item); i++) e.duration--;

	}

	@SubscribeEvent
	public void onEntityJoinWorld(EntityJoinWorldEvent e) {
		if((e.entity instanceof EntityArrow)) {
			NBTTagCompound nbt = e.entity.getEntityData();
			EntityArrow arrow = (EntityArrow) e.entity;
			if(!(arrow.shootingEntity instanceof EntityLivingBase)) return;
			ItemStack bow = ((EntityLivingBase)arrow.shootingEntity).getHeldItem();
			nbt.setTag("enchantments", bow.getEnchantmentTagList());
		}
	}

	@SubscribeEvent
	public void onJump(LivingJumpEvent e) {
		if(!e.entityLiving.isSneaking()) {		
			if(EnchantmentHelper.getEnchantmentLevel(EEMain.jump.effectId, e.entityLiving.getEquipmentInSlot(1)) > 0) {
				e.entityLiving.motionY += Math.sqrt(2*e.entityLiving.jumpMovementFactor*EnchantmentHelper.getEnchantmentLevel(EEMain.jump.effectId, e.entityLiving.getEquipmentInSlot(1)));
			}
		}
	}

	@SubscribeEvent
	public void onFall(LivingFallEvent e) {
		if(EnchantmentHelper.getEnchantmentLevel(EEMain.jump.effectId, e.entityLiving.getEquipmentInSlot(1)) > 0) {
			e.distance-= EnchantmentHelper.getEnchantmentLevel(EEMain.jump.effectId, e.entityLiving.getEquipmentInSlot(1));
		}

		if(e.entityLiving.isPotionActive(EEMain.fastFall)) e.damageMultiplier *= 2;
	}

	@SubscribeEvent
	public void onLivingAttack(LivingAttackEvent e) {
		if(e.source.getSourceOfDamage() == null) return;

		if(e.source.getSourceOfDamage() instanceof EntityArrow) { //source registering as entityplayer (fucking updates amirite)
			EntityArrow arrow = (EntityArrow) e.source.getSourceOfDamage();
			LinkedHashMap linkedhashmap = new LinkedHashMap();
			NBTTagList nbttaglist = arrow.getEntityData().getTagList("enchantments", 10);

			if (nbttaglist != null)
			{
				for (int i = 0; i < nbttaglist.tagCount(); ++i)
				{
					short short1 = nbttaglist.getCompoundTagAt(i).getShort("id");
					short short2 = nbttaglist.getCompoundTagAt(i).getShort("lvl");
					linkedhashmap.put(Integer.valueOf(short1), Integer.valueOf(short2));
				}

				Random rand = new Random();

				if(linkedhashmap.containsKey(EEMain.magicTip.effectId)) {
					e.entityLiving.attackEntityFrom(DamageSource.causeIndirectMagicDamage(e.entityLiving, arrow.shootingEntity), ((Integer)(linkedhashmap.get(EEMain.magicTip.effectId))).intValue()*2);
				}
				if(linkedhashmap.containsKey(EEMain.blindingTip.effectId)) {
					e.entityLiving.addPotionEffect(new PotionEffect(Potion.blindness.getId(), ((Integer)(linkedhashmap.get(EEMain.blindingTip.effectId))).intValue()*60, 0));
				}
				if(linkedhashmap.containsKey(EEMain.crippleTip.effectId)) {
					e.entityLiving.addPotionEffect(new PotionEffect(Potion.moveSlowdown.getId(), ((Integer)(linkedhashmap.get(EEMain.crippleTip.effectId))).intValue()*60, ((Integer)(linkedhashmap.get(EEMain.crippleTip.effectId))).intValue()));
				}
				if(linkedhashmap.containsKey(EEMain.poisonTip.effectId)) {
					e.entityLiving.addPotionEffect(new PotionEffect(Potion.poison.getId(), ((Integer)(linkedhashmap.get(EEMain.poisonTip.effectId))).intValue()*50, ((Integer)(linkedhashmap.get(EEMain.poisonTip.effectId))).intValue()));
				}   
				if(linkedhashmap.containsKey(EEMain.witherTip.effectId)) {
					e.entityLiving.addPotionEffect(new PotionEffect(Potion.wither.getId(), ((Integer)(linkedhashmap.get(EEMain.witherTip.effectId))).intValue()*50, ((Integer)(linkedhashmap.get(EEMain.witherTip.effectId))).intValue()));
				}
				if(linkedhashmap.containsKey(EEMain.bossDamageTip.effectId)) {
					if(e.entityLiving instanceof IBossDisplayData) {
						e.entityLiving.attackEntityFrom(DamageSource.causeIndirectMagicDamage(e.entityLiving, arrow.shootingEntity), 15 * ((Integer)(linkedhashmap.get(EEMain.bossDamageTip.effectId))).intValue());
					}		
				}
				if(linkedhashmap.containsKey(EEMain.critChanceTip.effectId)) {
					if(arrow.getIsCritical() && e.entityLiving.getRNG().nextInt(10) < ((Integer)(linkedhashmap.get(EEMain.critChanceTip.effectId))).intValue()) {
						e.entityLiving.attackEntityFrom(DamageSource.causeIndirectMagicDamage(e.entityLiving, arrow.shootingEntity), 20);
					}
				}
				if(linkedhashmap.containsKey(EEMain.disableAI.effectId)) {
					if(e.entityLiving instanceof EntityPlayer) {
						e.entityLiving.addPotionEffect(new PotionEffect(Potion.confusion.id, 400, 0));
					}
					if(e.entityLiving instanceof EntityLiving) {
						EntityLiving entityLiving = (EntityLiving)e.entityLiving;
						if(entityLiving.getHealth() <= 20) {
							entityLiving.tasks.taskEntries.clear();
							entityLiving.targetTasks.taskEntries.clear();
						}
						else if(entityLiving.getHealth() <= 80) {
							while(rand.nextInt(3) == 0 && !entityLiving.tasks.taskEntries.isEmpty()) {
								entityLiving.tasks.taskEntries.remove(rand.nextInt(entityLiving.tasks.taskEntries.size()));
							}
							while(rand.nextInt(3) == 0 && !entityLiving.targetTasks.taskEntries.isEmpty()) {
								entityLiving.targetTasks.taskEntries.remove(rand.nextInt(entityLiving.targetTasks.taskEntries.size()));
							}
						}
					}
				}
			}
			if(linkedhashmap.containsKey(EEMain.heavyTip.effectId) && !e.entityLiving.noClip) {
				e.entityLiving.addPotionEffect(new PotionEffect(EEMain.fastFall.getId(), ((Integer)(linkedhashmap.get(EEMain.heavyTip.effectId))).intValue()*100, ((Integer)(linkedhashmap.get(EEMain.heavyTip.effectId))).intValue()));
			}   
		}


		if(e.source.getEntity() instanceof EntityLivingBase && ((EntityLivingBase) e.source.getEntity()).getHeldItem() != null) {
			if(EnchantmentHelper.getEnchantmentLevel(EEMain.magicBlade.effectId, ((EntityLivingBase) e.source.getEntity()).getHeldItem()) > 0) {
				if(!e.source.isMagicDamage()) {
					e.entityLiving.attackEntityFrom(DamageSource.causeIndirectMagicDamage(e.entityLiving, e.source.getEntity()), EnchantmentHelper.getEnchantmentLevel(EEMain.magicBlade.effectId, ((EntityLivingBase) e.source.getEntity()).getHeldItem()) * 2);
				}
			}

			if(EnchantmentHelper.getEnchantmentLevel(EEMain.critChance.effectId, ((EntityLivingBase) e.source.getEntity()).getHeldItem()) > 0 && e.entityLiving.getRNG().nextInt(10) < EnchantmentHelper.getEnchantmentLevel(EEMain.critChance.effectId, ((EntityLivingBase) e.source.getEntity()).getHeldItem())) {
				if(!e.source.isMagicDamage()) {
					e.entityLiving.attackEntityFrom(DamageSource.causeIndirectMagicDamage(e.entityLiving, e.source.getEntity()), 16);
				}
			}

			if(EnchantmentHelper.getEnchantmentLevel(EEMain.lifeSteal.effectId, ((EntityLivingBase) e.source.getEntity()).getHeldItem()) > 0) {
				((EntityLivingBase)e.source.getEntity()).heal(e.ammount*EnchantmentHelper.getEnchantmentLevel(EEMain.lifeSteal.effectId, ((EntityLivingBase) e.source.getEntity()).getHeldItem())/16F);
			}

			if(EnchantmentHelper.getEnchantmentLevel(EEMain.attackSpeed.effectId, ((EntityLivingBase) e.source.getEntity()).getHeldItem()) > 0) {	
				e.entityLiving.hurtResistantTime -= 2;
				e.entityLiving.hurtResistantTime -= EnchantmentHelper.getEnchantmentLevel(EEMain.attackSpeed.effectId, ((EntityLivingBase) e.source.getEntity()).getHeldItem());
				e.entityLiving.motionY -= 0.2F;
				e.entityLiving.motionX *= 0.5F;
				e.entityLiving.motionZ *= 0.5F;
			}

			if(EnchantmentHelper.getEnchantmentLevel(EEMain.bossDamage.effectId, ((EntityLivingBase) e.source.getEntity()).getHeldItem()) > 0) {
				if(!e.source.isMagicDamage() && e.entityLiving instanceof IBossDisplayData) {
					e.entityLiving.attackEntityFrom(DamageSource.causeIndirectMagicDamage(e.entityLiving, e.source.getEntity()), 15 * EnchantmentHelper.getEnchantmentLevel(EEMain.bossDamage.effectId, ((EntityLivingBase) e.source.getEntity()).getHeldItem()));
				}
			}

			if(EnchantmentHelper.getEnchantmentLevel(EEMain.netherDamage.effectId, ((EntityLivingBase) e.source.getEntity()).getHeldItem()) > 0) {
				if(!e.source.isMagicDamage() && e.entityLiving.dimension == -1) {
					e.entityLiving.attackEntityFrom(DamageSource.causeIndirectMagicDamage(e.entityLiving, e.source.getEntity()), 4 * EnchantmentHelper.getEnchantmentLevel(EEMain.netherDamage.effectId, ((EntityLivingBase) e.source.getEntity()).getHeldItem()));
				}
			}

			if(!e.entityLiving.worldObj.isRemote && EnchantmentHelper.getEnchantmentLevel(EEMain.witherBlade.effectId, ((EntityLivingBase) e.source.getEntity()).getHeldItem()) > 0) {
				e.entityLiving.addPotionEffect(new PotionEffect(Potion.wither.id, EnchantmentHelper.getEnchantmentLevel(EEMain.witherBlade.effectId, ((EntityLivingBase) e.source.getEntity()).getHeldItem())*50, EnchantmentHelper.getEnchantmentLevel(EEMain.witherBlade.effectId, ((EntityLivingBase) e.source.getEntity()).getHeldItem())));
			}

			if(!e.entityLiving.worldObj.isRemote && EnchantmentHelper.getEnchantmentLevel(EEMain.poisonBlade.effectId, ((EntityLivingBase) e.source.getEntity()).getHeldItem()) > 0) {
				e.entityLiving.addPotionEffect(new PotionEffect(Potion.poison.id, EnchantmentHelper.getEnchantmentLevel(EEMain.poisonBlade.effectId, ((EntityLivingBase) e.source.getEntity()).getHeldItem())*50, EnchantmentHelper.getEnchantmentLevel(EEMain.poisonBlade.effectId, ((EntityLivingBase) e.source.getEntity()).getHeldItem())));
			}

			if(!e.entityLiving.worldObj.isRemote && EnchantmentHelper.getEnchantmentLevel(EEMain.crippleBlade.effectId, ((EntityLivingBase) e.source.getEntity()).getHeldItem()) > 0) {
				e.entityLiving.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, EnchantmentHelper.getEnchantmentLevel(EEMain.crippleBlade.effectId, ((EntityLivingBase) e.source.getEntity()).getHeldItem())*30, EnchantmentHelper.getEnchantmentLevel(EEMain.crippleBlade.effectId, ((EntityLivingBase) e.source.getEntity()).getHeldItem())));
				e.entityLiving.addPotionEffect(new PotionEffect(Potion.weakness.id, EnchantmentHelper.getEnchantmentLevel(EEMain.crippleBlade.effectId, ((EntityLivingBase) e.source.getEntity()).getHeldItem())*40, EnchantmentHelper.getEnchantmentLevel(EEMain.crippleBlade.effectId, ((EntityLivingBase) e.source.getEntity()).getHeldItem())));
				e.entityLiving.addPotionEffect(new PotionEffect(Potion.digSlowdown.id, EnchantmentHelper.getEnchantmentLevel(EEMain.crippleBlade.effectId, ((EntityLivingBase) e.source.getEntity()).getHeldItem())*60, EnchantmentHelper.getEnchantmentLevel(EEMain.crippleBlade.effectId, ((EntityLivingBase) e.source.getEntity()).getHeldItem())));
			}
		}
	}

	@SubscribeEvent
	public void onHurt(LivingHurtEvent e) {
		if(e.source == DamageSource.outOfWorld) return;

		int ignoreDamage = 0;
		for(int i=1; i <= 4; i++) {
			e.entityLiving.getEquipmentInSlot(i);
			ignoreDamage+=EnchantmentHelper.getEnchantmentLevel(EEMain.ignoreDamageChance.effectId, e.entityLiving.getEquipmentInSlot(i));
		}
		if(e.entityLiving.getRNG().nextInt(10) < ignoreDamage){
			e.setCanceled(true);
			return;
		}

		if(e.source == DamageSource.magic || e.source == DamageSource.wither) {
			int totalResist= 0;
			for(int i=1; i <= 4; i++) {
				totalResist += EnchantmentHelper.getEnchantmentLevel(EEMain.magicResist.effectId, e.entityLiving.getEquipmentInSlot(i));
			}
			if(totalResist > 0) {
				e.ammount /= totalResist/4;
			}
		}

		int damageCap = 20;
		for(int i=1; i <= 4; i++) {
			e.entityLiving.getEquipmentInSlot(i);
			damageCap-=EnchantmentHelper.getEnchantmentLevel(EEMain.damageCap.effectId, e.entityLiving.getEquipmentInSlot(i));
		}
		if(damageCap != 20 && e.ammount > damageCap){
			e.ammount = damageCap;
		}

		//Ignores Non-Entity Damage

		if(e.source.getEntity() == null) return;

		int totalFire = 0;
		for(int i=1; i <= 4; i++) {
			e.entityLiving.getEquipmentInSlot(i);
			totalFire+=EnchantmentHelper.getEnchantmentLevel(EEMain.firecloak.effectId, e.entityLiving.getEquipmentInSlot(i));
		}
		if(totalFire > 0){
			e.source.getEntity().setFire(totalFire);
		}
	}

	@SubscribeEvent
	public void onLivingUpdate(LivingUpdateEvent e) {	    	
		for(int i=1; i <= 4; i++) {
			if(EnchantmentHelper.getEnchantmentLevel(EEMain.healthRegen.effectId, e.entityLiving.getEquipmentInSlot(i)) > 0) {
				if(e.entityLiving instanceof EntityPlayer) {
					if(e.entityLiving.ticksExisted % 80 == 0 && ((EntityPlayer)e.entityLiving).getFoodStats().getFoodLevel() > 16 && ((EntityPlayer)e.entityLiving).shouldHeal()) {
						e.entityLiving.heal(0.2F * EnchantmentHelper.getEnchantmentLevel(EEMain.healthRegen.effectId, e.entityLiving.getEquipmentInSlot(i)));
					}
				}
				else if(e.entityLiving.ticksExisted % 80 == 0) {
					e.entityLiving.heal(0.1F);
				}			
			}
		}

		if(!e.entityLiving.worldObj.isRemote && !e.entityLiving.getActivePotionEffects().isEmpty()) {
			for(int i=1; i <= 4; i++) {
				if(EnchantmentHelper.getEnchantmentLevel(EEMain.debuffResist.effectId, e.entityLiving.getEquipmentInSlot(i)) > 0) {
					Collection copy = new ArrayList(e.entityLiving.getActivePotionEffects());
					for(Iterator iterator = copy.iterator(); iterator.hasNext();) {
						PotionEffect effect = (PotionEffect) iterator.next();
						Potion potion = Potion.potionTypes[effect.getPotionID()];
						if(potion.isBadEffect() && e.entityLiving.ticksExisted % 5 == 0) {
							PotionEffect effect1 = effect;
							e.entityLiving.getActivePotionEffects().remove(effect);
							effect = new PotionEffect(effect1.getPotionID(), effect1.getDuration()-10, effect1.getAmplifier());
							e.entityLiving.addPotionEffect(effect);
						}
					}
				}
			}
		}

		if(e.entityLiving instanceof EntityMob) {
			EntityMob monster = (EntityMob)e.entityLiving;
			Collection copy1 = new ArrayList(e.entityLiving.getActivePotionEffects());
			for(Iterator iterator = copy1.iterator(); iterator.hasNext();) {
				PotionEffect effect = (PotionEffect) iterator.next();
				if(effect.getPotionID() == Potion.blindness.id) {
					monster.setAttackTarget(null);
				}
			}	
		}

		if(!e.entityLiving.worldObj.isRemote && EnchantmentHelper.getEnchantmentLevel(EEMain.auraSlow.effectId, e.entityLiving.getEquipmentInSlot(3)) > 0) {
			int slowDistance = EnchantmentHelper.getEnchantmentLevel(EEMain.auraSlow.effectId, e.entityLiving.getEquipmentInSlot(3));
			AxisAlignedBB axisalignedbb = AxisAlignedBB.fromBounds(e.entityLiving.posX, e.entityLiving.posY, e.entityLiving.posZ, e.entityLiving.posX + 1, e.entityLiving.posY + 1, e.entityLiving.posZ + 1).expand(4*slowDistance, 4*slowDistance, 4*slowDistance);
			List list = e.entityLiving.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, axisalignedbb);
			Iterator iterator = list.iterator();
			EntityLivingBase entityLiving;

			while (iterator.hasNext())
			{
				entityLiving = (EntityLivingBase)iterator.next();
				if(entityLiving instanceof EntityPlayer && entityLiving.getCombatTracker().func_180135_h() != e.entityLiving) continue;
				entityLiving.motionX *= Math.pow(0.8D, EnchantmentHelper.getEnchantmentLevel(EEMain.auraSlow.effectId, e.entityLiving.getEquipmentInSlot(3)));
				entityLiving.motionZ *= Math.pow(0.8D, EnchantmentHelper.getEnchantmentLevel(EEMain.auraSlow.effectId, e.entityLiving.getEquipmentInSlot(3)));
			}
		}

		if(!e.entityLiving.worldObj.isRemote && EnchantmentHelper.getEnchantmentLevel(EEMain.waterRegen.effectId, e.entityLiving.getEquipmentInSlot(3)) > 0 && e.entityLiving.isWet() && !e.entityLiving.isPotionActive(Potion.regeneration.id)) e.entityLiving.addPotionEffect(new PotionEffect(Potion.regeneration.id, 220, EnchantmentHelper.getEnchantmentLevel(EEMain.waterRegen.effectId, e.entityLiving.getEquipmentInSlot(3))-1, true, true));
		if(!e.entityLiving.worldObj.isRemote && EnchantmentHelper.getEnchantmentLevel(EEMain.fireResist.effectId, e.entityLiving.getEquipmentInSlot(3)) > 0 && !e.entityLiving.isBurning()) e.entityLiving.addPotionEffect(new PotionEffect(Potion.fireResistance.id, 120 * EnchantmentHelper.getEnchantmentLevel(EEMain.fireResist.effectId, e.entityLiving.getEquipmentInSlot(3))-1, 1, true, false));

		if(!e.entityLiving.worldObj.isRemote && EnchantmentHelper.getEnchantmentLevel(EEMain.nightStats.effectId, e.entityLiving.getEquipmentInSlot(3)) > 0 && e.entityLiving.worldObj.getWorldTime() > 17000 && e.entityLiving.worldObj.getWorldTime() < 19000 && !e.entityLiving.worldObj.isRaining()) {
			e.entityLiving.addPotionEffect(new PotionEffect(Potion.damageBoost.id, 10, EnchantmentHelper.getEnchantmentLevel(EEMain.nightStats.effectId, e.entityLiving.getEquipmentInSlot(3))-1, true, false));
			e.entityLiving.addPotionEffect(new PotionEffect(Potion.digSpeed.id, 10, EnchantmentHelper.getEnchantmentLevel(EEMain.nightStats.effectId, e.entityLiving.getEquipmentInSlot(3))-1, true, false));
			e.entityLiving.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 10, 0, true, false));
			e.entityLiving.addPotionEffect(new PotionEffect(Potion.resistance.id, 10, 0, true, false));
		} else if(!e.entityLiving.worldObj.isRemote && EnchantmentHelper.getEnchantmentLevel(EEMain.dayStats.effectId, e.entityLiving.getEquipmentInSlot(3)) > 0 && e.entityLiving.worldObj.getWorldTime() > 5000 && e.entityLiving.worldObj.getWorldTime() < 7000 && !e.entityLiving.worldObj.isRaining()) {
			e.entityLiving.addPotionEffect(new PotionEffect(Potion.damageBoost.id, 10, EnchantmentHelper.getEnchantmentLevel(EEMain.dayStats.effectId, e.entityLiving.getEquipmentInSlot(3))-1, true, false));
			e.entityLiving.addPotionEffect(new PotionEffect(Potion.digSpeed.id, 10, EnchantmentHelper.getEnchantmentLevel(EEMain.dayStats.effectId, e.entityLiving.getEquipmentInSlot(3))-1, true, false));
			e.entityLiving.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 10, 0, true, false));
			e.entityLiving.addPotionEffect(new PotionEffect(Potion.resistance.id, 10, 0, true, false));
		}

		//Disabled while sneaking
		if(!e.entityLiving.isSneaking()) {


			if(EnchantmentHelper.getEnchantmentLevel(EEMain.speed.effectId, e.entityLiving.getEquipmentInSlot(1)) > 0) {
				if(e.entityLiving.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.movementSpeed).getModifier(speedMod.getID()) == null) {
					speedMod = new AttributeModifier(this.SPEEDMODID, "speedMod", this.SPEEDMODAMOUNT*EnchantmentHelper.getEnchantmentLevel(EEMain.speed.effectId, e.entityLiving.getEquipmentInSlot(1)) , 2);
					e.entityLiving.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.movementSpeed).applyModifier(speedMod);
				}
			} else {
				e.entityLiving.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.movementSpeed).removeModifier(speedMod);
			}

			if(!e.entityLiving.worldObj.isRemote && EnchantmentHelper.getEnchantmentLevel(EEMain.nightVision.effectId, e.entityLiving.getEquipmentInSlot(4)) > 0 && e.entityLiving.dimension == 0) {
				e.entityLiving.addPotionEffect(new PotionEffect(Potion.nightVision.id, 10, 0, true, true));
			}

			if(EnchantmentHelper.getEnchantmentLevel(EEMain.waterWalking.effectId, e.entityLiving.getEquipmentInSlot(1)) > 0) {
				int x = (int) Math.floor(e.entityLiving.posX);
				int y = (int) (e.entityLiving.posY - e.entityLiving.getYOffset());
				int z = (int) Math.floor(e.entityLiving.posZ);
				if(e.entityLiving.motionY < 0 && e.entityLiving.worldObj.getBlockState(new BlockPos(e.entityLiving.posX, e.entityLiving.posY-1, e.entityLiving.posZ)) == Blocks.water && e.entityLiving.motionY < 0 && e.entityLiving.worldObj.getBlockState(new BlockPos(e.entityLiving.posX, e.entityLiving.posY, e.entityLiving.posZ)) == Blocks.air) {
					e.entityLiving.onGround = true;
					e.entityLiving.motionY = 0;
					Random rand = new Random();
					if(rand.nextInt(120) == 0) e.entityLiving.worldObj.playSoundAtEntity(e.entityLiving, "game.player.swim.splash", 2, 1);
					if(rand.nextInt(5) == 0) {
						for(int i=0; i<=6; i++) {
							e.entityLiving.worldObj.spawnParticle(EnumParticleTypes.WATER_SPLASH, e.entityLiving.posX, e.entityLiving.posY - e.entityLiving.getYOffset(), e.entityLiving.posZ, rand.nextGaussian(), rand.nextGaussian(), rand.nextGaussian());
						}
					}
				}
			}
		} else {
			e.entityLiving.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.movementSpeed).removeModifier(speedMod);
		}

		//POTIONS

		if(e.entityLiving.isPotionActive(EEMain.fastFall)) e.entityLiving.motionY -= (1 + e.entityLiving.getActivePotionEffect(EEMain.fastFall).getAmplifier())*0.06D;
		if(e.entityLiving.isPotionActive(EEMain.sticky)) {
			e.entityLiving.fallDistance = 0;
			if(e.entityLiving.isCollidedHorizontally) e.entityLiving.motionY = 0.3D;
		}
	}
}
