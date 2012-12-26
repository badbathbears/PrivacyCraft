package de.badbathbears.privacy.core;

import net.minecraft.src.Block;
import net.minecraft.src.Item;
import net.minecraft.src.Material;
import net.minecraft.src.TileEntity;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkMod.SidedPacketHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import de.badbathbears.privacy.block.BlockBlockableDoor;
import de.badbathbears.privacy.block.BlockCodeLock;
import de.badbathbears.privacy.block.BlockKeyLock;
import de.badbathbears.privacy.block.TileEntityLock;
import de.badbathbears.privacy.item.ItemBlockableDoor;
import de.badbathbears.privacy.item.ItemCodeLock;
import de.badbathbears.privacy.item.ItemKey;
import de.badbathbears.privacy.item.ItemKeyLock;

@NetworkMod(//
		clientSideRequired = true,// 
		serverSideRequired = true, //
		clientPacketHandlerSpec = @SidedPacketHandler(channels = { "MineTechForge" }, packetHandler = ClientPacketHandler.class),// 
		serverPacketHandlerSpec = @SidedPacketHandler(channels = {"PCLockCode"}, packetHandler = ServerPacketHandler.class)//
)
@Mod(modid = "PrivacyCraft", name = "PrivacyCraft", version = "1.0")
public class PrivacyCraft {
	// The instance, this is very important later on
	@Instance("PrivacyCraft")
	public static PrivacyCraft instance = new PrivacyCraft();

	// Tells Forge the location of your proxies
	@SidedProxy(clientSide = "de.badbathbears.privacy.core.ClientProxy", serverSide = "de.badbathbears.privacy.core.CommonProxy")
	public static CommonProxy proxy;

	public static final String texturePath = "/PrivacyCraftGFX/";
	public static final String textureFile = texturePath + "Textures.png";
	// ITEMS
	public static Item blockableIronDoorItem = new ItemBlockableDoor(15001,Material.iron);
	public static Item blockableWoodDoorItem = new ItemBlockableDoor(15002,Material.wood);
	public static Item keyItem = new ItemKey(15003);
	public static Item stoneKeyLockItem = new ItemKeyLock(15004);
	public static Item stoneCodeLockItem = new ItemCodeLock(15005);
	
	// BLOCKS
	public static Block blockableWoodDoorBlock = new BlockBlockableDoor(2049,Material.wood);
	public static Block blockableIronDoorBlock = new BlockBlockableDoor(2050,Material.iron);
	public static Block stoneKeyLockBlock = new BlockKeyLock(2051,1,Material.rock);
	public static Block stoneCodeLockBlock = new BlockCodeLock(2052,1,Material.rock);


	@Init
	public void InitCobaltCraft(FMLInitializationEvent event) {
		GameRegistry.registerTileEntity(TileEntityLock.class, "PrivacyCraftTileEntityLock");
		NetworkRegistry.instance().registerGuiHandler(this, proxy);
		proxy.addNames();
		proxy.addRecipes();
		TileEntity.addMapping(TileEntityLock.class, "Lock");
	}
}
