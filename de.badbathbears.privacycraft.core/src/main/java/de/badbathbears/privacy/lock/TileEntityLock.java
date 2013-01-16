package de.badbathbears.privacy.lock;

import net.minecraft.src.INetworkManager;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.Packet;
import net.minecraft.src.Packet132TileEntityData;
import net.minecraft.src.TileEntity;

public class TileEntityLock extends TileEntity implements Lockable {

	
	private String keyCode = Lockable.DEFAULT_CODE;
	private boolean isSet = false;

	public TileEntityLock() {
	}

	@Override
	public void onDataPacket(INetworkManager net, Packet132TileEntityData pkt) {
		super.onDataPacket(net, pkt);
		NBTTagCompound tag = pkt.customParam1;
		keyCode = tag.getString(Lockable.KEY_INDEX);
	}

	@Override
	public void writeToNBT(NBTTagCompound par1nbtTagCompound) {
		super.writeToNBT(par1nbtTagCompound);
		par1nbtTagCompound.setString(KEY_INDEX, this.getKeyCode());
	}

	@Override
	public void readFromNBT(NBTTagCompound par1nbtTagCompound) {
		super.readFromNBT(par1nbtTagCompound);
		this.setKeyCode(par1nbtTagCompound.getString(KEY_INDEX));
	}

	public String getKeyCode() {
		return keyCode;
	}

	public Packet getDescriptionPacket() {
		NBTTagCompound tag = new NBTTagCompound();
		this.writeToNBT(tag);
		return new Packet132TileEntityData(this.xCoord, this.yCoord, this.zCoord, 1, tag);
	}

	public void setKeyCode(String keyCode) {
		this.keyCode = keyCode;
	}

	public String getInvName() {
		return "lock.key";
	}

	public boolean isSet() {
		return isSet;
	}

	public void setSet(boolean isSet) {
		this.isSet = isSet;
	}

	@Override
	public LockType getType() {
		return LockType.BLOCK;
	}
}
