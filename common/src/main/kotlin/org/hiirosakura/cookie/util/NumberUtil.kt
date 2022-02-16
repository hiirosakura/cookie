package org.hiirosakura.cookie.util

/**
 *

 * 项目名 cookie

 * 包名 org.hiirosakura.cookie.util

 * 文件名 NumberUtil

 * 创建时间 2022/2/16 17:20

 * @author forpleuvoir

 */
fun Number.clamp(minValue: Number, maxValue: Number): Number {
	if (this.toDouble() < minValue.toDouble()) {
		return minValue
	}
	return if (this.toDouble() > maxValue.toDouble()) {
		maxValue
	} else this
}

fun Int.clamp(minValue: Number, maxValue: Number): Int {
	return (this as Number).clamp(minValue, maxValue).toInt()
}

fun Long.clamp(minValue: Number, maxValue: Number): Long {
	return (this as Number).clamp(minValue, maxValue).toLong()
}


fun Double.clamp(minValue: Number, maxValue: Number): Double {
	return (this as Number).clamp(minValue, maxValue).toDouble()
}

fun Float.clamp(minValue: Number, maxValue: Number): Float {
	return (this as Number).clamp(minValue, maxValue).toFloat()
}