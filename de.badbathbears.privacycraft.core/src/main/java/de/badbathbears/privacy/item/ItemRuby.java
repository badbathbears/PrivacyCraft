package de.badbathbears.privacy.item;

import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;
import de.badbathbears.privacy.core.PrivacyCraft;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.Item;

public class ItemRuby extends Item {
	public ItemRuby(int par1) {
		super(par1);
		this.setTextureFile(PrivacyCraft.textureFile);
		this.setItemName("PrivacyCraftItemRuby");
		this.setCreativeTab(CreativeTabs.tabMaterials);
	}

	@SideOnly(Side.CLIENT)
	public int getItemIconFromDamage(int i) {
		return 0;
	}
}