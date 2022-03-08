package org.hiirosakura.cookie.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import org.hiirosakura.cookie.gui.screen.ScreenManager;
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
 * 文件名 MixinGameRenderer
 * <p>
 * 创建时间 2022/2/20 22:18
 *
 * @author forpleuvoir
 */
@Mixin(GameRenderer.class)
public abstract class MixinGameRenderer {

	@Shadow
	@Final
	private MinecraftClient client;

	@Inject(method = "render", at = @At("RETURN"))
	public void renderScreen(float tickDelta, long startTime, boolean tick, CallbackInfo ci) {
		ScreenManager.hasScreen(screen -> screen.render(new MatrixStack(), client.getLastFrameDuration()));
	}

}
