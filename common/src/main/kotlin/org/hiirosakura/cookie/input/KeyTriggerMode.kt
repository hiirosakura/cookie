package org.hiirosakura.cookie.input

import net.minecraft.text.Text
import org.hiirosakura.cookie.common.Option
import org.hiirosakura.cookie.util.tText

/**
 * 按键触发模式

 * 项目名 cookie

 * 包名 org.hiirosakura.cookie.input

 * 文件名 KeyTriggerMode

 * 创建时间 2022/2/16 21:31

 * @author forpleuvoir

 */
enum class KeyTriggerMode(override val key: String) : Option {

	OnPress("onPress"),
	OnLongPress("onLongPress"),
	OnRelease("onRelease"),
	Pressed("press"),
	BOTH("both");

	override val displayKey: Text
		get() = "cookie.key_bind.trigger_mode.${key}".tText

	override val allOption: List<KeyTriggerMode>
		get() = values().toList()

	override fun fromKey(key: String): KeyTriggerMode {
		allOption.forEach {
			if (it.key == key) return it
		}
		return OnRelease
	}

}