package org.hiirosakura.cookie.input

import com.google.common.collect.Lists
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import net.minecraft.client.util.InputUtil
import net.minecraft.text.Text
import org.hiirosakura.cookie.common.*
import org.hiirosakura.cookie.input.KeyTriggerMode.*
import java.util.*

/**
 *

 * 项目名 cookie

 * 包名 org.hiirosakura.cookie.input

 * 文件名 KeyBind

 * 创建时间 2022/2/16 20:15

 * @author forpleuvoir

 */
class KeyBind(
	vararg keyCodes: Int,
	private val defaultSetting: KeyBindSetting = KeyBindSetting(),
	var trigger: (KeyBind.() -> Unit)? = null
) : JsonData, Resettable, Tickable, Copyable<KeyBind>, Matchable, Notifiable<KeyBind> {

	private val log = CookieLogger.getLogger(this::class.java)

	private val defaultKeys: LinkedHashSet<Int> = LinkedHashSet(keyCodes.toSet())

	var setting: KeyBindSetting = KeyBindSetting().apply { copyOf(defaultSetting) }

	val keys: LinkedHashSet<Int> = LinkedHashSet(defaultKeys)

	/**
	 * 是否与其他按键冲突
	 */
	var isConflicted = false

	/**
	 * 冲突列表
	 */
	val conflictList: MutableList<KeyBind> = ArrayList()

	/**
	 * 用来记录被按下的时间
	 */
	var tickCounter: Long = 0
		private set(value) {
			field = value.coerceAtLeast(0)
		}

	/**
	 * 是否处于按下状态
	 */
	var isPressed: Boolean = false
		private set

	/**
	 * 更新状态
	 * @param key Set<Int>
	 * @return Boolean 是否取之后的操作
	 */
	fun update(key: Set<Int>): Boolean {
		if (key.isEmpty()) {
			isPressed = false
			return false
		}
		if (key.size == keys.size && keys.containsAll(key) && setting.environment.envMatch()) {
			isPressed = true
			return setting.cancelFurtherProcess
		} else if (isPressed) return setting.cancelFurtherProcess

		isPressed = false
		return false
	}

	override fun tick() {
		if (isPressed) tickCounter++
		when (setting.triggerMode) {
			OnPress -> {
				if (isPressed && tickCounter == 1L) trigger?.invoke(this)
			}
			OnLongPress -> {
				if (isPressed && tickCounter == setting.longPressTime) trigger?.invoke(this)
			}
			OnRelease -> {
				if (tickCounter != 0L && !isPressed) trigger?.invoke(this)
			}
			Pressed -> {
				if (isPressed) trigger?.invoke(this)
			}
			BOTH -> {
				if (isPressed && tickCounter == 1L) trigger?.invoke(this)
				else if (tickCounter != 0L && !isPressed) trigger?.invoke(this)
			}
		}
		if (!isPressed) tickCounter = 0L
	}

	override val isDefault: Boolean
		get() = defaultKeys == keys && setting == defaultSetting

	override fun resetDefault() {
		setting = defaultSetting
		setKey(*defaultKeys.toIntArray())
	}

	fun addKey(keyCode: Int): Boolean {
		if (keys.add(keyCode)) {
			onChanged()
			return true
		}
		return false
	}

	fun setKey(vararg keyCodes: Int) {
		keys.clear()
		keys.addAll(keyCodes.toSet())
		onChanged()
	}

	override fun copyOf(target: KeyBind): Boolean {
		var valueChange = false
		if (this.keys != target.keys) {
			this.keys.clear()
			this.keys.addAll(target.keys)
			valueChange = true
		}
		if (this.setting.copyOf(defaultSetting)) {
			valueChange = true
		}
		if (this.trigger != target.trigger) {
			this.trigger = target.trigger
			valueChange = true
		}
		if (valueChange) onChanged()
		return valueChange
	}

	val asTexts: List<Text>
		get() {
			val list = LinkedList<Text>()
			keys.forEach {
				if (it > 8)
					list.addLast(InputUtil.fromKeyCode(it, 0).localizedText)
				else
					list.addLast(InputUtil.Type.MOUSE.createFromCode(it).localizedText)
			}
			return list
		}

	val asTranslatableKey: List<String>
		get() {
			val list = LinkedList<String>()
			keys.forEach {
				if (it > 8)
					list.addLast(InputUtil.fromKeyCode(it, 0).translationKey)
				else
					list.addLast(InputUtil.Type.MOUSE.createFromCode(it).translationKey)
			}
			return list
		}

	inline fun setting(setting: KeyBindSetting.() -> Unit): KeyBind {
		this.setting.setting()
		onChanged()
		return this
	}

	override fun setValueFromJsonElement(jsonElement: JsonElement) {
		try {
			val jsonObject = jsonElement.asJsonObject
			this.keys.clear()
			jsonObject["keys"].asJsonArray.forEach {
				this.keys.add(InputUtil.fromTranslationKey(it.asString).code)
			}
			this.setting.setValueFromJsonElement(jsonObject["setting"])
			onChanged()
		} catch (e: Exception) {
			this.keys.clear()
			setting = defaultSetting
			log.error(e)
		}
	}

	override val asJsonElement: JsonElement
		get() {
			val jsonObject = JsonObject()
			val jsonArray = JsonArray()
			asTranslatableKey.forEach { jsonArray.add(it) }
			jsonObject.add("keys", jsonArray)
			jsonObject.add("setting", setting.asJsonElement)
			return jsonObject
		}

	override fun matched(regex: Regex): Boolean {
		return regex.run {
			asTexts.forEach { if (this.containsMatchIn(it.string)) return@run true }
			asTranslatableKey.forEach { if (this.containsMatchIn(it)) return@run true }
			setting matched regex
		}
	}


	override fun equals(other: Any?): Boolean {
		if (this === other) return true
		if (javaClass != other?.javaClass) return false

		other as KeyBind

		if (setting != other.setting) return false
		if (keys != other.keys) return false

		return true
	}

	override fun hashCode(): Int {
		var result = setting.hashCode()
		result = 31 * result + keys.hashCode()
		return result
	}

	override fun toString(): String {
		return asJsonElement.asString
	}

	val onChangedCallback: MutableList<KeyBind.() -> Unit> = Lists.newArrayList()

	override fun onChanged() {
		onChangedCallback.forEach { it() }
	}

	override fun setOnChangedCallback(callback: KeyBind.() -> Unit) {
		onChangedCallback.add(callback)
	}


}