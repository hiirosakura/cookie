package org.hiirosakura.cookie.config.options.impl

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import org.hiirosakura.cookie.config.Config
import org.hiirosakura.cookie.config.IConfigType
import org.hiirosakura.cookie.config.options.ConfigBase
import org.hiirosakura.cookie.config.options.ConfigType
import org.hiirosakura.cookie.config.options.IConfigGroup
import org.hiirosakura.cookie.util.ifc
import java.util.*

/**
 *

 * 项目名 cookie

 * 包名 org.hiirosakura.cookie.config.options.impl

 * 文件名 ConfigGroup

 * 创建时间 2022/2/16 18:17

 * @author forpleuvoir

 */
class ConfigGroup(override val key: String, override val defaultValue: List<Config<*>>) : ConfigBase<List<Config<*>>>(), IConfigGroup {

	override val type: IConfigType
		get() = ConfigType.GROUP

	override var configValue: List<Config<*>> = LinkedList(defaultValue)

	override fun initialize() {
		configValue.forEach { it.initialize() }
	}

	override val isDefault: Boolean
		get() {
			this.configValue.forEach {
				if (!it.isDefault) return false
			}
			return true
		}

	override fun resetDefault() {
		this.configValue.forEach {
			it.resetDefault()
		}
	}

	override fun setValue(value: List<Config<*>>) {
		//can't set config group
	}

	override fun matched(regex: Regex): Boolean {
		getValue().forEach {
			(it matched regex).ifc { return true }
		}
		return super.matched(regex)
	}

	override fun getConfigFromKey(key: String): Config<*>? {
		return getValue().find { it.key == key }
	}

	override fun getKeys(): Set<String> {
		val keys = HashSet<String>()
		this.getValue().forEach {
			keys.add(it.key)
		}
		return keys
	}

	override fun setOnChangedCallback(callback: Config<List<Config<*>>>.() -> Unit) {
		this.getValue().forEach {
			it.setOnChangedCallback { callback() }
		}
	}

	override fun setFromJson(jsonElement: JsonElement): Boolean {
		if (!jsonElement.isJsonObject) return false
		val jsonObject = jsonElement.asJsonObject
		this.configValue.forEach {
			it.setValueFromJsonElement(jsonObject[it.key])
		}
		return true
	}

	override val asJsonElement: JsonElement
		get() = JsonObject().apply {
			this@ConfigGroup.configValue.forEach {
				this.add(it.key, it.asJsonElement)
			}
		}
}