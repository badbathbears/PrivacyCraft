package de.badbathbears.privacy.block;

import java.util.Random;

import net.minecraft.src.BlockDoor;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Material;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import de.badbathbears.privacy.core.PrivacyCraft;

//FIXME unterer teil der tür reagiert doppelt (geht auf und direkt wieder zu)
//FIXME onActivate wird immer zweimal aufgerufen (vermutlich client und server) "offene" tür wird jeweils einmal als verschlossen und einmal als offen angesehen
//FIXME es wird keine tür gedropt, wenn ich die tür zerstöre
//TODO quickcheck einbauen (hat der alle schlüssel bei sich)

public class BlockBlockableDoor extends BlockDoor {
	public BlockBlockableDoor(int par1, Material par3Material) {
		super(par1, par3Material);
		this.setRequiresSelfNotify();
		this.setBlockName("PrivacyCraftBlockBlockableDoor");
	}

	@Override
	public boolean onBlockActivated(World par1World, int par2, int par3,
			int par4, EntityPlayer player, int par6, float par7, float par8,
			float par9) {
		// check for surrounding locks
		if (isDoorLocked(par1World, par2, par3, par4)) {
			return false;
		}

		return super.onBlockActivated(par1World, par2, par3, par4, player,
				par6, par7, par8, par9);
	}

	@Override
	public void onPoweredBlockChange(World par1World, int par2, int par3,
			int par4, boolean par5) {
		if (isDoorLocked(par1World, par2, par3, par4)) {
			return;
		}
		super.onPoweredBlockChange(par1World, par2, par3, par4, par5);
	}

	private boolean isDoorLocked(World par1World, int par2, int par3, int par4) {
		boolean locked = false;
		int y = par3;
		int var5 = par1World.getBlockMetadata(par2, par3, par4);
		if ((var5 & 8) != 0) {
			y = par3 - 1;
		}

		// check surrounding blocks
		/*
		 * Frontview: 
		 *  x 
		 * x#x 
		 * x#x 
		 *  x 
		 * Top: 
		 *  x 
		 * x#x
		 *  x
		 */
		// lower neighbours
		locked = checkLocked(par1World, par2, y, par4, locked);
		locked = checkLocked(par1World, par2 - 1, y, par4, locked);
		locked = checkLocked(par1World, par2, y, par4 + 1, locked);
		locked = checkLocked(par1World, par2, y, par4 - 1, locked);
		// upper neighbours
		locked = checkLocked(par1World, par2 + 1, y + 1, par4, locked);
		locked = checkLocked(par1World, par2 - 1, y + 1, par4, locked);
		locked = checkLocked(par1World, par2, y + 1, par4 + 1, locked);
		locked = checkLocked(par1World, par2, y, par4 - 1, locked);
		// upper center
		locked = checkLocked(par1World, par2, y + 2, par4, locked);
		// lower center
		locked = checkLocked(par1World, par2, y - 1, par4, locked);
		return locked;
	}

	private boolean checkLocked(World par1World, int par2, int y, int par4, boolean locked) {
		if (!locked)
			locked |= isBlockLockedLock(par1World, par2, y, par4);
		return locked;
	}

	private boolean isBlockLockedLock(World par1World, int par2, int par3,
			int par4) {
		TileEntity blockTileEntity = par1World.getBlockTileEntity(par2, par3, par4);
		return blockTileEntity != null && blockTileEntity instanceof TileEntityLock && ((TileEntityLock) blockTileEntity).isLocked();
	}

	@Override
	public int idDropped(int par1, Random par2Random, int par3) {
		return (par1 & 8) != 0 ? 0
				: (this.blockMaterial == Material.iron ? PrivacyCraft.blockableIronDoorItem.shiftedIndex
						: PrivacyCraft.blockableWoodDoorItem.shiftedIndex);
	}
}
