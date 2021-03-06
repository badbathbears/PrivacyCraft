package de.badbathbears.privacy.core;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.registry.LanguageRegistry;
import de.badbathbears.privacy.gui.ContainerCodeLock;
import de.badbathbears.privacy.gui.GuiCodeLock;
import de.badbathbears.privacy.gui.GuiKeyLock;
import de.badbathbears.privacy.lock.ItemKey;
import de.badbathbears.privacy.lock.KeyTile;
import de.badbathbears.privacy.lock.TileEntityLock;

public class CommonProxy implements IGuiHandler {
	public static final int GUI_KEY_LOCK = 0;
	public static final int GUI_CODE_LOCK = 1;

	public void registerRenderInformation() {
	}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if (!world.blockExists(x, y, z))
			return null;
		TileEntity tile = world.getBlockTileEntity(x, y, z);

		switch (ID) {
		case GuiCodeLock.ID_KEY:
		case GuiCodeLock.ID_CODE:
			if (!(tile instanceof TileEntityLock))
				return null;
			return new ContainerCodeLock();
		default:
			return null;
		}
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if (!world.blockExists(x, y, z))
			return null;
		TileEntity tile = world.getBlockTileEntity(x, y, z);
		ItemStack currentEquippedItem = player.getCurrentEquippedItem();
		if (!(tile instanceof TileEntityLock))
			return null;
		TileEntityLock lockTile = (TileEntityLock) tile;
		switch (ID) {

		case GuiCodeLock.ID_KEY:
			if (!(currentEquippedItem.getItem() instanceof ItemKey))
				return null;
			KeyTile keyTile = new KeyTile(currentEquippedItem); 
			return new GuiKeyLock(world,player, lockTile, keyTile, x, y, z, keyTile.isSet());
		case GuiCodeLock.ID_CODE:
			return new GuiCodeLock(world,player, lockTile, x, y, z, lockTile.isSet());
		default:
			return null;
		}
	}

	public void registerTiles() { // For registering TileEntities
	}

	public void registerBlocks() { // For registering Blocks
	}

	public void addNames() { // For adding Item's ingame names
		LanguageRegistry.addName(PrivacyCraft.blockableIronDoorItem, "Blockable iron door");
		LanguageRegistry.addName(PrivacyCraft.blockableWoodDoorItem, "Blockable wooden door");
		LanguageRegistry.addName(PrivacyCraft.blockableIronDoorBlock, "Blockable iron door");
		LanguageRegistry.addName(PrivacyCraft.blockableWoodDoorBlock, "Blockable wooden door");
		LanguageRegistry.addName(PrivacyCraft.stoneKeyLockBlock, "A lock for a key");
		LanguageRegistry.addName(PrivacyCraft.stoneKeyLockItem, "A lock for a key");
		LanguageRegistry.addName(PrivacyCraft.stoneCodeLockBlock, "A key code lock");
		LanguageRegistry.addName(PrivacyCraft.stoneCodeLockItem, "A key code lock");
		LanguageRegistry.addName(PrivacyCraft.keyItem, "A key");
	}

	public void addRecipes() { // For adding your Item's recipes
		
	}
}
