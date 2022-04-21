package org.hiirosakura.cookie.input

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import org.hiirosakura.cookie.common.CookieLogger
import org.hiirosakura.cookie.common.Copyable
import org.hiirosakura.cookie.common.JsonData
import org.hiirosakura.cookie.common.Matchable
import org.hiirosakura.cookie.util.JsonUtil.toJsonStr

/**
 *

 * 项目名 cookie

 * 包名 org.hiirosakura.cookie.input

 * 文件名 KeyBindSetting

 * 创建时间 2022/2/16 23:06

 * @author forpleuvoir

 */
class KeyBindSetting(
	/**
	 * 按键触发环境
	 */
	var environment: KeyEnvironment = KeyEnvironment.InGame,
	/**
	 * 是否取消之后的操作
	 */
	var cancelFurtherProcess: Boolean = true,
	/**
	 * 只有指定顺序的按键才会触发
	 */
	var ordered: Boolean = true,
	/**
	 * 按键触发模式
	 */
	var triggerMode: KeyTriggerMode = KeyTriggerMode.OnRelease,
	/**
	 * 按下多久触发长按
	 */
	longPressTime: Long = 20,
) : JsonData, Copyable<KeyBindSetting>, Matchable {

	/**
	 * 按下多久触发长按
	 */
	var longPressTime: Long = longPressTime.coerceAtLeast(0)
		set(value) {
			field = value.coerceAtLeast(0)
		}

	private val log = CookieLogger.getLogger(this::class.java)

	override val asJsonElement: JsonElement
		get() = JsonObject().apply {
			addProperty("keyEnvironment", environment.key)
			addProperty("cancelFurtherProcess", cancelFurtherProcess)
			addProperty("ordered", ordered)
			addProperty("triggerMode", triggerMode.key)
			addProperty("longPressTime", longPressTime)
		}

	override fun setValueFromJsonElement(jsonElement: JsonElement) {
		try {
			val jsonObject = jsonElement.asJsonObject
			this.environment = environment.fromKey(jsonObject["keyEnvironment"].asString)
			this.cancelFurtherProcess = jsonObject["cancelFurtherProcess"].asBoolean
			this.ordered = jsonObject["ordered"].asBoolean
			this.triggerMode = triggerMode.fromKey(jsonObject["trigger"].asString)
			this.longPressTime = jsonObject["longPressTime"].asLong
		} catch (e: Exception) {
			log.error(e)
		}
	}

	override fun matched(regex: Regex): Boolean {
		return regex.run {
			containsMatchIn(asJsonElement.toJsonStr())
		}
	}

	override fun equals(other: Any?): Boolean {
		if (this === other) return true
		if (javaClass != other?.javaClass) return false

		other as KeyBindSetting

		if (environment != other.environment) return false
		if (cancelFurtherProcess != other.cancelFurtherProcess) return false
		if (ordered != other.ordered) return false
		if (triggerMode != other.triggerMode) return false
		if (longPressTime != other.longPressTime) return false

		return true
	}

	override fun hashCode(): Int {
		var result = environment.hashCode()
		result = 31 * result + cancelFurtherProcess.hashCode()
		result = 31 * result + ordered.hashCode()
		result = 31 * result + triggerMode.hashCode()
		result = 31 * result + longPressTime.hashCode()
		return result
	}

	override fun copyOf(target: KeyBindSetting): Boolean {
		var valueChange = false
		if (this.cancelFurtherProcess != target.cancelFurtherProcess) {
			this.cancelFurtherProcess = target.cancelFurtherProcess
			valueChange = true
		}
		if (this.ordered != target.ordered) {
			this.ordered = target.ordered
			valueChange = true
		}
		if (this.triggerMode != target.triggerMode) {
			this.triggerMode = target.triggerMode
			valueChange = true
		}
		if (this.environment != target.environment) {
			this.environment = target.environment
			valueChange = true
		}
		if (this.longPressTime != target.longPressTime) {
			this.longPressTime = target.longPressTime
			valueChange = true
		}
		return valueChange
	}

}