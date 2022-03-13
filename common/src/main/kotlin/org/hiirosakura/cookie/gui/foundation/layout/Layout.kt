package org.hiirosakura.cookie.gui.foundation.layout

import org.hiirosakura.cookie.gui.foundation.AbstractParentElement

/**
 *

 * 项目名 cookie

 * 包名 org.hiirosakura.cookie.gui.foundation.layout

 * 文件名 Layout

 * 创建时间 2022/2/21 17:54

 * @author forpleuvoir

 */
abstract class Layout : AbstractParentElement() {

	override var width: Int = 0
	override var height: Int = 0

	abstract fun align()

	override fun initialize() {
		align()
	}
}