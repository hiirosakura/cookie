package org.hiirosakura.cookie.gui.screen

import net.minecraft.client.util.math.MatrixStack
import org.hiirosakura.cookie.common.Tickable
import org.hiirosakura.cookie.common.WIDGET_TEXTURE
import org.hiirosakura.cookie.common.textRenderer
import org.hiirosakura.cookie.gui.foundation.*
import org.hiirosakura.cookie.util.*
import org.hiirosakura.cookie.util.Direction.*
import org.hiirosakura.cookie.util.color.Color
import org.hiirosakura.cookie.util.color.Color4f
import org.hiirosakura.cookie.util.color.Color4i
import org.hiirosakura.cookie.util.math.I

/**
 *

 * 项目名 cookie

 * 包名 org.hiirosakura.cookie.gui.screen

 * 文件名 Tip

 * 创建时间 2022/3/31 22:10

 * @author forpleuvoir

 */
class TipRenderer(
	private val screen: Screen,
	private val tipColdDown: Int = 12,
) : Drawable, Tickable {

	private var tipColdDownCounter: Int = 0
		set(value) {
			field = value.coerceAtMost(tipColdDown)
		}

	override fun render(matrices: MatrixStack, delta: Number) {
		if (tipColdDownCounter < tipColdDown) return
		screen.hoveredTipElement()?.let { it ->
			it.tip()?.let { tips ->
				it.isEmptyTip().ifc { return }
				val bgCornerSize = 2
				val lineSpacing = 1.0f
				val padding = 2
				val margin = 2
				val lines = tips.wrapToLines(screen.width - (padding + bgCornerSize) * 2)
				val textWidth = lines.maxWidth()
				val textHeight = lines.size * textRenderer.fontHeight
				val width = textWidth + bgCornerSize * 2 + padding * 2
				val height = textHeight + bgCornerSize * 2 + padding * 2 + lineSpacing * (lines.size - 1)
				val canPlace: (Direction) -> Boolean = { dir ->
					when (dir) {
						Left  -> it.left.I - (width + 3 + margin) > 0
						Right -> it.right.I + (width + 3 + margin) < screen.width
						Up    -> it.top.I - (height + 3 + margin) > 0
						Down  -> it.bottom.I + (height + 3 + margin) < screen.height
					}
				}
				val direction: Direction =
					if (it.tipDirection() != null) it.tipDirection()!! else if (canPlace(Up)) Up else if (canPlace(Right)) Right else if (canPlace(
							Down)
					) Down else Left
				setShaderTexture(WIDGET_TEXTURE)
				enableBlend()
				defaultBlendFunc()
				enableDepthTest()
				val textColor: Color<out Number> = Color4f.BLACK
				val color: Color<out Number> = Color4i(255, 182, 185)
				val shadowColor: Color<out Number> = Color4f.BLACK.alpha(0.3f)
				//render
				when (direction) {
					Left  -> {
						//draw shadow
						setShaderColor(shadowColor)
						draw9Texture(
							matrices,
							it.left.I - (width + 3 + margin),
							((it.bottom.I - (it.height / 2)) - height / 2).clamp(0, screen.height - height) + 2,
							bgCornerSize,
							width,
							height,
							32,
							48,
							32,
							32
						)
						drawTexture(matrices, it.left.I - (margin + 4), it.bottom.I - (it.height / 2) - 3 + 2, 4, 7, 67, 48, 4, 7)
						setShaderColor(color)
						//draw background
						draw9Texture(
							matrices,
							it.left.I - (width + 3 + margin),
							((it.bottom.I - (it.height / 2)) - height / 2).clamp(0, screen.height - height),
							bgCornerSize,
							width,
							height,
							32,
							48,
							32,
							32
						)
						//draw arrow
						drawTexture(matrices, it.left.I - (margin + 4), it.bottom.I - (it.height / 2) - 3, 4, 7, 67, 48, 4, 7)
						drawTextLines(
							matrices,
							lines,
							it.left.I - (width + 3 + margin) + padding + bgCornerSize,
							((it.bottom.I - (it.height / 2)) - height / 2).clamp(0, screen.height - height) + padding + bgCornerSize,
							color = textColor,
							lineSpacing = lineSpacing
						)
					}
					Right -> {
						setShaderColor(shadowColor)
						draw9Texture(
							matrices,
							it.right.I + margin + 3,
							((it.bottom.I - (it.height / 2)) - height / 2).clamp(0, screen.height - height) + 2,
							bgCornerSize,
							width,
							height,
							32,
							48,
							32,
							32
						)
						drawTexture(matrices, it.right.I + margin, it.bottom.I - (it.height / 2) - 3 + 2, 4, 7, 64, 48, 4, 7)
						setShaderColor(color)
						draw9Texture(
							matrices, it.right.I + margin + 3, ((it.bottom.I - (it.height / 2)) - height / 2).clamp(0, screen.height - height),
							bgCornerSize, width, height, 32, 48, 32, 32
						)
						drawTexture(matrices, it.right.I + margin, it.bottom.I - (it.height / 2) - 3, 4, 7, 64, 48, 4, 7)
						drawTextLines(
							matrices,
							lines,
							it.right.I + margin + 3 + padding + bgCornerSize,
							((it.bottom.I - (it.height / 2)) - height / 2).clamp(0, screen.height - height) + padding + bgCornerSize,
							color = textColor,
							lineSpacing = lineSpacing
						)
					}
					Up    -> {
						setShaderColor(shadowColor)
						draw9Texture(
							matrices,
							(it.right.I - (it.width / 2) - (width / 2)).clamp(0, screen.width - width),
							it.top.I - (height + 3 + margin) + 2,
							bgCornerSize,
							width,
							height,
							32,
							48,
							32,
							32
						)
						drawTexture(matrices, (it.right.I - (it.width / 2) - 3), it.top.I - (margin + 4) + 2 + 1, 7, 3, 64, 52, 7, 3)
						setShaderColor(color)
						draw9Texture(
							matrices,
							(it.right.I - (it.width / 2) - (width / 2)).clamp(0, screen.width - width),
							it.top.I - (height + 3 + margin),
							bgCornerSize,
							width,
							height,
							32,
							48,
							32,
							32
						)
						drawTexture(matrices, (it.right.I - (it.width / 2) - 3), it.top.I - (margin + 4), 7, 4, 64, 51, 7, 4)
						drawTextLines(
							matrices,
							lines,
							(it.right.I - (it.width / 2) - (width / 2)).clamp(0, screen.width - width) + padding + bgCornerSize,
							it.top.I - (height + 3 + margin) + padding + bgCornerSize,
							color = textColor,
							lineSpacing = lineSpacing
						)
					}
					Down  -> {
						setShaderColor(shadowColor)
						draw9Texture(
							matrices, (it.right.I - (it.width / 2) - (width / 2)).clamp(0, screen.width - width), it.bottom.I + (3 + margin) + 2,
							bgCornerSize, width, height, 32, 48, 32, 32
						)
						drawTexture(matrices, (it.right.I - (it.width / 2) - 3), it.bottom.I + margin + 2, 7, 4, 64, 48, 7, 4)
						setShaderColor(color)
						draw9Texture(
							matrices, (it.right.I - (it.width / 2) - (width / 2)).clamp(0, screen.width - width), it.bottom.I + (3 + margin),
							bgCornerSize, width, height, 32, 48, 32, 32
						)
						drawTexture(matrices, (it.right.I - (it.width / 2) - 3), it.bottom.I + margin, 7, 4, 64, 48, 7, 4)
						drawTextLines(
							matrices,
							lines,
							(it.right.I - (it.width / 2) - (width / 2)).clamp(0, screen.width - width) + padding + bgCornerSize,
							it.bottom.I + (3 + margin) + padding + bgCornerSize,
							color = textColor,
							lineSpacing = lineSpacing
						)
					}
				}
				setShaderColor(Color4f.WHITE)
				disableBlend()
			}
		}
	}

	override fun tick() {
		if (screen.hoveredTipElement() != null) {
			tipColdDownCounter++
		} else {
			tipColdDownCounter = 0
		}
	}

}