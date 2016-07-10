package com.CA.ExpandedEnchants.Block;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.CA.ExpandedEnchants.EEMain;

public class BlockFluidXP extends BlockFluidClassic {

	public BlockFluidXP(Fluid fluid, Material material) {
		super(fluid, material);
	}
	
/*	@Override
	public int getRenderType() {
		return 1;
	}*/

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, BlockPos pos, IBlockState state, Random rand) {
		if(rand.nextInt(4) == 0) {
			world.spawnParticle(EnumParticleTypes.ENCHANTMENT_TABLE, pos.getX() + 0.5D + (rand.nextGaussian() / 4), pos.getY() + 2 + rand.nextDouble() / 2, pos.getZ() + 0.5D + (rand.nextGaussian() / 4), 0, 0D, 0);
		}
		super.randomDisplayTick(world, pos, state, rand);
	}

	@Override
	public void onEntityCollidedWithBlock(World world, BlockPos pos, Entity entity) {
		if(!(entity instanceof EntityItem)) { 
			entity.attackEntityFrom(EEMain.xpDamage, 7);
		}
		else {
			EntityItem itemEntity = (EntityItem)entity;
			if(itemEntity.getEntityItem().isItemEnchanted()) {
				Map map = new HashMap();
				EnchantmentHelper.setEnchantments(map, itemEntity.getEntityItem());
			}
		}
	}
}
