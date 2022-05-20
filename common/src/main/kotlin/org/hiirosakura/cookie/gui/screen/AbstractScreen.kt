package org.hiirosakura.cookie.gui.screen

import net.minecraft.client.util.math.MatrixStack
import org.hiirosakura.cookie.common.mc
import org.hiirosakura.cookie.gui.foundation.AbstractParentElement
import org.hiirosakura.cookie.gui.foundation.Element
import org.hiirosakura.cookie.gui.foundation.ParentElement
import org.hiirosakura.cookie.gui.foundation.drawRect
import org.hiirosakura.cookie.util.color.Color
import org.hiirosakura.cookie.util.color.Color4f
import org.hiirosakura.cookie.util.math.Vector3d
import java.util.*

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

	override val tipRenderer: TipRenderer = TipRenderer(this)

	override fun tick() {
		super<Screen>.tick()
		tipRenderer.tick()
	}

	override fun renderTip(matrices: MatrixStack, delta: Number) {
		tipRenderer.render(matrices, delta)
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