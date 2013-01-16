package de.badbathbears.privacy.lock;

import java.util.Random;

import net.minecraft.src.CreativeTabs;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.MathHelper;
import net.minecraft.src.World;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;
import de.badbathbears.privacy.core.PrivacyCraft;
import de.badbathbears.privacy.gui.GuiCodeLock;

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
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9) {
		if(player.isSneaking()) {
			return false;
		}
		ItemStack currentEquippedItem = player.getCurrentEquippedItem();
		TileEntityLock tile = (TileEntityLock) getTile(world, x, y, z);
		if (currentEquippedItem != null && currentEquippedItem.getItem() instanceof ItemKey) {
			KeyTile keyTile = new KeyTile(currentEquippedItem);
			System.out.println("keylock	key");
			if(keyTile.isSet()){
				System.out.println("keylock	set");
				if (matches(keyTile, tile)) {
					System.out.println("keylock	toggle");
					toggleLocked(world, x, y, z, player);
				}
			} else {
				System.out.println("keylock	not set");
				if(world.isRemote){
					System.out.println("keylock	remote -> open ui");
					player.openGui(PrivacyCraft.instance, GuiCodeLock.ID_KEY, world, x, y, z);
				}
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

	private boolean isLocked(IBlockAccess world, int x, int y, int z) {
		int blockMetadata = world.getBlockMetadata(x, y, z);
		return isLocked(blockMetadata);
	}

	public static boolean isLocked(int blockMetadata) {
		return (blockMetadata & 8) == 8;
	}

	private boolean matches(KeyTile keyStack, TileEntityLock tile) {
		String keyCode = tile.getKeyCode();
		String keyCode2 = keyStack.getKeyCode();
		System.out.println("keylock.matches" + keyCode + " vs " + keyCode2);
		return keyCode2.equals(keyCode);
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
