package de.badbathbears.privacy.lock;

import java.util.Random;

import net.minecraft.src.CreativeTabs;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.MathHelper;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.World;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;
import de.badbathbears.privacy.core.PrivacyCraft;

//TODO dafür sorgen dass nicht verschiebbar (auch nicht durch redpower!)
public class BlockKeyLock extends BlockLock {

	public BlockKeyLock(int par1, int par2, Material par3Material) {
		super(par1, par2, par3Material);
		this.setCreativeTab(CreativeTabs.tabRedstone);
		this.setBlockName("PrivacyCraftBlockKeyLock");
		unlockedTexture = 3;
		lockedTexture = 2;
	}

	@Override
	public boolean onBlockActivated(World world, int par2, int par3, int par4, EntityPlayer player, int par6, float par7, float par8, float par9) {
		if(player.isSneaking()) {
			return false;
		}
		ItemStack currentEquippedItem = player.getCurrentEquippedItem();
		TileEntityLock tile = (TileEntityLock) getTile(world, par2, par3, par4);
		if (currentEquippedItem != null && currentEquippedItem.getItem() instanceof ItemKey) {
			if (matches(currentEquippedItem, tile)) {
				toggleLocked(world, par2, par3, par4, player);
			}
			return true;
		}
		return false;
	}

	@Override
	public String getTextureFile() {
		return PrivacyCraft.textureFile;
	}

	@SideOnly(Side.CLIENT)
	/**
	 * Retrieves the block texture to use based on the display side. Args: iBlockAccess, x, y, z, side
	 */
	public int getBlockTexture(IBlockAccess par1IBlockAccess, int x, int y, int z, int side) {
		int meta = par1IBlockAccess.getBlockMetadata(x, y, z);
		
		if((meta & 0x7) == 0)
			meta = 2;
		
		if(isLocked(meta)) {
			meta=meta-8;
		}
		return side != meta ? 1 : (this.isLocked(par1IBlockAccess, x, y, z) ? 2 : 3);
	}
	
	@Override
	public int getBlockTextureFromSide(int side) {
		return side == 4 ? 2 : 1;
	}

	private boolean isLocked(IBlockAccess world, int x, int y, int z) {
		int blockMetadata = world.getBlockMetadata(x, y, z);
		return isLocked(blockMetadata);
	}

	public static boolean isLocked(int blockMetadata) {
		return (blockMetadata & 8) == 8;
	}

	private boolean matches(ItemStack keyStack, TileEntityLock tile) {
		NBTTagCompound tagCompound = keyStack.getTagCompound();
		String itemKey = tagCompound.getString(TileEntityLock.KEY_CODE);
		if(tile != null && itemKey != null && itemKey.equals(tile.getKeyCode())) {
			return true;
		}
		return false;
	}

	public int idDropped(int par1, Random par2Random, int par3) {
		return PrivacyCraft.stoneKeyLockItem.shiftedIndex;
	}

	@Override
	public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLiving par5EntityLiving) {
		super.onBlockPlacedBy(par1World, par2, par3, par4, par5EntityLiving);
		int var6 = MathHelper.floor_double((double) (par5EntityLiving.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
		if (var6 == 0) {
			par1World.setBlockMetadataWithNotify(par2, par3, par4, 2);
		} else if (var6 == 1) {
			par1World.setBlockMetadataWithNotify(par2, par3, par4, 5);
		} else if (var6 == 2) {
			par1World.setBlockMetadataWithNotify(par2, par3, par4, 3);
		} else if (var6 == 3) {
			par1World.setBlockMetadataWithNotify(par2, par3, par4, 4);
		}
	}
}
