package de.badbathbears.privacy.core;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.logging.Logger;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.INetworkManager;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Packet250CustomPayload;
import net.minecraft.src.World;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;
import de.badbathbears.privacy.lock.BlockLock;
import de.badbathbears.privacy.lock.ItemKey;
import de.badbathbears.privacy.lock.KeyTile;
import de.badbathbears.privacy.lock.Lockable;
import de.badbathbears.privacy.lock.Lockable.LockType;
import de.badbathbears.privacy.lock.TileEntityLock;

public class ServerPacketHandler implements IPacketHandler {
	private static Logger logger = Logger.getLogger(ServerPacketHandler.class.getSimpleName());

	@Override
	public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player) {
		if (packet.channel.equals("PCLockCode")) {
			DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(packet.data));
			int blockX;
			int blockY;
			int blockZ;
			String keyCode = "";
			boolean set = false;
			boolean locked = false;
			LockType type = null;
			try {
				blockX = inputStream.readInt();
				blockY = inputStream.readInt();
				blockZ = inputStream.readInt();
				type = LockType.values()[inputStream.readInt()];
				locked = inputStream.readBoolean();
				set = inputStream.readBoolean();
				int length = inputStream.readInt();
				for (int i = 0; i < length; i++) {
					keyCode += inputStream.readChar();
				}
			} catch (IOException e) {
				logger.warning("missformed inputstream in PCLockCode channel " + e.getMessage());
				return;
			}
			EntityPlayer ePlayer = (EntityPlayer) player;
			World world = ePlayer.worldObj;
			Lockable lock;
			System.out.println("serverpackagehandler	get package code= " + keyCode + " " + set);
			if (type == LockType.BLOCK) {
				lock = handleBlock(blockX, blockY, blockZ, locked, ePlayer, world);
				lock.setKeyCode(keyCode);
				lock.setSet(set);
			} else if (type == LockType.ITEM) {
				lock = handleItem(ePlayer);
				lock.setKeyCode(keyCode);
				lock.setSet(set);
			} else if (type == LockType.BOTH) {
				lock = handleBlock(blockX, blockY, blockZ, locked, ePlayer, world);
				lock.setKeyCode(keyCode);
				lock.setSet(set);
				lock = handleItem(ePlayer);
				lock.setKeyCode(keyCode);
				lock.setSet(set);
			} else {
				logger.warning("unknown type " + type.name() + " in PCLockCode channel");
				return;
			}
		}
	}

	private Lockable handleItem(EntityPlayer ePlayer) {
		Lockable lock;
		ItemStack currentEquippedItem = ePlayer.getCurrentEquippedItem();
		if (currentEquippedItem.getItem() instanceof ItemKey) {
			lock = new KeyTile(currentEquippedItem);
		} else {
			logger.warning("wrong item equipped");
			lock = null;
		}
		return lock;
	}

	private Lockable handleBlock(int blockX, int blockY, int blockZ, boolean locked, EntityPlayer ePlayer, World world) {
		Lockable lock;
		int blockMetadata = world.getBlockMetadata(blockX, blockY, blockZ);
		if (BlockLock.isLocked(blockMetadata) && !locked) {
			BlockLock.unlock(world, blockX, blockY, blockZ, blockMetadata, ePlayer);
		} else if (!BlockLock.isLocked(blockMetadata) && locked) {
			BlockLock.lock(world, blockX, blockY, blockZ, blockMetadata, ePlayer);
		}
		lock = (TileEntityLock) world.getBlockTileEntity(blockX, blockY, blockZ);
		return lock;
	}
}