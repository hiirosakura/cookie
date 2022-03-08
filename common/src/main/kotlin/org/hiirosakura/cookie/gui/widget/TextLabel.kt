package org.hiirosakura.cookie.gui.widget

import net.minecraft.client.render.LightmapTextureManager
import net.minecraft.client.render.Tessellator
import net.minecraft.client.render.VertexConsumerProvider
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.text.Text
import org.hiirosakura.cookie.common.textRenderer
import org.hiirosakura.cookie.gui.foundation.AbstractElement
import org.hiirosakura.cookie.gui.foundation.Align
import org.hiirosakura.cookie.gui.foundation.Align.*
import org.hiirosakura.cookie.gui.foundation.ParentElement
import org.hiirosakura.cookie.gui.foundation.drawOutlinedBox
import org.hiirosakura.cookie.util.color.Color
import org.hiirosakura.cookie.util.color.Color4i
import org.hiirosakura.cookie.util.text

/**
 *

 * 项目名 cookie

 * 包名 org.hiirosakura.cookie.gui.widget

 * 文件名 TextLabel

 * 创建时间 2022/2/20 18:23

 * @author forpleuvoir

 */
class TextLabel(
	var text: Text,
	override var width: Int = textRenderer.getWidth(text),
	override var height: Int = textRenderer.fontHeight,
) : AbstractElement() {

	constructor(text: String, width: Int, height: Int) : this(text(text), width, height)

	var align: Align = CENTER

	private val textWidth: Int get() = textRenderer.getWidth(this.text)
	private val textHeight: Int get() = textRenderer.fontHeight

	private val centerX: Double get() = this.x + this.width / 2
	private val centerY: Double get() = this.y + this.height / 2

	var textColor: Color<out Number> = Color4i().fromInt(Color4i.WHITE.rgba)
	var rightToLeft: Boolean = false
	var shadow: Boolean = true
	var backgroundColor: Color<out Number> = Color4i.BLACK.apply { alpha = 0 }
	var bordColor: Color<out Number> = Color4i.BLACK.apply { alpha = 0 }

	private val renderText: String
		get() {
			return if (this.width - padding.horizontal >= textRenderer.getWidth(text)) {
				text.string
			} else {
				textRenderer.trimToWidth(text, (this.width - padding.horizontal).toInt()).string
			}
		}

	override val render: (matrices: MatrixStack, delta: Number) -> Unit = { matrices: MatrixStack, _: Number ->
		drawOutlinedBox(matrices, x, y, width, height, backgroundColor, bordColor, 1, false)
		renderText(matrices)
	}

	private fun renderText(matrices: MatrixStack) {
		val textX: Double
		val textY: Double
		when (align) {
			TOP_LEFT -> {
				textX = this.x + padding.left
				textY = this.y + padding.top
			}
			TOP_CENTER -> {
				textX = centerX - textWidth / 2
				textY = this.y + padding.top
			}
			TOP_RIGHT -> {
				textX = this.x + this.width - textWidth - padding.right
				textY = this.y + padding.top
			}
			CENTER_LEFT -> {
				textX = this.x + padding.left
				textY = centerY - textHeight / 2
			}
			CENTER -> {
				textX = centerX - textWidth / 2
				textY = centerY - textHeight / 2
			}
			CENTER_RIGHT -> {
				textX = this.x + this.width - textWidth - padding.right
				textY = centerY - textHeight / 2
			}
			BOTTOM_LEFT -> {
				textX = this.x + padding.left
				textY = this.y - textHeight - padding.bottom
			}
			BOTTOM_CENTER -> {
				textX = centerX - textWidth / 2
				textY = this.y - textHeight - padding.bottom
			}
			BOTTOM_RIGHT -> {
				textX = this.x + this.width - textWidth - padding.right
				textY = this.y - textHeight - padding.bottom
			}
		}
		val immediate = VertexConsumerProvider.immediate(Tessellator.getInstance().buffer)
		textRenderer.draw(
			renderText,
			textX.toFloat(),
			textY.toFloat(),
			textColor.rgba,
			shadow,
			matrices.peek().positionMatrix,
			immediate,
			false,
			0,
			LightmapTextureManager.MAX_LIGHT_COORDINATE,
			rightToLeft
		)
		immediate.draw()
	}

}

inline fun ParentElement.textLabel(
	text: String,
	width: Int = textRenderer.getWidth(text),
	height: Int = textRenderer.fontHeight,
	noinline onClick: (TextLabel.(Int) -> Boolean) = { true },
	align: Align = CENTER,
	shadow: Boolean = true,
	rightToLeft: Boolean = false,
	backgroundColor: Color<out Number> = Color4i.BLACK.apply { alpha = 0 },
	bordColor: Color<out Number> = Color4i.BLACK.apply { alpha = 0 },
	scope: (TextLabel.() -> Unit) = {},
): TextLabel = textLabel(text(text), width, height, onClick, align, shadow, rightToLeft, backgroundColor, bordColor, scope)


inline fun ParentElement.textLabel(
	text: Text,
	width: Int = textRenderer.getWidth(text),
	height: Int = textRenderer.fontHeight,
	noinline onClick: (TextLabel.(Int) -> Boolean) = { true },
	align: Align = CENTER,
	shadow: Boolean = true,
	rightToLeft: Boolean = false,
	backgroundColor: Color<out Number> = Color4i.BLACK.apply { alpha = 0 },
	bordColor: Color<out Number> = Color4i.BLACK.apply { alpha = 0 },
	scope: (TextLabel.() -> Unit) = {},
): TextLabel {
	val textLabel = TextLabel(text, width, height).apply {
		this.align = align
		this.onClick = { onClick.invoke(this, it) }
		this.shadow = shadow
		this.rightToLeft = rightToLeft
		this.backgroundColor = backgroundColor
		this.bordColor = bordColor
		this.parent = this@textLabel
		scope()
	}
	this.addElement(textLabel)
	return textLabel
}