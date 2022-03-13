package org.hiirosakura.cookie.util.color

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import org.hiirosakura.cookie.common.JsonData
import org.hiirosakura.cookie.util.clamp


/**
 *

 * 项目名 ibuki_gourd

 * 包名 org.hiirosakura.cookie.util.color

 * 文件名 Color

 * 创建时间 2021/12/13 18:55

 * @author forpleuvoir

 */
interface Color<T : Number> : JsonData {

	companion object {

		fun copy(color: Color<out Number>): Color<out Number> {
			return if (color.red is Int) {
				Color4i(color.red as Int, color.green as Int, color.blue as Int, color.alpha as Int)
			} else {
				Color4f(color.red as Float, color.green as Float, color.blue as Float, color.alpha as Float)
			}
		}
	}

	val rgba: Int
	val hexString: String
		get() = "#${String.format("%x", hashCode()).uppercase()}"

	fun rgba(alpha: T): Int
	fun fromInt(color: Int): Color<T>
	fun alpha(alpha: T): Color<T> {
		return fromInt(this.rgba(alpha))
	}

	fun opacity(opacity: Double): Color<T>

	fun formatStr(str: String): String {
		return (if (str == "0") "00" else if (str.length == 1) "0$str" else str).uppercase()
	}

	override val asJsonElement: JsonElement
		get() {
			val jsonObject = JsonObject()
			jsonObject.addProperty("red", red)
			jsonObject.addProperty("green", green)
			jsonObject.addProperty("blue", blue)
			jsonObject.addProperty("alpha", alpha)
			return jsonObject
		}

	fun fixAllValue() {
		red = fixValue(red)
		green = fixValue(green)
		blue = fixValue(blue)
		alpha = fixValue(alpha)
	}

	@Suppress("unchecked_cast")
	fun fixValue(value: T): T {
		return (value as Number).clamp(minValue as Number, maxValue as Number) as T
	}

	fun toHsl(): HSLColor {
		return HSLColor(this)
	}

	fun fromHsl(hslColor: HSLColor) {
		this.fromInt(hslColor.toRgba())
	}

	fun toHsv(): HSVColor {
		return HSVColor(this)
	}

	fun fromHsv(hsvColor: HSVColor) {
		this.fromInt(hsvColor.toRgba())
	}

	val minValue: T
	val maxValue: T

	var red: T
	var green: T
	var blue: T
	var alpha: T

}