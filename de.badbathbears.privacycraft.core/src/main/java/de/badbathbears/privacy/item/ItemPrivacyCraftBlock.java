package de.badbathbears.privacy.item;

import net.minecraft.src.Block;
import net.minecraft.src.ItemBlock;

public class ItemPrivacyCraftBlock extends ItemBlock {

	public ItemPrivacyCraftBlock(Block block) {
		super(block.blockID - 256);
	}


}
