package org.hiirosakura.cookie.gui.screen

import net.minecraft.client.util.math.MatrixStack
import net.minecraft.text.Text
import org.hiirosakura.cookie.gui.foundation.Drawable
import org.hiirosakura.cookie.gui.foundation.Element
import org.hiirosakura.cookie.util.Direction

/**
 *

 * 项目名 cookie

 * 包名 org.hiirosakura.cookie.gui.screen

 * 文件名 Tip

 * 创建时间 2022/3/31 22:10

 * @author forpleuvoir

 */
class Tip(
	val tips: Text,
	val direction: Direction,
	val target: Element,
	val screen: Screen,
) : Drawable {

	@Deprecated("不要用这个")
	override fun render(matrices: MatrixStack, delta: Number) {
	}

}