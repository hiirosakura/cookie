package org.hiirosakura.cookie.mixin;

import net.minecraft.client.MinecraftClient;
import org.hiirosakura.cookie.input.InputHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * 项目名 cookie
 * <p>
 * 包名 org.hiirosakura.cookie.mixin
 * <p>
 * 文件名 MixinMinecraftClient
 * <p>
 * 创建时间 2022/2/17 1:04
 *
 * @author forpleuvoir
 */
@Mixin(MinecraftClient.class)
public abstract class MixinMinecraftClient {

	@Inject(method = "tick", at = @At("RETURN"))
	public void tickEnd(CallbackInfo ci) {
		InputHandler.INSTANCE.tick();
	}
}
