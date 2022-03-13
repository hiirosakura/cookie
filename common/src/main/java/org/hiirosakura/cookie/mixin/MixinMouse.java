package org.hiirosakura.cookie.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import org.hiirosakura.cookie.gui.foundation.ElementKt;
import org.hiirosakura.cookie.gui.screen.ScreenManager;
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

	@Shadow
	private int activeButton;

	@Shadow
	private double glfwTime;

	@Shadow
	private double x;

	@Shadow
	private double y;

	@Inject(method = "onMouseButton", at = @At("HEAD"), cancellable = true)
	public void onMouseButton(long window, int button, int action, int mods, CallbackInfo ci) {
		if (window == this.client.getWindow().getHandle()) {
			if (action == 1) {
				this.activeButton = button;
				if (InputHandler.keyPress(button)) ci.cancel();
				ScreenManager.hasScreen(screen -> {
					screen.mouseClick(ElementKt.getMouseX(), ElementKt.getMouseY(), button);
					ci.cancel();
				});
			} else {
				this.activeButton = -1;
				if (InputHandler.keyRelease(button)) ci.cancel();
				ScreenManager.hasScreen(screen -> {
					screen.mouseRelease(ElementKt.getMouseX(), ElementKt.getMouseY(), button);
					ci.cancel();
				});
			}
		}
	}

	@Inject(method = "onMouseScroll", at = @At("HEAD"), cancellable = true)
	public void onMouseScroll(long window, double horizontal, double vertical, CallbackInfo ci) {
		if (window == this.client.getWindow().getHandle()) {
			ScreenManager.hasScreen(screen -> {
				double amount = (this.client.options.discreteMouseScroll ? Math.signum(vertical) : vertical) * this.client.options.mouseWheelSensitivity;
				screen.mouseScrolling(ElementKt.getMouseX(), ElementKt.getMouseY(), amount);
				ci.cancel();
			});
		}
	}

	@Inject(method = "onCursorPos", at = @At("HEAD"))
	public void onCursorPos(long window, double x, double y, CallbackInfo ci) {
		if (window == this.client.getWindow().getHandle()) {
			ScreenManager.hasScreen(screen -> screen.mouseMove(ElementKt.getMouseX(), ElementKt.getMouseY()));
			if (this.activeButton != -1 && this.glfwTime > 0.0) {
				double deltaX = (x - this.x) * (double) this.client.getWindow().getScaledWidth() / (double) this.client.getWindow().getWidth();
				double deltaY = (y - this.y) * (double) this.client.getWindow().getScaledHeight() / (double) this.client.getWindow().getHeight();
				ScreenManager.hasScreen(screen -> screen.mouseDragging(ElementKt.getMouseX(), ElementKt.getMouseY(), this.activeButton, deltaX, deltaY));
			}
		}
	}
}
