package org.hiirosakura.cookie.config.options.impl

import com.google.gson.JsonElement
import org.hiirosakura.cookie.config.IConfigType
import org.hiirosakura.cookie.config.options.ConfigBase
import org.hiirosakura.cookie.config.options.ConfigType
import org.hiirosakura.cookie.config.options.IConfigHotkey
import org.hiirosakura.cookie.input.KeyBind

/**
 *

 * 项目名 cookie

 * 包名 org.hiirosakura.cookie.config.options.impl

 * 文件名 ConfigHotkey

 * 创建时间 2022/2/17 1:29

 * @author forpleuvoir

 */
class ConfigHotkey(
	override val key: String,
	override val defaultValue: KeyBind
) : ConfigBase<KeyBind>(), IConfigHotkey {

	override fun initialize() {
		super<IConfigHotkey>.initialize()
	}

	override val type: IConfigType
		get() = ConfigType.HOTKEY

	override var configValue: KeyBind = KeyBind().apply { copyOf(defaultValue) }

	override fun getKeyBind(): KeyBind = configValue

	override fun setKeyBind(keyBind: KeyBind) {
		setValue(keyBind)
	}

	override fun setValue(value: KeyBind) {
		if (this.configValue.copyOf(value)) {
			onChanged()
		}
	}

	override fun matched(regex: Regex): Boolean {
		return if (regex.run {
				configValue.matched(regex)
			}) true
		else super.matched(regex)
	}

	override fun setFromJson(jsonElement: JsonElement): Boolean {
		if (!jsonElement.isJsonObject) return false
		configValue.setValueFromJsonElement(jsonElement)
		return true
	}

	override val asJsonElement: JsonElement
		get() = configValue.asJsonElement


}