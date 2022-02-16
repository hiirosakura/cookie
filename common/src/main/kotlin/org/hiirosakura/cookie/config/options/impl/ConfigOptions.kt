package org.hiirosakura.cookie.config.options.impl

import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import org.hiirosakura.cookie.common.Option
import org.hiirosakura.cookie.config.IConfigType
import org.hiirosakura.cookie.config.options.ConfigBase
import org.hiirosakura.cookie.config.options.ConfigType
import org.hiirosakura.cookie.config.options.IConfigOptions

/**
 *

 * 项目名 cookie

 * 包名 org.hiirosakura.cookie.config.options.impl

 * 文件名 ConfigOptions

 * 创建时间 2022/2/16 20:07

 * @author forpleuvoir

 */
class ConfigOptions(
	override val key: String,
	override val defaultValue: Option
) : ConfigBase<Option>(), IConfigOptions {

	override val type: IConfigType
		get() = ConfigType.OPTIONS

	override var configValue: Option = defaultValue

	override fun matched(regex: Regex): Boolean {
		return if (regex.run {
				containsMatchIn(getValue().displayKey.string)
						|| containsMatchIn(getValue().remark.string)
						|| containsMatchIn(getValue().key)
			}) true
		else super.matched(regex)
	}

	override val asJsonElement: JsonElement
		get() = JsonPrimitive(configValue.key)

	override fun setFromJson(jsonElement: JsonElement): Boolean {
		if (!jsonElement.isJsonPrimitive) return false
		this.configValue = defaultValue.fromKey(jsonElement.asString)
		return true
	}
}