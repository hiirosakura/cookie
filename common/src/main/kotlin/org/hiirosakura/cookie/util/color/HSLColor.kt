package org.hiirosakura.cookie.util.color

import org.hiirosakura.cookie.util.clamp
import org.hiirosakura.cookie.util.math.F
import org.hiirosakura.cookie.util.math.I
import kotlin.math.floor

/**
 *

 * 项目名 cookie

 * 包名 org.hiirosakura.cookie.util.color

 * 文件名 HSLColor

 * 创建时间 2022/3/13 23:57

 * @author forpleuvoir

 */
class HSLColor(color: Color<out Number>) {

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
			field = value.clamp(0f, 1f)
		}

	/**
	 * 亮度
	 */
	var lightness: Float = 0f
		set(value) {
			field = value.clamp(0f, 1f)
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
		lightness = (max + min) / 2
		if (max == min) {
			saturation = 0f
			hue = 0
		} else {
			saturation = if (lightness > 0.5f) {
				(max - min) / (2.0f - max - min)
			} else {
				(max - min) / (max + min)
			}
			val h = when (max) {
				red -> {
					(green - blue) / (max + min)
				}
				green -> {
					(blue - red) / (max + min)
				}
				else -> {
					(red - green) / (max + min)
				}
			}
			hue = if (h * 60 < 0) (h * 60).I + 360 else (h * 60).I
		}

	}

	fun toRgba(): Int {
		val hue = (hue / 360).F
		val r: Float
		val g: Float
		val b: Float
		val x: Float
		val y: Float
		val z: Float
		val v: Float = if (lightness <= 0.5f) lightness * (1.0f + saturation) else lightness + saturation - lightness * saturation
		if (saturation == 0.0f) {
			r = lightness
			g = lightness
			b = lightness
			return Color4f(r, g, b, alpha).rgba
		}
		y = 2.0f * lightness - v
		x = y + (v - y) * (6.0f * hue - floor(6.0f * hue))
		z = v - (v - y) * (6.0f * hue - floor(6.0f * hue))
		when ((6.0 * hue).toInt()) {
			0 -> {
				r = v
				g = x
				b = y
			}
			1 -> {
				r = z
				g = v
				b = y
			}
			2 -> {
				r = y
				g = v
				b = x
			}
			3 -> {
				r = y
				g = z
				b = v
			}
			4 -> {
				r = x
				g = y
				b = v
			}
			5 -> {
				r = v
				g = y
				b = z
			}
			else -> {
				r = v
				g = x
				b = y
			}
		}
		return Color4f(r, g, b, alpha).rgba
	}

}