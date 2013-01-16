package de.badbathbears.privacy.gui;

import net.minecraft.src.Container;
import net.minecraft.src.EntityPlayer;

public class ContainerCodeLock extends Container {

	public ContainerCodeLock() {
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer var1) {
		return true;
	}

}
