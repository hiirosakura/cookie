package org.hiirosakura.cookie.gui.foundation.layout

/**
 *

 * 项目名 cookie

 * 包名 org.hiirosakura.cookie.gui.foundation.layout

 * 文件名 Margin

 * 创建时间 2022/2/22 14:42

 * @author forpleuvoir

 */
class Margin(
	var top: Double = 0.0,
	var bottom: Double = 0.0,
	var left: Double = 0.0,
	var right: Double = 0.0
) {

	constructor(margin: Double) : this(margin, margin, margin, margin)

	var horizontal: Double
		get() = left + right
		set(value) {
			this.left = value
			this.right = value
		}

	var vertical: Double
		get() = top + bottom
		set(value) {
			this.top = value
			this.bottom = value
		}

	fun set(top: Double, bottom: Double, left: Double, right: Double) {
		this.left = left
		this.right = right
		this.top = top
		this.bottom = bottom
	}

	fun set(margin: Margin) {
		this.left = margin.left
		this.right = margin.right
		this.top = margin.top
		this.bottom = margin.bottom
	}

	override fun toString(): String {
		return "Margin{top:$top, bottom:$bottom, left:$left, right:$right}"
	}
}