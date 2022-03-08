package org.hiirosakura.cookie.gui.foundation.layout

/**
 *

 * 项目名 cookie

 * 包名 org.hiirosakura.cookie.gui.foundation.layout

 * 文件名 Modify

 * 创建时间 2022/2/22 14:54

 * @author forpleuvoir

 */
interface Modifiable {

	fun padding(
		top: Double = 0.0,
		bottom: Double = 0.0,
		left: Double = 0.0,
		right: Double = 0.0
	)

	fun padding(padding: Padding)

	fun margin(
		top: Double = 0.0,
		bottom: Double = 0.0,
		left: Double = 0.0,
		right: Double = 0.0
	)

	fun margin(margin: Margin)

}

