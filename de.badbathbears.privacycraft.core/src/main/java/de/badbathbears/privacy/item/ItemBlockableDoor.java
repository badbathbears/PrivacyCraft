package de.badbathbears.privacy.item;

import net.minecraft.src.Block;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemDoor;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.MathHelper;
import net.minecraft.src.World;
import de.badbathbears.privacy.core.PrivacyCraft;

public class ItemBlockableDoor extends ItemDoor {

	private Material doorMaterial;

	public ItemBlockableDoor(int par1, Material par2Material) {
		super(par1, par2Material);
		this.doorMaterial = par2Material;
		this.setTextureFile(PrivacyCraft.textureFile);
		if (par2Material == Material.wood) {
			this.setItemName("PrivacyCraftItemBlockableWoodDoor");
		} else if (par2Material == Material.iron) {
			this.setItemName("PrivacyCraftItemBlockableIronDoor");
		}
	}

	public boolean onItemUse(ItemStack par1ItemStack,
			EntityPlayer par2EntityPlayer, World par3World, int par4, int par5,
			int par6, int par7, float par8, float par9, float par10) {
		if (par7 != 1) {
			return false;
		} else {
			++par5;
			Block var11;

			if (this.doorMaterial == Material.wood) {
				var11 = PrivacyCraft.blockableWoodDoorBlock;
			} else if (this.doorMaterial == Material.iron) {
				var11 = PrivacyCraft.blockableIronDoorBlock;
			} else {
				// TODO insert new doors. e.g. Glass
				return false;
			}

			if (par2EntityPlayer.canPlayerEdit(par4, par5, par6, par7,
					par1ItemStack)
					&& par2EntityPlayer.canPlayerEdit(par4, par5 + 1, par6,
							par7, par1ItemStack)) {
				if (!var11.canPlaceBlockAt(par3World, par4, par5, par6)) {
					return false;
				} else {
					int var12 = MathHelper
							.floor_double((double) ((par2EntityPlayer.rotationYaw + 180.0F) * 4.0F / 360.0F) - 0.5D) & 3;
					placeDoorBlock(par3World, par4, par5, par6, var12, var11);
					--par1ItemStack.stackSize;
					return true;
				}
			} else {
				return false;
			}
		}
	}
}
