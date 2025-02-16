package org.rusherhack.nosound;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import org.rusherhack.client.api.feature.module.ModuleCategory;
import org.rusherhack.client.api.feature.module.ToggleableModule;
import org.rusherhack.core.setting.BooleanSetting;
import org.rusherhack.core.setting.NullSetting;
import org.rusherhack.core.setting.Setting;

import java.util.HashMap;

/**
 * @author john@rusherhack.org 2/15/2025
 */
public class NoSoundsModule extends ToggleableModule {
	
	public static NoSoundsModule INSTANCE;
	public final HashMap<ResourceLocation, BooleanSetting> soundSettingMap = new HashMap<>();
	
	public NoSoundsModule() {
		super("NoSounds", "Toggle some sounds of the game", ModuleCategory.MISC);
		INSTANCE = this;
		
		final NullSetting sounds = new NullSetting("Sounds");
		for(SoundEvent soundEvent : BuiltInRegistries.SOUND_EVENT) {
			
			final ResourceLocation soundLocation = soundEvent.getLocation();
			final String soundName = soundLocation.getPath();
			
			final BooleanSetting setting = this.createAndAddSetting(sounds, soundName);
			
			if(setting == null) {
				this.getLogger().warn("Failed to create setting for " + soundName);
				continue;
			}
			
			this.soundSettingMap.put(soundLocation, setting);
		}
		
		this.registerSettings(sounds);
	}
	
	public boolean shouldCancelSound(ResourceLocation sound) {
		Setting<?> setting = this.soundSettingMap.get(sound);
		
		if(setting == null) {
			return false;
		}
		
		while(setting instanceof BooleanSetting bool) {
			
			if(!bool.getValue()) {
				return true;
			}
			
			setting = bool.getParent();
		}
		
		return false;
	}
	
	private BooleanSetting createAndAddSetting(NullSetting sounds, String soundName) {
		final int i = soundName.lastIndexOf('.');
		
		if(i < 0) {
			return new BooleanSetting(soundName, true);
		}
		
		Setting<?> soundSetting = sounds;
		for(String s : soundName.split("\\.")) {
			
			Setting<?> subSetting = soundSetting.getSubSetting(s);
			
			if(subSetting == null) {
				final BooleanSetting setting = new BooleanSetting(s, true);
				soundSetting.addSubSettings(setting);
				subSetting = setting;
			}
			
			soundSetting = subSetting;
		}
		
		if(soundSetting instanceof BooleanSetting bool) {
			return bool;
		}
		
		return null;
	}
	
}
