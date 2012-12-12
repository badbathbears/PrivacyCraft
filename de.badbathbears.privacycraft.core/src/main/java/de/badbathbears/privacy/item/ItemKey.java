package de.badbathbears.privacy.item;

import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.Item;

public class ItemKey extends Item {

	public ItemKey(int par1) {
		super(par1);
		setTextureFile("/PrivacyCraftGFX/Items.png");
		setItemName("PrivacyCraftItemKey");
		setCreativeTab(CreativeTabs.tabTools);
	}

	@SideOnly(Side.CLIENT)
	public int getItemIconFromDamage(int i) {
		return 0;
	}
	
	@Override
	public int getItemStackLimit() {
		return 0;
	}
}
