package org.hiirosakura.cookie.config.options

import org.hiirosakura.cookie.config.Config

/**
 *

 * 项目名 cookie

 * 包名 org.hiirosakura.cookie.config.options

 * 文件名 IConfigDouble

 * 创建时间 2022/2/16 16:58

 * @author forpleuvoir

 */
interface IConfigDouble : Config<Double>, IConfigNumber<Double> {

	override val minValue: Double get() = Double.MIN_VALUE
	override val maxValue: Double get() = Double.MAX_VALUE

	override fun fixValue(number: Double): Double {
		return super.fixValue(number).toDouble()
	}
}