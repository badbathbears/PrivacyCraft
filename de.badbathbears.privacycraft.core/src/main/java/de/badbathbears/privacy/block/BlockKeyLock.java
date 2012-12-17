package de.badbathbears.privacy.block;

import java.util.Random;

import net.minecraft.src.BlockContainer;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.MathHelper;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;
import de.badbathbears.privacy.core.PrivacyCraft;
import de.badbathbears.privacy.item.ItemKey;

//TODO keyCode einfügen
//TODO dafür sorgen dass nicht verschiebbar (auch nicht durch redpower!)
public class BlockKeyLock extends BlockContainer {

	public BlockKeyLock(int par1, int par2, Material par3Material) {
		super(par1, par2, par3Material);
		this.setCreativeTab(CreativeTabs.tabRedstone);
		this.setBlockName("PrivacyCraftBlockKeyLock");
	}

	@Override
	public TileEntity createNewTileEntity(World var1) {
		return new TileEntityLock();
	}

	@Override
	public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer player, int par6, float par7, float par8, float par9) {
		ItemStack currentEquippedItem = player.getCurrentEquippedItem();
		TileEntityLock tile = (TileEntityLock) getTile(par1World, par2, par3, par4);
		if (currentEquippedItem != null && currentEquippedItem.getItem() instanceof ItemKey) {
			if (matches(currentEquippedItem, tile)) {
				this.toggleLocked(par1World, par2, par3, par4);
			}
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
		// keyStack.getTagCompound()
		return true;
	}

	private TileEntity getTile(World world, int par2, int par3, int par4) {
		return world.getBlockTileEntity(par2, par3, par4);
	}

	private void toggleLocked(World par1World, int par2, int par3, int par4) {
		int blockMetadata = par1World.getBlockMetadata(par2, par3, par4);
		if(isLocked(blockMetadata)) {
			// xxx1 -> xxx0
			par1World.setBlockMetadataWithNotify(par2, par3, par4, (blockMetadata & 7));
		} else {
			// xxx0 -> xxx1
			par1World.setBlockMetadataWithNotify(par2, par3, par4, (blockMetadata | 8));
		}
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
