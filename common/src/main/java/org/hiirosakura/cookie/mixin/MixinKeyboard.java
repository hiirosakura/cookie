package org.hiirosakura.cookie.mixin;

import net.minecraft.client.Keyboard;
import net.minecraft.client.MinecraftClient;
import org.hiirosakura.cookie.input.InputHandler;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * 项目名 cookie
 * <p>
 * 包名 org.hiirosakura.cookie.mixin
 * <p>
 * 文件名 MixinKeyboard
 * <p>
 * 创建时间 2022/2/17 0:24
 *
 * @author forpleuvoir
 */
@Mixin(Keyboard.class)
public abstract class MixinKeyboard {

	@Shadow
	@Final
	private MinecraftClient client;

	@Shadow
	private boolean repeatEvents;

	@Inject(method = "onKey", at = @At("HEAD"), cancellable = true)
	public void onKey(long window, int key, int scancode, int action, int modifiers, CallbackInfo ci) {
		if (window == this.client.getWindow().getHandle()) {
			if (action == 1 || action == 2 && this.repeatEvents) {
				if (InputHandler.keyPress(key)) ci.cancel();
			} else {
				if (InputHandler.keyRelease(key)) ci.cancel();
			}
		}
	}
}
