package de.badbathbears.privacy.block;

import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.TileEntity;

public class TileEntityLock extends TileEntity {

	
	private int keyCode = 0;
	private boolean locked = false;

	@Override
	public void writeToNBT(NBTTagCompound par1nbtTagCompound) {
		super.writeToNBT(par1nbtTagCompound);
		par1nbtTagCompound.setInteger("KeyCode", this.getKeyCode());
		par1nbtTagCompound.setBoolean("Locked", this.isLocked());		
	}
	
	@Override
	public void readFromNBT(NBTTagCompound par1nbtTagCompound) {
		super.readFromNBT(par1nbtTagCompound);
		this.setKeyCode(par1nbtTagCompound.getInteger("KeyCode"));
		this.setLocked(par1nbtTagCompound.getBoolean("Locked"));
	}

	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	public int getKeyCode() {
		return keyCode;
	}

	public void setKeyCode(int keyCode) {
		this.keyCode = keyCode;
	}
}
