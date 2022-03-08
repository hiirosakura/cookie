package org.hiirosakura.cookie.input

import org.hiirosakura.cookie.common.Tickable
import org.lwjgl.glfw.GLFW
import java.util.*

/**
 *

 * 项目名 cookie

 * 包名 org.hiirosakura.cookie.input

 * 文件名 InputHandler

 * 创建时间 2022/2/17 0:29

 * @author forpleuvoir

 */

object InputHandler : Tickable {

	/**
	 * 当前已按下的KeyCode
	 */
	private val keys: LinkedHashSet<Int> = LinkedHashSet()

	/**
	 * 所有已注册的KeyBind
	 */
	private val keyBinds: LinkedList<KeyBind> = LinkedList()

	@JvmStatic
	fun register(vararg keyBinds: KeyBind) {
		this.keyBinds.addAll(keyBinds)
		keyBinds.forEach {
			it.setOnChangedCallback {
				conflictList.clear()
				conflictList.addAll(checkConflict(this))
				isConflicted = conflictList.isNotEmpty()
			}
		}
	}

	@JvmStatic
	fun unregister(vararg keyBinds: KeyBind) {
		this.keyBinds.removeAll(keyBinds.toSet())
		keyBinds.forEach {
			it.onChangedCallback.clear()
		}
	}

	/**
	 * 检查按键冲突
	 * @param keyBind KeyBind
	 * @return List<KeyBind>
	 */
	private fun checkConflict(keyBind: KeyBind): List<KeyBind> {
		val conflictList: MutableList<KeyBind> = ArrayList()
		keyBinds.forEach {
			if (it.keys == keyBind.keys && it != keyBind) {
				conflictList.add(it)
			}
		}
		return conflictList
	}

	/**
	 * 按键按下
	 * @param keyCode Int
	 * @return Boolean 是否取消之后的操作
	 */
	@JvmStatic
	fun keyPress(keyCode: Int): Boolean {
		if (keyCode != GLFW.GLFW_KEY_UNKNOWN)
			if (keys.add(keyCode))
				keyBinds.forEach { if (it.update(keys)) return true }
		return false
	}

	/**
	 * 按键释放
	 * @param keyCode Int
	 * @return Boolean 是否取消之后的操作
	 */
	@JvmStatic
	fun keyRelease(keyCode: Int): Boolean {
		if (keyCode != GLFW.GLFW_KEY_UNKNOWN)
			if (keys.remove(keyCode))
				keyBinds.forEach { if (it.update(keys)) return true }
		return false
	}

	override fun tick() {
		if (keyBinds.isNotEmpty()) keyBinds.forEach { it.tick() }
	}

	fun unPressAll() {
		keys.clear()
		keyBinds.forEach { it.update(keys) }
	}

}