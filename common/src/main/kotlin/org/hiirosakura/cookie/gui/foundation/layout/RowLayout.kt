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
		var width = 0.0
		var height = 0.0
		children.forEachIndexed { index, element ->
			val preElement = elementPre(element)
			val preElementRight = preElement?.right?.D ?: 0.0
			val marginLeft = (if (preElement != null) preElementRight else padding.left) + element.margin.left
			if (!element.fixed) element.setPosition(this.position + Vector3d(marginLeft, padding.top + element.margin.top))
			width = (element.right.D + element.margin.right.D) - this.x + padding.right
			if (element.height + padding.horizontal + element.margin.horizontal > height) {
				height = element.height + element.margin.horizontal + padding.horizontal
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