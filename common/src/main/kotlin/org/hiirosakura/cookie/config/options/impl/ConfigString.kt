package org.hiirosakura.cookie.config.options.impl

import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import org.hiirosakura.cookie.config.IConfigType
import org.hiirosakura.cookie.config.options.ConfigBase
import org.hiirosakura.cookie.config.options.ConfigType
import org.hiirosakura.cookie.config.options.IConfigString

/**
 *

 * 项目名 cookie

 * 包名 org.hiirosakura.cookie.config.options.impl

 * 文件名 ConfigString

 * 创建时间 2022/2/16 19:14

 * @author forpleuvoir

 */
class ConfigString(override val key: String, override val defaultValue: String) : ConfigBase<String>(), IConfigString {

	override val type: IConfigType
		get() = ConfigType.STRING

	override var configValue: String = defaultValue

	override fun setFromJson(jsonElement: JsonElement): Boolean {
		if (!jsonElement.isJsonPrimitive) return false
		this.configValue = jsonElement.asString
		return true
	}

	override val asJsonElement: JsonElement
		get() = JsonPrimitive(configValue)
}