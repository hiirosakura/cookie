package org.hiirosakura.cookie.mixin;

import net.minecraft.client.main.Main;
import org.hiirosakura.cookie.common.CommonKt;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Arrays;

/**
 * 项目名 cookie
 * <p>
 * 包名 org.hiirosakura.cookie.mixin
 * <p>
 * 文件名 MixinMain
 * <p>
 * 创建时间 2022/2/23 17:47
 *
 * @author forpleuvoir
 */
@Mixin(Main.class)
public abstract class MixinMain {

	@Inject(method = "main", at = @At(value = "INVOKE", target = "Lnet/minecraft/SharedConstants;createGameVersion()V", shift = At.Shift.AFTER))
	private static void main(String[] args, CallbackInfo ci) {
		CommonKt.setDevEnv(Arrays.asList(args).contains("dev"));
	}

}
