package de.badbathbears.privacy.block;

import net.minecraft.src.INetworkManager;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.Packet;
import net.minecraft.src.Packet132TileEntityData;
import net.minecraft.src.TileEntity;

public class TileEntityLock extends TileEntity {

	public static final String DEFAULT = "0000";
	private String keyCode = DEFAULT;
	private boolean isSet = false;

	public TileEntityLock() {
	}

	@Override
	public void onDataPacket(INetworkManager net, Packet132TileEntityData pkt) {
		super.onDataPacket(net, pkt);
		NBTTagCompound tag = pkt.customParam1;
		keyCode = tag.getString("KeyCode");
	}

	@Override
	public void writeToNBT(NBTTagCompound par1nbtTagCompound) {
		super.writeToNBT(par1nbtTagCompound);
		par1nbtTagCompound.setString("KeyCode", this.getKeyCode());
	}

	@Override
	public void readFromNBT(NBTTagCompound par1nbtTagCompound) {
		super.readFromNBT(par1nbtTagCompound);
		this.setKeyCode(par1nbtTagCompound.getString("KeyCode"));
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
}
