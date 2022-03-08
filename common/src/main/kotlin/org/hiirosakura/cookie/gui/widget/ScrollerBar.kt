package org.hiirosakura.cookie.gui.widget

import net.minecraft.client.util.math.MatrixStack
import org.hiirosakura.cookie.gui.foundation.AbstractElement

/**
 * 滚动条

 * 项目名 cookie

 * 包名 org.hiirosakura.cookie.gui.widget

 * 文件名 ScrollerBar

 * 创建时间 2022/2/23 18:13

 * @author forpleuvoir

 */
class ScrollerBar : AbstractElement() {

	var horizontal: Boolean = false

	override val render: (matrices: MatrixStack, delta: Number) -> Unit = { matrices, delta ->

	}
	override var width: Int
		get() = TODO("Not yet implemented")
		set(value) {}
	override var height: Int
		get() = TODO("Not yet implemented")
		set(value) {}
}