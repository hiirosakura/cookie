package org.hiirosakura.cookie.gui.widget.button

import net.minecraft.client.sound.PositionedSoundInstance
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.sound.SoundEvents
import net.minecraft.text.Text
import org.hiirosakura.cookie.common.WIDGET_TEXTURE
import org.hiirosakura.cookie.common.soundManager
import org.hiirosakura.cookie.common.textRenderer
import org.hiirosakura.cookie.gui.foundation.*
import org.hiirosakura.cookie.util.color.Color
import org.hiirosakura.cookie.util.color.Color4f
import org.hiirosakura.cookie.util.text

/**
 *

 * 项目名 cookie

 * 包名 org.hiirosakura.cookie.gui.widget.button

 * 文件名 Button

 * 创建时间 2022/2/21 18:03

 * @author forpleuvoir

 */
abstract class Button(var text: Text = "".text) : AbstractElement() {

	constructor(text: String = "") : this(text(text))

	override var width: Int = textRenderer.getWidth(text) + 8
		set(value) {
			field = value.coerceAtLeast(6)
		}
	override var height: Int = 20
		set(value) {
			field = value.coerceAtLeast(7)
		}

	var buttonColor: Color<out Number> = Color4f.WHITE

	override val render: (matrices: MatrixStack, delta: Number) -> Unit = { matrices, delta ->
		drawBackground(matrices, delta)
		drawCenteredText(
			matrices,
			text,
			x,
			if (active) y - 1 else y,
			width,
			height,
			color = Color4f.BLACK.alpha(if (active) 0.9f else 0.6f),
			shadow = false
		)
	}

	protected open fun drawBackground(matrices: MatrixStack, delta: Number) {
		setShaderTexture(WIDGET_TEXTURE)
		enableBlend()
		defaultBlendFunc()
		enableDepthTest()
		setShaderColor(buttonColor)
		val u = if (active) if (mouseHover()) 16 else 0 else 32
		draw9Texture(matrices, x, y, 4, width, height, 16, u, 16, 16)
		disableBlend()
	}

	override var mouseClick: (mouseX: Number, mouseY: Number, button: Int) -> Boolean = { _, _, button ->
		if (button == 0) soundManager.play(PositionedSoundInstance.master(SoundEvents.UI_BUTTON_CLICK, 1.0f))
		onClick.invoke(button)
		true
	}

}

inline fun ParentElement.button(
	text: String,
	width: Int = textRenderer.getWidth(text) + 8,
	height: Int = 20,
	color: Color<out Number> = Color4f.WHITE,
	noinline onClick: Button.(Int) -> Unit = { },
	scope: Button.() -> Unit = {}
): Button = button(text(text), width, height, color, onClick, scope)


inline fun ParentElement.button(
	text: Text,
	width: Int = textRenderer.getWidth(text) + 8,
	height: Int = 20,
	color: Color<out Number> = Color4f.WHITE,
	noinline onClick: Button.(Int) -> Unit = { },
	scope: Button.() -> Unit = {}
): Button {
	val button = object : Button(text) {}.apply {
		this.width = width
		this.height = height
		this.buttonColor = color
		this.onClick = { onClick.invoke(this, it) }
		this.scope()
	}
	this.addElement(button)
	return button
}