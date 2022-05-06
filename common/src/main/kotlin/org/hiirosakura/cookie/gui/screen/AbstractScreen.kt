package org.hiirosakura.cookie.gui.screen

import net.minecraft.client.util.math.MatrixStack
import org.hiirosakura.cookie.common.WIDGET_TEXTURE
import org.hiirosakura.cookie.common.mc
import org.hiirosakura.cookie.common.textRenderer
import org.hiirosakura.cookie.gui.foundation.*
import org.hiirosakura.cookie.util.*
import org.hiirosakura.cookie.util.Direction.*
import org.hiirosakura.cookie.util.color.Color
import org.hiirosakura.cookie.util.color.Color4f
import org.hiirosakura.cookie.util.color.Color4i
import org.hiirosakura.cookie.util.math.I
import org.hiirosakura.cookie.util.math.Vector3d
import java.util.*
import kotlin.reflect.KProperty

/**
 *

 * 项目名 cookie

 * 包名 org.hiirosakura.cookie.gui.screen

 * 文件名 AbstractScreen

 * 创建时间 2022/2/20 15:36

 * @author forpleuvoir

 */
abstract class AbstractScreen : AbstractParentElement(), Screen {

	override val rememberValue: MutableMap<String, Any?> = HashMap()

	override val rememberProperties: MutableMap<String, MutableSet<KProperty<*>>> = HashMap()

	override var visible: Boolean = true

	final override var width: Int = mc.window.scaledWidth
		set(value) {
			field = value
			resize(field, height)
		}
	final override var height: Int = mc.window.scaledHeight
		set(value) {
			field = value
			resize(width, field)
		}

	override var dragging: Boolean = false

	override var focused: Element? = null

	override var parentScreen: Screen? = null

	override var parent: ParentElement? = super<Screen>.parent

	override val position: Vector3d = Vector3d()

	var backgroundColor: Color<out Number> = Color4f.BLACK.alpha(0.5f)

	internal val preInitActions = LinkedList<() -> Unit>()
	override fun pushPreInitAction(action: () -> Unit) {
		preInitActions.push(action)
	}

	override fun getPreInitActions(): List<() -> Unit> {
		return preInitActions
	}

	internal val initializedAction = LinkedList<() -> Unit>()

	override fun pushInitializedAction(action: () -> Unit) {
		initializedAction.push(action)
	}

	override fun getInitializedAction(): List<() -> Unit> {
		return initializedAction
	}


	override fun tick() {
		super<Screen>.tick()
		if (hoveredTipElement() != null) {
			tipColdDownCounter++
		} else {
			tipColdDownCounter = 0
		}
	}

	/**
	 * 鼠标悬浮元素多久才会渲染tip
	 */
	var tipColdDown: Int = 12

	private var tipColdDownCounter: Int = 0
		set(value) {
			field = value.coerceAtMost(tipColdDown)
		}

	override fun renderTip(matrices: MatrixStack, delta: Number) {
		if (tipColdDownCounter < tipColdDown) return
		hoveredTipElement()?.let { it ->
			it.tip()?.let { tips ->
				it.isEmptyTip().ifc { return }
				val bgCornerSize = 2
				val lineSpacing = 1.0f
				val padding = 2
				val margin = 2
				val warpToLines = tips.wrapToLines(this.width - (padding + bgCornerSize) * 2)
				val textWidth = warpToLines.maxWidth()
				val textHeight = warpToLines.size * textRenderer.fontHeight
				val width = textWidth + bgCornerSize * 2 + padding * 2
				val height = textHeight + bgCornerSize * 2 + padding * 2 + lineSpacing * (warpToLines.size - 1)
				val canPlace: (Direction) -> Boolean = { dir ->
					when (dir) {
						Left  -> it.left.I - (width + 3 + margin) > 0
						Right -> it.right.I + (width + 3 + margin) < this.width
						Up    -> it.top.I - (height + 3 + margin) > 0
						Down  -> it.bottom.I + (height + 3 + margin) < this.height
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
							((it.bottom.I - (it.height / 2)) - height / 2).clamp(0, this.height - height) + 2,
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
							((it.bottom.I - (it.height / 2)) - height / 2).clamp(0, this.height - height),
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
							warpToLines,
							it.left.I - (width + 3 + margin) + padding + bgCornerSize,
							((it.bottom.I - (it.height / 2)) - height / 2).clamp(0, this.height - height) + padding + bgCornerSize,
							color = textColor,
							lineSpacing = lineSpacing
						)
					}
					Right -> {
						setShaderColor(shadowColor)
						draw9Texture(
							matrices, it.right.I + margin + 3, ((it.bottom.I - (it.height / 2)) - height / 2).clamp(0, this.height - height) + 2,
							bgCornerSize, width, height, 32, 48, 32, 32
						)
						drawTexture(matrices, it.right.I + margin, it.bottom.I - (it.height / 2) - 3 + 2, 4, 7, 64, 48, 4, 7)
						setShaderColor(color)
						draw9Texture(
							matrices, it.right.I + margin + 3, ((it.bottom.I - (it.height / 2)) - height / 2).clamp(0, this.height - height),
							bgCornerSize, width, height, 32, 48, 32, 32
						)
						drawTexture(matrices, it.right.I + margin, it.bottom.I - (it.height / 2) - 3, 4, 7, 64, 48, 4, 7)
						drawTextLines(
							matrices,
							warpToLines,
							it.right.I + margin + 3 + padding + bgCornerSize,
							((it.bottom.I - (it.height / 2)) - height / 2).clamp(0, this.height - height) + padding + bgCornerSize,
							color = textColor,
							lineSpacing = lineSpacing
						)
					}
					Up    -> {
						setShaderColor(shadowColor)
						draw9Texture(
							matrices,
							(it.right.I - (it.width / 2) - (width / 2)).clamp(0, this.width - width),
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
							matrices, (it.right.I - (it.width / 2) - (width / 2)).clamp(0, this.width - width), it.top.I - (height + 3 + margin),
							bgCornerSize, width, height, 32, 48, 32, 32
						)
						drawTexture(matrices, (it.right.I - (it.width / 2) - 3), it.top.I - (margin + 4), 7, 4, 64, 51, 7, 4)
						drawTextLines(
							matrices,
							warpToLines,
							(it.right.I - (it.width / 2) - (width / 2)).clamp(0, this.width - width) + padding + bgCornerSize,
							it.top.I - (height + 3 + margin) + padding + bgCornerSize,
							color = textColor,
							lineSpacing = lineSpacing
						)
					}
					Down  -> {
						setShaderColor(shadowColor)
						draw9Texture(
							matrices, (it.right.I - (it.width / 2) - (width / 2)).clamp(0, this.width - width), it.bottom.I + (3 + margin) + 2,
							bgCornerSize, width, height, 32, 48, 32, 32
						)
						drawTexture(matrices, (it.right.I - (it.width / 2) - 3), it.bottom.I + margin + 2, 7, 4, 64, 48, 7, 4)
						setShaderColor(color)
						draw9Texture(
							matrices, (it.right.I - (it.width / 2) - (width / 2)).clamp(0, this.width - width), it.bottom.I + (3 + margin),
							bgCornerSize, width, height, 32, 48, 32, 32
						)
						drawTexture(matrices, (it.right.I - (it.width / 2) - 3), it.bottom.I + margin, 7, 4, 64, 48, 7, 4)
						drawTextLines(
							matrices,
							warpToLines,
							(it.right.I - (it.width / 2) - (width / 2)).clamp(0, this.width - width) + padding + bgCornerSize,
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

	override fun render(matrices: MatrixStack, delta: Number) {
		renderBackground(matrices, delta)
		super<AbstractParentElement>.render(matrices, delta)
		renderTip(matrices, delta)
	}

	protected open fun renderBackground(matrixStack: MatrixStack, delta: Number) {
		drawRect(matrixStack, this.x, this.y, this.width, this.height, backgroundColor)
	}


}

/**
 *
 * 尽量和使用此方法创建screen而不是继承[AbstractScreen]
 * @receiver ScreenManager
 * @param screenScope [@kotlin.ExtensionFunctionType] Function1<AbstractScreen, Unit>
 * @return Screen
 */
fun ScreenManager.screen(screenScope: AbstractScreen.() -> Unit): Screen {
	val screen = object : AbstractScreen() {
		override fun initialize() {
			children.clear()
			screenScope()
			getInitializedAction().forEach { it() }
			preInitActions.clear()
			initializedAction.clear()
		}

		override fun refresh() {
			getPreInitActions().forEach { it() }
			initialize()
		}
	}
	return screen
}