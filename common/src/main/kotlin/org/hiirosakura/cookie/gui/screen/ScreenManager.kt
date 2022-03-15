package org.hiirosakura.cookie.gui.screen

import net.minecraft.client.option.KeyBinding
import net.minecraft.client.render.BufferRenderer
import org.hiirosakura.cookie.common.Tickable
import org.hiirosakura.cookie.common.mc
import org.hiirosakura.cookie.input.InputHandler
import java.util.function.Consumer

/**
 *

 * 项目名 cookie

 * 包名 org.hiirosakura.cookie.gui.screen

 * 文件名 ScreenManager

 * 创建时间 2022/2/19 18:25

 * @author forpleuvoir

 */
object ScreenManager : Tickable {

	var current: Screen? = null
		private set

	fun setCurrent(screen: Screen?) {
		current = screen
		BufferRenderer.unbindAll()
		current?.let {
			mc.mouse.unlockCursor()
			InputHandler.unPressAll()
			KeyBinding.unpressAll()
			it.initialize()
			mc.skipGameRender = false
			mc.updateWindowTitle()
			return
		}
		mc.soundManager.resumeAll()
		mc.mouse.lockCursor()
		mc.updateWindowTitle()
	}


	fun setCurrent(screen: Screen?, needInit: Boolean = true) {
		current = screen
		BufferRenderer.unbindAll()
		current?.let {
			mc.mouse.unlockCursor()
			InputHandler.unPressAll()
			KeyBinding.unpressAll()
			if (needInit)
				it.initialize()
			mc.skipGameRender = false
			mc.updateWindowTitle()
			return
		}
		mc.soundManager.resumeAll()
		mc.mouse.lockCursor()
		mc.updateWindowTitle()
	}

	/**
	 * 打开一个Screen
	 * @param scope [@kotlin.ExtensionFunctionType] Function1<ScreenManager, Screen>
	 */
	inline fun openScreen(scope: ScreenManager.() -> Screen) {
		setCurrent(scope(this))
	}

	@JvmStatic
	fun hasScreen(): Boolean = current != null

	@JvmStatic
	fun hasScreen(action: Consumer<Screen>) {
		current?.let { action.accept(it) }
	}

	override fun tick() {
		current?.tick()
	}
}