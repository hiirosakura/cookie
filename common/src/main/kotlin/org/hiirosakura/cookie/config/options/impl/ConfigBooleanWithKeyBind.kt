package org.hiirosakura.cookie.config.options.impl

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import org.hiirosakura.cookie.config.IConfigType
import org.hiirosakura.cookie.config.options.ConfigBase
import org.hiirosakura.cookie.config.options.ConfigType
import org.hiirosakura.cookie.config.options.IConfigBooleanWithKeyBind
import org.hiirosakura.cookie.input.KeyBind

/**
 *

 * 项目名 cookie

 * 包名 org.hiirosakura.cookie.config.options.impl

 * 文件名 ConfigBooleanWithKeyBind

 * 创建时间 2022/2/17 2:41

 * @author forpleuvoir

 */
class ConfigBooleanWithKeyBind(
	override val key: String,
	override val defaultValue: Boolean,
	private val defaultKeyBind: KeyBind = KeyBind()
) : ConfigBase<Boolean>(), IConfigBooleanWithKeyBind {

	override fun initialize() {
		super<IConfigBooleanWithKeyBind>.initialize()
	}

	override val type: IConfigType
		get() = ConfigType.BOOLEAN_WITH_KEY_BIND

	private val keyBind: KeyBind = KeyBind().apply { copyOf(defaultKeyBind) }

	override fun getKeyBind(): KeyBind = keyBind

	override fun setKeyBind(keyBind: KeyBind) {
		if (this.keyBind.copyOf(keyBind)) {
			onChanged()
		}
	}

	override var configValue: Boolean = defaultValue

	override val isDefault: Boolean
		get() = super.isDefault && keyBind == defaultKeyBind

	override fun resetDefault() {
		var valueChanged = false
		val oldValue = configValue
		if (defaultValue != oldValue) {
			configValue = defaultValue
			valueChanged = true
		}
		if (keyBind != defaultKeyBind) {
			if (keyBind.copyOf(defaultKeyBind)) {
				valueChanged = true
			}
		}
		if (valueChanged) onChanged()
	}

	override fun matched(regex: Regex): Boolean {
		return if (regex.run {
				keyBind.matched(regex) || this.containsMatchIn(configValue.toString())
			}) true
		else super.matched(regex)
	}

	override fun setFromJson(jsonElement: JsonElement): Boolean {
		if (!jsonElement.isJsonObject) return false
		jsonElement.asJsonObject.apply {
			configValue = this.get("value").asBoolean
			keyBind.setValueFromJsonElement(this.get("keyBind"))
		}
		return true
	}

	override val asJsonElement: JsonElement
		get() = JsonObject().apply {
			addProperty("value", configValue)
			add("keyBind", keyBind.asJsonElement)
		}
}