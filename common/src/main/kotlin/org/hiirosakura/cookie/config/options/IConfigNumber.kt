package org.hiirosakura.cookie.config.options

import org.hiirosakura.cookie.util.clamp

/**
 *

 * 项目名 cookie

 * 包名 org.hiirosakura.cookie.config.options

 * 文件名 IConfigNumber

 * 创建时间 2022/2/16 17:25

 * @author forpleuvoir

 */
interface IConfigNumber<T : Number> {

	val minValue: Number
	val maxValue: Number

	fun fixValue(number: T): Number = number.clamp(minValue, maxValue)

}