package de.badbathbears.privacy.core;

import net.minecraftforge.client.MinecraftForgeClient;

public class ClientProxy extends CommonProxy {

	public void registerRenderInformation() {
		MinecraftForgeClient.preloadTexture("/PrivacyCraftGFX/Items.png");
		MinecraftForgeClient.preloadTexture("/PrivacyCraftGFX/Blocks.png");
	}

}