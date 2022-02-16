package org.hiirosakura.cookie.fabric.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.RunArgs;
import org.hiirosakura.cookie.fabric.CookieFabric;
import org.hiirosakura.cookie.mod.CookieModKt;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * 项目名 cookie
 * <p>
 * 包名 org.hiirosakura.cookie.fabric.mixin
 * <p>
 * 文件名 MixinMinecraftClient
 * <p>
 * 创建时间 2022/2/16 15:33
 *
 * @author forpleuvoir
 */
@Mixin(MinecraftClient.class)
public abstract class MixinMinecraftClient {

	@Inject(method = "<init>", at = @At("HEAD"))
	private static void init(RunArgs args, CallbackInfo ci) {
		CookieModKt.setCookieMod(CookieFabric.INSTANCE);
	}

}
