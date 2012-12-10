package de.badbathbears.privacy.core;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.World;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class CommonProxy implements IGuiHandler {
	public void registerRenderInformation() {
	}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return null;
	}

	public void registerTiles() { // For registering TileEntities
	}

	public void registerBlocks() { // For registering Blocks
	}

	public void addNames() { // For adding Item's ingame names
		LanguageRegistry.addName(PrivacyCraft.ruby, "Ruby");
	}

	public void addRecipes() { // For adding your Item's recipes
	}
}
