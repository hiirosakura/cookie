package org.hiirosakura.cookie.gui.foundation.layout

/**
 * 内边距

 * 项目名 cookie

 * 包名 org.hiirosakura.cookie.gui.foundation.layout

 * 文件名 Padding

 * 创建时间 2022/2/22 14:40

 * @author forpleuvoir

 */
class Padding(
	var top: Double = 0.0,
	var bottom: Double = 0.0,
	var left: Double = 0.0,
	var right: Double = 0.0
) {

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

	fun set(padding: Padding) {
		this.left = padding.left
		this.right = padding.right
		this.top = padding.top
		this.bottom = padding.bottom
	}


	override fun toString(): String {
		return "Padding{top:$top, bottom:$bottom, left:$left, right:$right}"
	}
}