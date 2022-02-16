package org.hiirosakura.cookie.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
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
 * 文件名 MixinMouse
 * <p>
 * 创建时间 2022/2/17 1:03
 *
 * @author forpleuvoir
 */
@Mixin(Mouse.class)
public abstract class MixinMouse {

	@Shadow
	@Final
	private MinecraftClient client;

	@Inject(method = "onMouseButton", at = @At("HEAD"), cancellable = true)
	public void onMouseButton(long window, int button, int action, int mods, CallbackInfo ci) {
		if (window == this.client.getWindow().getHandle()) {
			if (action == 1) {
				if (InputHandler.keyPress(button)) ci.cancel();
			} else {
				if (InputHandler.keyRelease(button)) ci.cancel();
			}
		}
	}
}
