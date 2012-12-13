package de.badbathbears.privacy.block;

import java.util.Random;

import net.minecraft.src.BlockContainer;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import de.badbathbears.privacy.core.PrivacyCraft;
import de.badbathbears.privacy.item.ItemKey;

//TODO keyCode einfügen
//TODO dafür sorgen dass nicht verschiebbar (auch nicht durch redpower!)
public class BlockKeyLock extends BlockContainer {

	public BlockKeyLock(int par1, int par2, Material par3Material) {
		super(par1, par2, par3Material);
		this.setTextureFile("/PrivacyCraftGFX/Blocks.png");
		this.setCreativeTab(CreativeTabs.tabRedstone);
		this.setBlockName("PrivacyCraftBlockKeyLock");
	}

	@Override
	public TileEntity createNewTileEntity(World var1) {
		return new TileEntityLock();
	}

	@Override
	public boolean onBlockActivated(World par1World, int par2, int par3,
			int par4, EntityPlayer player, int par6, float par7, float par8,
			float par9) {
		ItemStack currentEquippedItem = player.getCurrentEquippedItem();
		TileEntityLock tile = (TileEntityLock) getTile(par1World, par2, par3,
				par4);
		if (currentEquippedItem != null
				&& currentEquippedItem.getItem() instanceof ItemKey) {
			if (matches(currentEquippedItem, tile)) {
				this.toggleLocked(tile);
			}
		}
		return false;
	}
	
	private boolean matches(ItemStack keyStack, TileEntityLock tile) {
		// keyStack.getTagCompound()
		return true;
	}

	private TileEntity getTile(World world, int par2, int par3, int par4) {
		return world.getBlockTileEntity(par2, par3, par4);
	}

	private void toggleLocked(TileEntityLock tile) {
		if (tile.isLocked()) {
			tile.setLocked(false);
		} else {
			tile.setLocked(true);
		}
	}

	public int idDropped(int par1, Random par2Random, int par3) {
		return PrivacyCraft.stoneKeyLockItem.shiftedIndex;
	}
}
