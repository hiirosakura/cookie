package org.hiirosakura.cookie.gui.screen

import net.minecraft.client.Mouse
import net.minecraft.client.util.math.MatrixStack
import org.hiirosakura.cookie.common.mc
import org.hiirosakura.cookie.gui.foundation.ParentElement
import org.hiirosakura.cookie.util.ifc
import org.lwjgl.glfw.GLFW
import kotlin.reflect.KProperty

/**
 *

 * 项目名 cookie

 * 包名 org.hiirosakura.cookie.gui.screen

 * 文件名 Screen

 * 创建时间 2022/2/19 18:27

 * @author forpleuvoir

 */
interface Screen : ParentElement {

	val mouse: Mouse get() = mc.mouse

	var parentScreen: Screen?

	val rememberValue: MutableMap<String, Any?>

	val rememberProperties: MutableMap<String, MutableSet<KProperty<*>>>

	fun pushPreInitAction(action: () -> Unit)

	fun getPreInitActions(): List<() -> Unit>

	fun pushInitializedAction(action: () -> Unit)

	fun getInitializedAction(): List<() -> Unit>

	override var parent: ParentElement?
		get() = this.parentScreen
		set(value) {}

	/**
	 * 打开Screen时是否需要暂停游戏
	 */
	val pauseScreen: Boolean get() = false

	/**
	 * 刷新屏幕
	 */
	fun refresh()

	/**
	 * 关闭当前Screen
	 */
	fun close() = onClose().ifc { ScreenManager.setCurrent(this.parentScreen) }

	fun renderTip(matrices: MatrixStack, delta: Number)

	fun resize(width: Number, height: Number) {}

	/**
	 * 当Screen关闭时 调用
	 *
	 * 返回值 是否需要关闭
	 */
	val onClose: Screen.() -> Boolean
		get() = { true }

	val shouldCloseOnEsc: Boolean get() = true

	override fun keyPress(keyCode: Int, modifiers: Int): Boolean {
		if (keyCode == GLFW.GLFW_KEY_ESCAPE && shouldCloseOnEsc) {
			close()
			return true
		}
		return super.keyPress(keyCode, modifiers)
	}
}