package org.hiirosakura.cookie.gui.foundation.layout

import org.hiirosakura.cookie.gui.foundation.ParentElement
import org.hiirosakura.cookie.util.math.D
import org.hiirosakura.cookie.util.math.I
import org.hiirosakura.cookie.util.math.Vector3d

/**
 *

 * 项目名 cookie

 * 包名 org.hiirosakura.cookie.gui.foundation.layout

 * 文件名 ColumnLayout

 * 创建时间 2022/2/22 18:01

 * @author forpleuvoir

 */
class ColumnLayout : Layout() {

	override fun align() {
		var width = 0.0
		var height = 0.0
		children.forEach { element ->
			val preElement = preNotFixedElement(children, element)
			val preElementBottom = preElement?.bottom?.D ?: 0.0
			val marginTop = (if (preElement != null) preElementBottom else padding.top) + element.margin.top
			if (!element.fixed) element.setPosition(this.position + Vector3d(padding.left + element.margin.left, marginTop))
			if (element is ParentElement) element.initialize()
			height = (element.bottom.D + element.margin.bottom) - this.y + padding.bottom
			if (element.width + element.margin.horizontal + padding.horizontal > width) {
				width = element.width + element.margin.horizontal + padding.horizontal
			}
		}
		this.width = width.I
		this.height = height.I
	}

}

inline fun ParentElement.column(
	padding: Padding = Padding(),
	margin: Margin = Margin(),
	scope: ColumnLayout.() -> Unit
): ColumnLayout {
	val column = ColumnLayout().apply {
		padding(padding)
		margin(margin)
		scope()
	}
	this.addElement(column)
	return column
}