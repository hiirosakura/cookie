package org.hiirosakura.cookie.gui.widget.button

import net.minecraft.client.util.math.MatrixStack
import net.minecraft.text.Text
import org.hiirosakura.cookie.common.textRenderer
import org.hiirosakura.cookie.gui.foundation.ParentElement
import org.hiirosakura.cookie.gui.foundation.drawCenteredText
import org.hiirosakura.cookie.gui.foundation.drawTexture
import org.hiirosakura.cookie.gui.texture.GuiTextures.BUTTON_1
import org.hiirosakura.cookie.gui.texture.GuiTextures.BUTTON_1_HOVERED
import org.hiirosakura.cookie.gui.texture.GuiTextures.BUTTON_1_PRESSED
import org.hiirosakura.cookie.gui.widget.ClickableElement
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
abstract class Button(var text: Text = "".text) : ClickableElement() {

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
		val textY = status(y - 1, y - 2, y)
		drawCenteredText(
			matrices,
			text,
			x,
			textY,
			width,
			height,
			color = Color4f.BLACK.alpha(if (active) 0.9f else 0.6f),
			shadow = false
		)
	}

	protected open fun drawBackground(matrices: MatrixStack, delta: Number) {
		val texture = status(BUTTON_1, BUTTON_1_HOVERED, BUTTON_1_PRESSED)
		drawTexture(matrices, x, y, width, height, texture, buttonColor)
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