package org.hiirosakura.cookie.util.color

import org.hiirosakura.cookie.util.clamp
import org.hiirosakura.cookie.util.math.I

/**
 *

 * 项目名 cookie

 * 包名 org.hiirosakura.cookie.util.color

 * 文件名 HSVColor

 * 创建时间 2022/3/14 0:00

 * @author forpleuvoir

 */
class HSVColor(color: Color<out Number>) {

	/**
	 * 色相
	 */
	var hue: Int = 0
		set(value) {
			field = value.clamp(0, 360)
		}

	/**
	 * 饱和度
	 */
	var saturation: Float = 0f
		set(value) {
			field = value.clamp(0, 1f)
		}

	/**
	 * 色调
	 */
	var value: Float = 0f
		set(value) {
			field = value.clamp(0, 1f)
		}

	var alpha: Float = 1f
		set(value) {
			field = value.clamp(0f, 1f)
		}

	init {
		val color4f = Color4f(color)
		alpha = color4f.alpha
		val red = color4f.red
		val green = color4f.green
		val blue = color4f.blue
		val max = red.coerceAtLeast(green.coerceAtLeast(blue))
		val min = red.coerceAtLeast(green.coerceAtLeast(blue))
		val h = when (max) {
			red -> (green - blue) / (max - min)
			green -> 2 + (blue - red) / (max - min)
			else -> 4 + (red - green) / (max - min)
		}
		hue = if (h * 60 < 0) (h * 60).I + 360 else (h * 60).I
		value = max
		saturation = (max - min) / max
	}

	fun toRgba(): Int {
		var red = 0.0f
		var green = 0.0f
		var blue = 0.0f
		if (saturation == 0f) {
			red = value
			green = value
			blue = value
			return Color4f(red, green, blue, alpha).rgba
		}
		val h = hue / 60f
		val i = h.I
		val f = h - i
		val a = value * (1 - saturation)
		val b = value * (1 - saturation * f)
		val c = value * (1 - saturation * (1 - f))
		when (i) {
			0 -> {
				red = value
				green = c
				blue = a
			}
			1 -> {
				red = b
				green = value
				blue = a
			}
			2 -> {
				red = a
				green = value
				blue = c
			}
			3 -> {
				red = a
				green = b
				blue = value
			}
			4 -> {
				red = c
				green = a
				blue = value
			}
			5 -> {
				red = value
				green = a
				blue = b
			}
		}
		return Color4f(red, green, blue, alpha).rgba
	}

}