package de.badbathbears.privacy.lock;

import net.minecraft.src.CreativeTabs;
import net.minecraft.src.Entity;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.World;
import de.badbathbears.privacy.core.PrivacyCraft;

public class ItemKey extends Item {

	private int textureIndex = 6;

	public ItemKey(int par1) {
		super(par1);
		this.setTextureFile(PrivacyCraft.textureFile);
		this.setIconIndex(textureIndex);
		this.setItemName("PrivacyCraftItemKey");
		this.setCreativeTab(CreativeTabs.tabTools);
	}

	public boolean shouldRotateAroundWhenRendering() {
		return false;
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
		} else {
			if(itemstack.stackTagCompound.getString(Lockable.KEY_INDEX) == null) {
				itemstack.stackTagCompound.setString(Lockable.KEY_INDEX, Lockable.DEFAULT_CODE);
			}
		}
	}
}
