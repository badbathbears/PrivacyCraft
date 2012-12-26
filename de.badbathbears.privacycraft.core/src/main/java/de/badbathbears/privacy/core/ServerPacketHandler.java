package de.badbathbears.privacy.core;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.INetworkManager;
import net.minecraft.src.Packet250CustomPayload;
import net.minecraft.src.World;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;
import de.badbathbears.privacy.lock.BlockLock;
import de.badbathbears.privacy.lock.TileEntityLock;

public class ServerPacketHandler implements IPacketHandler {

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
			try {
				blockX = inputStream.readInt();
				blockY = inputStream.readInt();
				blockZ = inputStream.readInt();
				locked = inputStream.readBoolean();
				set = inputStream.readBoolean();
				int length = inputStream.readInt();
				for (int i = 0; i < length; i++) {
					keyCode += inputStream.readChar();
				}
			} catch (IOException e) {
				e.printStackTrace();
				return;
			}
			
			EntityPlayer ePlayer = (EntityPlayer)player;
			World world = ePlayer.worldObj;
			
			int blockMetadata = world.getBlockMetadata(blockX, blockY, blockZ);
			if (BlockLock.isLocked(blockMetadata) && !locked) {
				BlockLock.unlock(world, blockX, blockY, blockZ, blockMetadata, ePlayer);
			} else if (!BlockLock.isLocked(blockMetadata) && locked) {
				BlockLock.lock(world, blockX, blockY, blockZ, blockMetadata, ePlayer);
			}
			TileEntityLock tileEntity = (TileEntityLock) world.getBlockTileEntity(blockX, blockY, blockZ);
			tileEntity.setKeyCode(keyCode);
			tileEntity.setSet(set);
			}

	}
}