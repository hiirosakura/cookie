package org.hiirosakura.cookie.gui.screen

import net.minecraft.client.Mouse
import org.hiirosakura.cookie.common.mc
import org.hiirosakura.cookie.gui.foundation.ParentElement
import org.hiirosakura.cookie.util.ifc
import org.lwjgl.glfw.GLFW

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

	val remembers: MutableMap<String, Any?>

	override var parent: ParentElement?
		get() = this.parentScreen
		set(value) {}

	/**
	 * 打开Screen时是否需要暂停游戏
	 */
	val pauseScreen: Boolean get() = false

	/**
	 * 关闭当前Screen
	 */
	fun close() = onClose().ifc { ScreenManager.current = this.parentScreen }

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