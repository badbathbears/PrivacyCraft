package de.badbathbears.privacy.block;

import java.util.Random;

import net.minecraft.src.CreativeTabs;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.World;
import de.badbathbears.privacy.core.PrivacyCraft;
import de.badbathbears.privacy.item.ItemKey;

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

	private boolean matches(ItemStack keyStack, TileEntityLock tile) {
		// keyStack.getTagCompound()
		return true;
	}

	public int idDropped(int par1, Random par2Random, int par3) {
		return PrivacyCraft.stoneKeyLockItem.shiftedIndex;
	}

}
