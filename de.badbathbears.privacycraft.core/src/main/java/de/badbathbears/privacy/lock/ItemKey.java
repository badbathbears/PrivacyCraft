package de.badbathbears.privacy.lock;

import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;
import de.badbathbears.privacy.core.PrivacyCraft;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.Entity;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.World;

public class ItemKey extends Item {

	public ItemKey(int par1) {
		super(par1);
		this.setTextureFile(PrivacyCraft.textureFile);
		this.setItemName("PrivacyCraftItemKey");
		this.setCreativeTab(CreativeTabs.tabTools);
	}

	@SideOnly(Side.CLIENT)
	public int getItemIconFromDamage(int i) {
		return 6;
	}

	@Override
	public int getItemStackLimit() {
		return 0;
	}

	@Override
	public void onUpdate(ItemStack itemstack, World par2World, Entity par3Entity, int par4, boolean par5) {
		super.onUpdate(itemstack, par2World, par3Entity, par4, par5);
		if (itemstack.stackTagCompound == null) {
			itemstack.setTagCompound(new NBTTagCompound());
		}
		itemstack.stackTagCompound.setString(TileEntityLock.KEY_CODE, "");
	}
}
