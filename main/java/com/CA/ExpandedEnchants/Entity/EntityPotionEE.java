package com.CA.ExpandedEnchants.Entity;

import java.util.Iterator;
import java.util.List;

import com.CA.ExpandedEnchants.EEMain;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.init.Items;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityPotionEE extends EntityPotion {
	
	private ItemStack potionDamage;
	
    public EntityPotionEE(World worldIn) {
        super(worldIn);
    }

    public EntityPotionEE(World worldIn, EntityLivingBase p_i1789_2_, int p_i1789_3_) {
        this(worldIn, p_i1789_2_, new ItemStack(Items.potionitem, 1, p_i1789_3_));
    }

    public EntityPotionEE(World worldIn, EntityLivingBase p_i1790_2_, ItemStack p_i1790_3_) {
        super(worldIn, p_i1790_2_, p_i1790_3_);
        this.potionDamage = p_i1790_3_;
    }

    @SideOnly(Side.CLIENT)
    public EntityPotionEE(World worldIn, double p_i1791_2_, double p_i1791_4_, double p_i1791_6_, int p_i1791_8_) {
        this(worldIn, p_i1791_2_, p_i1791_4_, p_i1791_6_, new ItemStack(Items.potionitem, 1, p_i1791_8_));
    }

    public EntityPotionEE(World worldIn, double p_i1792_2_, double p_i1792_4_, double p_i1792_6_, ItemStack p_i1792_8_) {
        super(worldIn, p_i1792_2_, p_i1792_4_, p_i1792_6_, p_i1792_8_);
        this.potionDamage = p_i1792_8_;
    }

    @Override
    protected void onImpact(MovingObjectPosition p_70184_1_) {
    	if (!this.worldObj.isRemote)
        {
            List list = ((ItemPotion) EEMain.potion).getEffects(this.potionDamage);

            if (list != null && !list.isEmpty())
            {
                AxisAlignedBB axisalignedbb = this.getEntityBoundingBox().expand(4.0D, 2.0D, 4.0D);
                List list1 = this.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, axisalignedbb);

                if (!list1.isEmpty())
                {
                    Iterator iterator = list1.iterator();

                    while (iterator.hasNext())
                    {
                        EntityLivingBase entitylivingbase = (EntityLivingBase)iterator.next();
                        double d0 = this.getDistanceSqToEntity(entitylivingbase);

                        if (d0 < 16.0D)
                        {
                            double d1 = 1.0D - Math.sqrt(d0) / 4.0D;

                            if (entitylivingbase == p_70184_1_.entityHit)
                            {
                                d1 = 1.0D;
                            }

                            Iterator iterator1 = list.iterator();

                            while (iterator1.hasNext())
                            {
                                PotionEffect potioneffect = (PotionEffect)iterator1.next();
                                int i = potioneffect.getPotionID();

                                if (Potion.potionTypes[i].isInstant())
                                {
                                    Potion.potionTypes[i].affectEntity(this, this.getThrower(), entitylivingbase, potioneffect.getAmplifier(), d1);
                                }
                                else
                                {
                                    int j = (int)(d1 * (double)potioneffect.getDuration() + 0.5D);

                                    if (j > 20)
                                    {
                                        entitylivingbase.addPotionEffect(new PotionEffect(i, j, potioneffect.getAmplifier()));
                                    }
                                }
                            }
                        }
                    }
                }
            }

            this.worldObj.playAuxSFX(2002, new BlockPos(this), this.getPotionDamage());
            this.setDead();
        }
    }
}
