package org.hiirosakura.cookie.config.options

import com.google.common.collect.Lists
import com.google.gson.JsonElement
import net.minecraft.text.Text
import org.hiirosakura.cookie.common.CookieLogger
import org.hiirosakura.cookie.config.Config
import org.hiirosakura.cookie.mod.CookieLang
import org.hiirosakura.cookie.util.tText

/**
 *

 * 项目名 cookie

 * 包名 org.hiirosakura.cookie.config.options

 * 文件名 ConfigBase

 * 创建时间 2022/2/16 14:56

 * @author forpleuvoir

 */
abstract class ConfigBase<T> : Config<T> {

	protected val log = CookieLogger.getLogger(this::class.java)

	protected open val onChangedCallback: MutableList<Config<T>.() -> Unit> = Lists.newArrayList()

	override val remark: Text by lazy { "${key}.remark".tText }

	override val displayKey: Text
		get() = key.tText

	override fun initialize() {}

	protected abstract var configValue: T

	override fun setValue(value: T) {
		val oldValue = configValue
		if (value != oldValue) {
			configValue = value
			onChanged()
		}
	}

	override fun getValue(): T = configValue

	override val isDefault: Boolean
		get() = getValue() == defaultValue

	override fun resetDefault() {
		setValue(defaultValue)
	}

	override fun matched(regex: Regex): Boolean {
		return regex.run {
			containsMatchIn(displayKey.string)
					|| containsMatchIn(remark.string)
					|| containsMatchIn(key)
					|| containsMatchIn(getValue().toString())
		}
	}

	override fun toString(): String {
		return "${type.type}{${key}:${getValue().toString()}}"
	}

	/**
	 * 从[JsonElement]中获取值
	 * @param jsonElement JsonElement
	 * @return Boolean 是否成功
	 */
	@Throws
	protected abstract fun setFromJson(jsonElement: JsonElement): Boolean

	override fun setValueFromJsonElement(jsonElement: JsonElement) {
		try {
			if (!setFromJson(jsonElement)) {
				log.warn(CookieLang.SetFromJsonFailed.tString(key, jsonElement))
			}
		} catch (e: Exception) {
			log.warn(CookieLang.SetFromJsonFailed.tString(key, jsonElement))
			log.error(e)
		}
	}

	override fun setOnChangedCallback(callback: Config<T>.() -> Unit) {
		onChangedCallback.add(callback)
	}

	override fun onChanged() {
		onChangedCallback.forEach { it.invoke(this) }
	}

	override fun equals(other: Any?): Boolean {
		return if (other is Config<*>) {
			this.key == other.key && this.getValue() == other.getValue()
		} else false
	}

	override fun hashCode(): Int {
		return displayKey.hashCode()
	}
}