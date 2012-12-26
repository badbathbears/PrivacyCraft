package de.badbathbears.privacy.gui;

import de.badbathbears.privacy.lock.TileEntityLock;
import net.minecraft.src.Container;
import net.minecraft.src.EntityPlayer;

public class ContainerCodeLock extends Container {

	private TileEntityLock lock;

	public ContainerCodeLock(TileEntityLock lock) {
		this.lock = lock;
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer var1) {
		return true;
	}

}
