package de.badbathbears.privacy.block;

import net.minecraft.src.BlockContainer;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.Material;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;

public class BlockSaveStructure extends BlockContainer {

	public BlockSaveStructure(int par1, int par2, Material par3Material) {
		super(par1, par2, par3Material);
		this.setCreativeTab(CreativeTabs.tabBlock);
		this.setBlockName("PrivacyCraftBlockSaveStructure");
	}

	@Override
	public TileEntity createNewTileEntity(World var1) {
		return null;
	}
	
	@Override
	public void onBlockAdded(World par1World, int par2, int par3, int par4) {
		super.onBlockAdded(par1World, par2, par3, par4);
	}

}
