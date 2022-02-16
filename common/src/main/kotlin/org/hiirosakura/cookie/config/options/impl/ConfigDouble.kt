package org.hiirosakura.cookie.config.options.impl

import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import org.hiirosakura.cookie.config.IConfigType
import org.hiirosakura.cookie.config.options.ConfigBase
import org.hiirosakura.cookie.config.options.ConfigType
import org.hiirosakura.cookie.config.options.IConfigDouble

/**
 *

 * 项目名 cookie

 * 包名 org.hiirosakura.cookie.config.options.impl

 * 文件名 ConfigDouble

 * 创建时间 2022/2/16 16:59

 * @author forpleuvoir

 */
class ConfigDouble(
	override val key: String,
	override val defaultValue: Double,
	override val minValue: Double = Double.MIN_VALUE,
	override val maxValue: Double = Double.MAX_VALUE
) : ConfigBase<Double>(), IConfigDouble {

	override val type: IConfigType
		get() = ConfigType.DOUBLE

	override var configValue: Double = fixValue(defaultValue)

	override fun setValue(value: Double) {
		super.setValue(fixValue(value))
	}

	override fun setFromJson(jsonElement: JsonElement): Boolean {
		if (!jsonElement.isJsonPrimitive) return false
		this.configValue = jsonElement.asDouble
		return true
	}

	override val asJsonElement: JsonElement
		get() = JsonPrimitive(getValue())
}