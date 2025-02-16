package org.rusherhack.nosound;

import org.rusherhack.client.api.RusherHackAPI;

/**
 * RusherHack plugin that allows you to disable some game sounds
 *
 * @author john@rusherhack.org 2/15/2025
 */
public class Plugin extends org.rusherhack.client.api.plugin.Plugin {
	
	@Override
	public void onLoad() {
		this.getLogger().info("Loading NoSounds");
		
		RusherHackAPI.getModuleManager().registerFeature(new NoSoundsModule());
	}
	
	@Override
	public void onUnload() {
		this.getLogger().info("NoSounds unloaded!");
	}
	
}