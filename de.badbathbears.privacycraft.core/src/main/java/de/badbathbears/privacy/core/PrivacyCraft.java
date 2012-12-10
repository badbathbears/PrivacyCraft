package de.badbathbears.privacy.core;

import net.minecraft.src.Item;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkMod.SidedPacketHandler;
import cpw.mods.fml.common.network.NetworkRegistry;

@NetworkMod(//
		clientSideRequired = true,// 
		serverSideRequired = true, //
		clientPacketHandlerSpec = @SidedPacketHandler(channels = { "MineTechForge" }, packetHandler = ClientPacketHandler.class),// 
		serverPacketHandlerSpec = @SidedPacketHandler(channels = {}, packetHandler = ServerPacketHandler.class)//
)
@Mod(modid = "PrivacyCraft", name = "PrivacyCraft", version = "1.0")
public class PrivacyCraft {
	// The instance, this is very important later on
	@Instance("PrivacyCraft")
	public static PrivacyCraft instance = new PrivacyCraft();

	// Tells Forge the location of your proxies
	@SidedProxy(clientSide = "de.badbathbears.privacy.core.ClientProxy", serverSide = "de.badbathbears.privacy.core.CommonProxy")
	public static CommonProxy proxy;

	public static Item ruby = new ItemRuby(15000);

	@Init
	public void InitCobaltCraft(FMLInitializationEvent event) {
		NetworkRegistry.instance().registerGuiHandler(this, proxy);
		proxy.addNames();
	}
}
