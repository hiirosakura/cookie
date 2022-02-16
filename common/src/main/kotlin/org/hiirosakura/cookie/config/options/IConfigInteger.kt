package org.hiirosakura.cookie.config.options

import org.hiirosakura.cookie.config.Config

/**
 *

 * 项目名 cookie

 * 包名 org.hiirosakura.cookie.config.options

 * 文件名 IConfigInteger

 * 创建时间 2022/2/16 17:39

 * @author forpleuvoir

 */
interface IConfigInteger : Config<Int>, IConfigNumber<Int> {

	override val minValue: Int
		get() = Int.MIN_VALUE
	override val maxValue: Int
		get() = Int.MAX_VALUE

	override fun fixValue(number: Int): Int {
		return super.fixValue(number).toInt()
	}
}