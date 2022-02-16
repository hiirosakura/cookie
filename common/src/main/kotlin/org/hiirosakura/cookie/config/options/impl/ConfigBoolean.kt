package org.hiirosakura.cookie.config.options.impl

import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import org.hiirosakura.cookie.config.IConfigType
import org.hiirosakura.cookie.config.options.ConfigBase
import org.hiirosakura.cookie.config.options.ConfigType
import org.hiirosakura.cookie.config.options.IConfigBoolean

/**
 *

 * 项目名 cookie

 * 包名 org.hiirosakura.cookie.config.options.impl

 * 文件名 ConfigBoolean

 * 创建时间 2022/2/16 16:15

 * @author forpleuvoir

 */
class ConfigBoolean(
	override val key: String,
	override val defaultValue: Boolean = false
) : ConfigBase<Boolean>(), IConfigBoolean {

	override val type: IConfigType
		get() = ConfigType.BOOLEAN

	override var configValue: Boolean = defaultValue

	override fun setFromJson(jsonElement: JsonElement): Boolean {
		if (!jsonElement.isJsonPrimitive) return false
		this.configValue = jsonElement.asBoolean
		return true
	}

	override val asJsonElement: JsonElement
		get() = JsonPrimitive(getValue())


}