package org.hiirosakura.cookie.config.options.impl

import com.google.gson.JsonArray
import com.google.gson.JsonElement
import org.hiirosakura.cookie.config.IConfigType
import org.hiirosakura.cookie.config.options.ConfigBase
import org.hiirosakura.cookie.config.options.ConfigType
import org.hiirosakura.cookie.config.options.IConfigStrings

/**
 *

 * 项目名 cookie

 * 包名 org.hiirosakura.cookie.config.options.impl

 * 文件名 ConfigStrings

 * 创建时间 2022/2/16 19:21

 * @author forpleuvoir

 */
class ConfigStrings(
	override val key: String,
	override val defaultValue: MutableList<String>
) : ConfigBase<MutableList<String>>(), IConfigStrings {

	override val type: IConfigType
		get() = ConfigType.STRINGS

	override var configValue: MutableList<String> = ArrayList(defaultValue)

	override fun add(element: String) {
		if (configValue.add(element)) {
			onChanged()
		}
	}

	override fun get(index: Int): String {
		return configValue[index]
	}

	override fun set(index: Int, element: String) {
		if (this.configValue[index] != element) {
			this.configValue[index] = element
			this.onChanged()
		}
	}

	override fun remove(index: Int) {
		if (index >= 0 && index < this.configValue.size) {
			this.configValue.removeAt(index)
			this.onChanged()
		}
	}

	override fun remove(element: String) {
		if (this.configValue.remove(element)) {
			this.configValue
		}
	}

	override fun setValue(value: MutableList<String>) {
		if (this.configValue != value) {
			this.configValue.clear()
			this.configValue.addAll(value)
			this.onChangedCallback
		}
	}

	override val asJsonElement: JsonElement
		get() = JsonArray().apply {
			configValue.forEach(::add)
		}

	override fun setFromJson(jsonElement: JsonElement): Boolean {
		if (!jsonElement.isJsonArray) return false
		val jsonArray = jsonElement.asJsonArray
		configValue.clear()
		jsonArray.forEach {
			configValue.add(it.asString)
		}
		return true
	}

}