package org.hiirosakura.cookie.input

import net.minecraft.text.Text
import org.hiirosakura.cookie.common.Option
import org.hiirosakura.cookie.common.mc
import org.hiirosakura.cookie.gui.screen.ScreenManager
import org.hiirosakura.cookie.util.tText

/**
 * 按键按下事件 环境

 * 项目名 cookie

 * 包名 org.hiirosakura.cookie.input

 * 文件名 KeyEnvironment

 * 创建时间 2021/12/15 22:08

 * @author forpleuvoir

 */
enum class KeyEnvironment(override val key: String) : Option {

	InGame("in_game"),
	InScreen("in_screen"),
	Both("both");

	override val displayKey: Text
		get() = "cookie.key_bind.environment.${key}".tText

	fun envMatch(): Boolean {
		if (this == Both) return true
		return this == currentEnv()
	}

	inline fun onEnvMatch(callback: () -> Unit) {
		if (envMatch()) callback.invoke()
	}

	override fun fromKey(key: String): KeyEnvironment {
		allOption.forEach {
			if (it.key == key) return it
		}
		return InGame
	}

	override val allOption: List<KeyEnvironment>
		get() = values().toList()

}

fun currentEnv(): KeyEnvironment {
	return if (mc.currentScreen == null && !ScreenManager.hasScreen()) KeyEnvironment.InGame else KeyEnvironment.InScreen
}
