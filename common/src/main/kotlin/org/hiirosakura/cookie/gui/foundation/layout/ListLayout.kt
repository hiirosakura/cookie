package org.hiirosakura.cookie.gui.foundation.layout

import net.minecraft.client.util.math.MatrixStack
import org.hiirosakura.cookie.gui.foundation.ParentElement
import org.hiirosakura.cookie.gui.widget.ScrollerBar
import org.hiirosakura.cookie.util.ifc
import org.hiirosakura.cookie.util.math.D
import org.hiirosakura.cookie.util.math.I
import org.hiirosakura.cookie.util.math.Vector3d
import org.hiirosakura.cookie.util.notc

/**
 *

 * 项目名 cookie

 * 包名 org.hiirosakura.cookie.gui.foundation.layout

 * 文件名 ListLayout

 * 创建时间 2022/2/23 17:33

 * @author forpleuvoir

 */
class ListLayout(width: Int = 0, height: Int = 0) : Layout() {

	private var fixedWidth: Boolean = false
	private var fixedHeight: Boolean = false
	var scrollerBar: ScrollerBar = ScrollerBar()
	var horizontal: Boolean by scrollerBar::horizontal

	override fun render(matrices: MatrixStack, delta: Number) {
		align()
		super.render(matrices, delta)
	}

	init {
		fixedWidth = width != 0
		fixedHeight = height != 0
		this.width = width
		this.height = height
		this.addElement(scrollerBar.apply { renderLevel = -1 })
	}

	val contentSize: Int
		get() {
			return if (horizontal) {
				if (fixedWidth) width - scrollerBar.width
				else width
			} else {
				if (fixedHeight) height - scrollerBar.height
				else height
			}
		}

	override fun align() {
		val alignChildren = children.filter { it != scrollerBar }
		if (!horizontal) {
			var width = 0.0
			var height = this.padding.vertical
			alignChildren.forEach { element ->
				val preElement =
					if (preNotFixedElement(alignChildren, element) != scrollerBar) preNotFixedElement(alignChildren, element) else null
				val preElementBottom = preElement?.bottom?.D ?: (this.top.D + this.padding.top.D)
				val marginTop =
					(if (preElement != null) preElementBottom else (this.top.D + this.padding.top.D) - scrollerBar.amount) + element.margin.top
				if (element is ParentElement) element.initialize()
				if (!element.fixed) {
					element.setPosition(Vector3d(this.left.D + padding.left + element.margin.left, marginTop))
					element.visible = !(element.bottom.D <= this.top.D || element.top.D >= this.bottom.D)
					height += element.height + element.margin.vertical
					if (element.height + element.margin.vertical + padding.vertical > width) {
						width = element.width + element.margin.horizontal + padding.horizontal
					}
				}
			}
			if (!fixedWidth)
				this.width = width.I + scrollerBar.width
			if (!fixedHeight)
				this.height = height.I
			scrollerBar.apply {
				this.height = this@ListLayout.height
				x = this@ListLayout.right.D - scrollerBar.width
				y = this@ListLayout.top.D
				maxAmount = { (height - this@ListLayout.height).coerceAtLeast(0.0) }
				percent = { (this@ListLayout.height / height).coerceAtMost(1.0) }
			}
		} else {
			var width = this.padding.vertical
			var height = 0.0
			alignChildren.forEach { element ->
				var preElement = if (elementPre(element) != scrollerBar) elementPre(element) else null
				preElement?.let {
					it.fixed.ifc { preElement = null }
				}
				val preElementRight = preElement?.right?.D ?: (this.left.D + this.padding.left)
				val marginLeft =
					(if (preElement != null) preElementRight else (this.left.D + this.padding.left) - scrollerBar.amount) + element.margin.left
				if (element is ParentElement) element.initialize()
				if (!element.fixed) {
					element.setPosition(Vector3d(marginLeft, this.top.D + padding.top + element.margin.top))
					element.visible = !(element.right.D <= this.left.D || element.left.D >= this.right.D)
					width += element.width + element.margin.horizontal
					if (element.height + padding.horizontal + element.margin.horizontal > height) {
						height = element.height + element.margin.horizontal + padding.horizontal
					}
				}
			}
			if (!fixedWidth)
				this.width = width.I
			if (!fixedHeight)
				this.height = height.I + scrollerBar.height
			scrollerBar.apply {
				this.width = this@ListLayout.width
				x = this@ListLayout.left.D
				y = this@ListLayout.bottom.D - scrollerBar.height
				maxAmount = { (width - this@ListLayout.width).coerceAtLeast(0.0) }
				percent = { (this@ListLayout.width / width).coerceAtMost(1.0) }
			}
		}

	}

	override fun mouseScrolling(mouseX: Number, mouseY: Number, amount: Number): Boolean {
		active.notc { return true }
		return if (hoveredElement?.mouseScrolling(mouseX, mouseY, amount) == true) {
			scrollerBar.mouseScrolling(mouseX, mouseY, amount)
		} else hoveredElement?.mouseScrolling(mouseX, mouseY, amount) ?: scrollerBar.mouseScrolling(mouseX, mouseY, amount)
	}
}

inline fun ParentElement.list(
	width: Int = 0,
	height: Int = 0,
	horizontal: Boolean = false,
	scrollerBarScope: ScrollerBar.() -> Unit = {},
	padding: Padding = Padding(),
	margin: Margin = Margin(),
	scope: ListLayout.() -> Unit
): ListLayout {
	return this.addElement(ListLayout(
		width, height
	).apply {
		scrollerBar.apply(scrollerBarScope)
		this.horizontal = horizontal
		padding(padding)
		margin(margin)
		scope()
	})
}