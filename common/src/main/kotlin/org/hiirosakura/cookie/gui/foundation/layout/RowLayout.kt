package org.hiirosakura.cookie.gui.foundation.layout

import org.hiirosakura.cookie.gui.foundation.ParentElement
import org.hiirosakura.cookie.util.math.D
import org.hiirosakura.cookie.util.math.I
import org.hiirosakura.cookie.util.math.Vector3d


/**
 *

 * 项目名 cookie

 * 包名 org.hiirosakura.cookie.gui.foundation.layout

 * 文件名 RowLayout

 * 创建时间 2022/2/22 15:25

 * @author forpleuvoir

 */
class RowLayout : Layout() {

	override fun align() {
		var width = this.padding.vertical
		var height = 0.0
		children.forEach { element ->
			val preElement = preNotFixedElement(children, element)
			val preElementRight = preElement?.right?.D ?: (this.left.D + this.padding.left)
			val marginLeft =
				(if (preElement != null) preElementRight else this.left.D + this.padding.left) + element.margin.left
			if (element is ParentElement) element.initialize()
			if (!element.fixed) {
				element.setPosition(Vector3d(marginLeft, this.top.D + padding.top + element.margin.top))
				width += element.width + element.margin.horizontal
				if (element.height + padding.horizontal + element.margin.horizontal > height) {
					height = element.height + element.margin.horizontal + padding.horizontal
				}
			}
		}
		this.width = width.I
		this.height = height.I
	}

}

inline fun ParentElement.row(
	padding: Padding = Padding(),
	margin: Margin = Margin(),
	scope: RowLayout.() -> Unit
): RowLayout {
	val row = RowLayout().apply {
		padding(padding)
		margin(margin)
		scope()
	}
	this.addElement(row)
	return row
}