package org.rusherhack.nosound.mixins;

import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.SoundEngine;
import org.rusherhack.nosound.NoSoundsModule;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author john@rusherhack.org 2/15/2025
 */
@Mixin(value = SoundEngine.class)
public class MixinSoundEngine {
	
	@Inject(method = "play", at = @At("HEAD"), cancellable = true)
	private void onPlaySound(SoundInstance sound, CallbackInfo ci) {
		
		if(NoSoundsModule.INSTANCE == null || !NoSoundsModule.INSTANCE.isToggled()) {
			return;
		}
		
		if(NoSoundsModule.INSTANCE.shouldCancelSound(sound.getLocation())) {
			ci.cancel();
		}
	}
}