package org.hiirosakura.cookie.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.Window;
import org.hiirosakura.cookie.gui.screen.ScreenManager;
import org.hiirosakura.cookie.initialize._InitializeKt;
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
 * 文件名 MixinMinecraftClient
 * <p>
 * 创建时间 2022/2/17 1:04
 *
 * @author forpleuvoir
 */
@Mixin(MinecraftClient.class)
public abstract class MixinMinecraftClient {

	@Shadow
	private volatile boolean paused;

	@Shadow
	@Final
	private Window window;

	@Inject(method = "tick", at = @At("RETURN"))
	public void tickEnd(CallbackInfo ci) {
		InputHandler.INSTANCE.tick();
	}


	@Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/sound/SoundManager;tick(Z)V", shift = At.Shift.BEFORE))
	public void screenTick(CallbackInfo ci) {
		ScreenManager.INSTANCE.tick();
	}

	@Inject(method = "onResolutionChanged", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/Window;setScaleFactor(D)V", shift = At.Shift.AFTER))
	public void screenResize(CallbackInfo ci) {
		ScreenManager.hasScreen(screen -> {
			screen.setHeight(this.window.getScaledHeight());
			screen.setWidth(this.window.getScaledWidth());
		});
	}

	@Inject(method = "openPauseMenu", at = @At(value = "HEAD"), cancellable = true)
	public void openPauseMenu(CallbackInfo ci) {
		if (ScreenManager.hasScreen()) ci.cancel();
	}

	@Inject(method = "setScreen", at = @At(value = "HEAD"), cancellable = true)
	public void setScreen(CallbackInfo ci) {
		if (ScreenManager.hasScreen()) ci.cancel();
	}

	@Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/Window;setPhase(Ljava/lang/String;)V", shift = At.Shift.AFTER))
	public void paused(boolean tick, CallbackInfo ci) {
		this.paused = ScreenManager.hasScreen() && ScreenManager.INSTANCE.getCurrent().getPauseScreen();
	}

	@Inject(method = "<init>", at = @At(value = "RETURN"))
	public void init(CallbackInfo ci) {
		_InitializeKt.initialing();
	}

	@Inject(method = "run", at = @At("HEAD"))
	public void run(CallbackInfo ci) {
		_InitializeKt.initialized();
	}

}
