package org.hiirosakura.cookie.config.options.impl

import com.google.common.collect.ImmutableMap
import com.google.gson.JsonElement
import org.hiirosakura.cookie.config.IConfigType
import org.hiirosakura.cookie.config.options.ConfigBase
import org.hiirosakura.cookie.config.options.ConfigType
import org.hiirosakura.cookie.config.options.IConfigMap
import org.hiirosakura.cookie.util.JsonUtil.toJsonObject

/**
 *

 * 项目名 cookie

 * 包名 org.hiirosakura.cookie.config.options.impl

 * 文件名 ConfigMap

 * 创建时间 2022/2/17 1:49

 * @author forpleuvoir

 */
class ConfigMap(
	override val key: String,
	override val defaultValue: MutableMap<String, String>
) : ConfigBase<MutableMap<String, String>>(), IConfigMap<String, String> {

	override val type: IConfigType
		get() = ConfigType.MAP

	override var configValue: MutableMap<String, String> = LinkedHashMap(defaultValue)

	override fun matched(regex: Regex): Boolean {
		return if (regex.run {
				getValue().forEach {
					if (containsMatchIn(it.key) || containsMatchIn(it.value)) return@run true
				}
				false
			}) true
		else super.matched(regex)
	}

	override fun setValue(value: MutableMap<String, String>) {
		if (this.configValue != value) {
			this.configValue.clear()
			this.configValue.putAll(value)
			this.onChanged()
		}
	}

	override fun getValue(): MutableMap<String, String> {
		return ImmutableMap.copyOf(configValue)
	}

	override fun set(key: String, value: String) {
		val oldValue: String? = this.configValue[key]
		this.configValue[key] = value
		if (oldValue != value) {
			this.onChanged()
		}
	}

	override fun get(key: String): String? = configValue[key]

	override fun remove(key: String) {
		if (key.contains(key)) {
			this.configValue.remove(key)
			this.onChanged()
		}
	}

	override fun rename(origin: String, current: String) {
		if (this.configValue.containsKey(origin)) {
			val value = this.configValue[origin]
			value?.let {
				this.configValue.remove(origin)
				this.configValue[current] = it
				this.onChanged()
			}
		}
	}

	override fun rest(originKey: String, currentKey: String, value: String) {
		if (this.configValue.containsKey(originKey)) {
			if (originKey != currentKey) rename(originKey, currentKey)
			this.configValue[currentKey] = value
			this.onChanged()
		}
	}

	override val asJsonElement: JsonElement
		get() = getValue().toJsonObject()

	override fun setFromJson(jsonElement: JsonElement): Boolean {
		if (!jsonElement.isJsonObject) return false
		configValue.clear()
		val jsonObject = jsonElement.asJsonObject
		jsonObject.entrySet().forEach {
			configValue[it.key] = it.value.asString
		}
		return true
	}
}