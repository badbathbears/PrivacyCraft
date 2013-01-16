package de.badbathbears.privacy.lock;

import net.minecraft.src.Block;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemBlock;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;
import de.badbathbears.privacy.core.PrivacyCraft;

public class ItemKeyLock extends ItemBlock {

	public ItemKeyLock(int par1) {
		super(par1);
		this.setTextureFile(PrivacyCraft.textureFile);
		this.setCreativeTab(CreativeTabs.tabRedstone);
		this.setItemName("PrivacyCraftItemKeyLock");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getIconFromDamage(int par1) {
		return 3;
	}
	
	@Override
	public boolean onItemUse(ItemStack par1ItemStack,
			EntityPlayer par2EntityPlayer, World par3World, int par4, int par5,
			int par6, int par7, float par8, float par9, float par10) {
		int var11 = par3World.getBlockId(par4, par5, par6);
		Block block = PrivacyCraft.stoneKeyLockBlock;

		if (var11 == Block.snow.blockID) {
			par7 = 1;
		} else if (var11 != Block.vine.blockID
				&& var11 != Block.tallGrass.blockID
				&& var11 != Block.deadBush.blockID
				&& (Block.blocksList[var11] == null || !Block.blocksList[var11]
						.isBlockReplaceable(par3World, par4, par5, par6))) {
			if (par7 == 0) {
				--par5;
			}

			if (par7 == 1) {
				++par5;
			}

			if (par7 == 2) {
				--par6;
			}

			if (par7 == 3) {
				++par6;
			}

			if (par7 == 4) {
				--par4;
			}

			if (par7 == 5) {
				++par4;
			}
		}

		if (par1ItemStack.stackSize == 0) {
			return false;
		} else if (!par2EntityPlayer.canPlayerEdit(par4, par5, par6, par7,
				par1ItemStack)) {
			return false;
		} else if (par5 == 255
				&& Block.blocksList[block.blockID].blockMaterial.isSolid()) {
			return false;
		} else if (par3World.canPlaceEntityOnSide(block.blockID, par4, par5,
				par6, false, par7, par2EntityPlayer)) {
			int var13 = this.getMetadata(par1ItemStack.getItemDamage());
			int var14 = block.func_85104_a(par3World, par4, par5, par6, par7,
					par8, par9, par10, var13);

			if (placeBlockAt(par1ItemStack, par2EntityPlayer, par3World, par4,
					par5, par6, par7, par8, par9, par10, var14,block)) {
				par3World.playSoundEffect((double) ((float) par4 + 0.5F),
						(double) ((float) par5 + 0.5F),
						(double) ((float) par6 + 0.5F),
						block.stepSound.getPlaceSound(),
						(block.stepSound.getVolume() + 1.0F) / 2.0F,
						block.stepSound.getPitch() * 0.8F);
				--par1ItemStack.stackSize;
			}

			return true;
		} else {
			return false;
		}
	}

	public boolean placeBlockAt(ItemStack stack, EntityPlayer player,
			World world, int x, int y, int z, int side, float hitX, float hitY,
			float hitZ, int metadata, Block block) {
		if (!world.setBlockAndMetadataWithNotify(x, y, z, block.blockID,
				metadata)) {
			return false;
		}

		if (world.getBlockId(x, y, z) == block.blockID) {
			block.onBlockPlacedBy(world, x, y, z,
					player);
			block.func_85105_g(world, x, y, z,
					metadata);
		}

		return true;
	}
}
