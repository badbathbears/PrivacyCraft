package de.badbathbears.privacy.lock;

import java.util.Random;

import net.minecraft.src.CreativeTabs;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Material;
import net.minecraft.src.World;
import de.badbathbears.privacy.core.PrivacyCraft;
import de.badbathbears.privacy.gui.GuiCodeLock;

public class BlockCodeLock extends BlockLock {

	public BlockCodeLock(int par1, int par2, Material par3Material) {
		super(par1, par2, par3Material);
		this.setCreativeTab(CreativeTabs.tabRedstone);
		this.setBlockName("PrivacyCraftBlockCodeLock");
		unlockedTexture = 5;
		lockedTexture = 4;
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9) {
		if(player.isSneaking()) {
			return false;
		}
		int blockMetadata = world.getBlockMetadata(x, y, z);
		//setzen => code setzen => offen => schliessen => zu => öffnen => offen => schliessen  
		//client hat keinen code => will setzen
		//server schliesst? 
		if (!isLocked(blockMetadata)) {
			lock(world, x, y, z, blockMetadata, player);
		} else {
			if(world.isRemote){
				player.openGui(PrivacyCraft.instance, GuiCodeLock.ID_CODE, world, x, y, z);
			}
		}
		return true;
	}

	public int idDropped(int par1, Random par2Random, int par3) {
		return PrivacyCraft.stoneCodeLockItem.shiftedIndex;
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLiving par5EntityLiving) {
		super.onBlockPlacedBy(world, x, y, z, par5EntityLiving);
		if (!world.isRemote) {
			((EntityPlayer) par5EntityLiving).openGui(PrivacyCraft.instance, GuiCodeLock.ID_CODE, world, x, y, z);
		}
	}
}
