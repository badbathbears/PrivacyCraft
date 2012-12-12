package de.badbathbears.privacy.block;

import java.util.ArrayList;

import de.badbathbears.privacy.core.PrivacyCraft;
import de.badbathbears.privacy.item.ItemKey;
import net.minecraft.src.BlockContainer;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;

//TODO keyCode einfügen
//FIXME block wird auch gesetzt, wenn ich eine tür bediene
//TODO dafür sorgen dass nicht verschiebbar auch nicht durch redpower!
public class BlockKeyLock extends BlockContainer {

	public BlockKeyLock(int par1, int par2, Material par3Material) {
		super(par1, par2, par3Material);
	}

	@Override
	public TileEntity createNewTileEntity(World var1) {
		return new TileEntityLock();
	}
	
	@Override
	public boolean onBlockActivated(World par1World, int par2, int par3,
			int par4, EntityPlayer player, int par6, float par7,
			float par8, float par9) {
		ItemStack currentEquippedItem = player.getCurrentEquippedItem();
		TileEntityLock tile = (TileEntityLock) getTile(
				par1World, par2, par3, par4);
		if (currentEquippedItem != null
				&& currentEquippedItem.getItem() instanceof ItemKey) {
			if (matches(currentEquippedItem, tile)) {
				this.toggleLocked(tile);
			}
		}
		return false;
	}
	
	private boolean matches(ItemStack keyStack, TileEntityLock tile) {
		//keyStack.getTagCompound()
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
	
	@Override
	public ArrayList<ItemStack> getBlockDropped(World world, int x, int y,
			int z, int metadata, int fortune) {
		ArrayList<ItemStack> list = new ArrayList<ItemStack>();
		list.add(new ItemStack(PrivacyCraft.stoneKeyLockItem,1));
		return list;
	}
}
