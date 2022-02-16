package org.hiirosakura.cookie.config.options.impl

import com.google.gson.JsonElement
import org.hiirosakura.cookie.config.IConfigType
import org.hiirosakura.cookie.config.options.ConfigBase
import org.hiirosakura.cookie.config.options.ConfigType
import org.hiirosakura.cookie.config.options.IConfigColor
import org.hiirosakura.cookie.util.color.Color

/**
 *

 * 项目名 cookie

 * 包名 org.hiirosakura.cookie.config.options.impl

 * 文件名 ConfigColor

 * 创建时间 2022/2/16 17:59

 * @author forpleuvoir

 */
class ConfigColor(override val key: String, override val defaultValue: Color<out Number>) : ConfigBase<Color<out Number>>(), IConfigColor {

	override val type: IConfigType
		get() = ConfigType.COLOR

	override var configValue: Color<out Number> = Color.copy(defaultValue)

	override fun setValue(value: Color<out Number>) {
		val oldValue = this.configValue
		this.configValue = Color.copy(value)
		if (oldValue != this.configValue) {
			this.onChanged()
		}
	}

	override fun getValue(): Color<out Number> {
		return Color.copy(configValue)
	}

	override fun setFromJson(jsonElement: JsonElement): Boolean {
		configValue.setValueFromJsonElement(jsonElement)
		return true
	}

	override val asJsonElement: JsonElement
		get() = configValue.asJsonElement
}