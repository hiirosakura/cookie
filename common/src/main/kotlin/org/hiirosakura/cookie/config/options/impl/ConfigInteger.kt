package org.hiirosakura.cookie.config.options.impl

import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import org.hiirosakura.cookie.config.IConfigType
import org.hiirosakura.cookie.config.options.ConfigBase
import org.hiirosakura.cookie.config.options.ConfigType
import org.hiirosakura.cookie.config.options.IConfigInteger

/**
 *

 * 项目名 cookie

 * 包名 org.hiirosakura.cookie.config.options.impl

 * 文件名 ConfigInteger

 * 创建时间 2022/2/16 17:42

 * @author forpleuvoir

 */
class ConfigInteger(
	override val key: String,
	override val defaultValue: Int,
	override val minValue: Int = Int.MIN_VALUE,
	override val maxValue: Int = Int.MAX_VALUE
) : ConfigBase<Int>(), IConfigInteger {

	override val type: IConfigType
		get() = ConfigType.INTEGER

	override var configValue: Int = fixValue(defaultValue)

	override fun setValue(value: Int) {
		super.setValue(fixValue(value))
	}

	override fun setFromJson(jsonElement: JsonElement): Boolean {
		if (!jsonElement.isJsonPrimitive) return false
		this.configValue = jsonElement.asInt
		return true
	}

	override val asJsonElement: JsonElement
		get() = JsonPrimitive(getValue())

}