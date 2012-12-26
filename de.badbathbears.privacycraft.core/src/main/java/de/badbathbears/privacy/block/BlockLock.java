package de.badbathbears.privacy.block;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

import net.minecraft.src.BlockContainer;
import net.minecraft.src.EntityClientPlayerMP;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.Material;
import net.minecraft.src.MathHelper;
import net.minecraft.src.Packet250CustomPayload;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;
import de.badbathbears.privacy.core.PrivacyCraft;

public abstract class BlockLock extends BlockContainer {

	protected int lockedTexture;
	protected int unlockedTexture;
	protected int defaultTexture;

	protected BlockLock(int par1, int par2, Material par3Material) {
		super(par1, par2, par3Material);
		defaultTexture = 1;
	}

	@Override
	public TileEntity createNewTileEntity(World var1) {
		return new TileEntityLock();
	}

	public static boolean isLocked(int blockMetadata) {
		return (blockMetadata & 8) == 8;
	}

	public static void toggleLocked(World world, int x, int y, int z, EntityPlayer playerEntity) {
		int blockMetadata = world.getBlockMetadata(x, y, z);
		if (isLocked(blockMetadata)) {
			// xxx1 -> xxx0
			unlock(world, x, y, z, blockMetadata, playerEntity);
		} else {
			// xxx0 -> xxx1
			lock(world, x, y, z, blockMetadata, playerEntity);
		}
	}

	public static void unlock(World world, int x, int y, int z, int blockMetadata, EntityPlayer playerEntity) {
		TileEntityLock tile = (TileEntityLock) world.getBlockTileEntity(x, y, z);
		world.setBlockMetadataWithNotify(x, y, z, (blockMetadata & 7));
		sendPacket(playerEntity, tile.getKeyCode(), tile.isSet(), x, y, z, false);
	}

	public static void lock(World world, int x, int y, int z, int blockMetadata, EntityPlayer playerEntity) {
		TileEntityLock tile = (TileEntityLock) world.getBlockTileEntity(x, y, z);
		world.setBlockMetadataWithNotify(x, y, z, (blockMetadata | 8));
		// TileEntityLock newTile = (TileEntityLock) world.getBlockTileEntity(x,
		// y, z);
		// newTile.setKeyCode(tile.getKeyCode());
		sendPacket(playerEntity, tile.getKeyCode(), tile.isSet(), x, y, z, true);
	}

	public static void sendPacket(EntityPlayer playerEntity, String keyCode, boolean set, int x, int y, int z, boolean locked) {
		Side side = FMLCommonHandler.instance().getEffectiveSide();
		if (side == Side.CLIENT) {
			ByteArrayOutputStream bos = new ByteArrayOutputStream(28);
			DataOutputStream outputStream = new DataOutputStream(bos);
			try {
				outputStream.writeInt(x);
				outputStream.writeInt(y);
				outputStream.writeInt(z);
				outputStream.writeBoolean(locked);
				outputStream.writeBoolean(set);
				outputStream.writeInt(keyCode.length());

				outputStream.writeChars(keyCode);
			} catch (Exception ex) {
				ex.printStackTrace();
			}

			Packet250CustomPayload packet = new Packet250CustomPayload();
			packet.channel = "PCLockCode";
			packet.data = bos.toByteArray();
			packet.length = bos.size();

			((EntityClientPlayerMP) playerEntity).sendQueue.addToSendQueue(packet);
		}
	}

	@Override
	public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLiving par5EntityLiving) {
		super.onBlockPlacedBy(par1World, par2, par3, par4, par5EntityLiving);
		int var6 = MathHelper.floor_double((double) (par5EntityLiving.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
		if (var6 == 0) {
			par1World.setBlockMetadataWithNotify(par2, par3, par4, 2);
		}

		if (var6 == 1) {
			par1World.setBlockMetadataWithNotify(par2, par3, par4, 5);
		}

		if (var6 == 2) {
			par1World.setBlockMetadataWithNotify(par2, par3, par4, 3);
		}

		if (var6 == 3) {
			par1World.setBlockMetadataWithNotify(par2, par3, par4, 4);
		}
	}

	@SideOnly(Side.CLIENT)
	/**
	 * Retrieves the block texture to use based on the display side. Args: iBlockAccess, x, y, z, side
	 */
	public int getBlockTexture(IBlockAccess par1IBlockAccess, int x, int y, int z, int side) {
		int meta = par1IBlockAccess.getBlockMetadata(x, y, z);

		if (isLocked(meta)) {
			meta = meta - 8;
		}
		return side != meta ? defaultTexture : (this.isLocked(par1IBlockAccess, x, y, z) ? lockedTexture : unlockedTexture);
	}

	private boolean isLocked(IBlockAccess world, int x, int y, int z) {
		int blockMetadata = world.getBlockMetadata(x, y, z);
		return isLocked(blockMetadata);
	}

	@Override
	public String getTextureFile() {
		return PrivacyCraft.textureFile;
	}

	protected TileEntity getTile(World world, int par2, int par3, int par4) {
		return world.getBlockTileEntity(par2, par3, par4);
	}
}
