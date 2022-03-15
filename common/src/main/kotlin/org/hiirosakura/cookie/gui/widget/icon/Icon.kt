package org.hiirosakura.cookie.gui.widget.icon

import net.minecraft.client.util.math.MatrixStack
import org.hiirosakura.cookie.gui.foundation.Drawable
import org.hiirosakura.cookie.util.color.Color
import org.hiirosakura.cookie.util.color.Color4f

/**
 *

 * 项目名 cookie

 * 包名 org.hiirosakura.cookie.gui.widget.icon

 * 文件名 Icon

 * 创建时间 2022/3/14 18:20

 * @author forpleuvoir

 */
interface Icon : Drawable {

	val size: Int
	val color: Color<out Number> get() = Color4f.BLACK
	val hoverColor: Color<out Number> get() = Color4f.WHITE
	val u: Int
	val v: Int
	val textureWidth: Int get() = 256
	val textureHeight: Int get() = 256

	/**
	 *
	 * @param matrices MatrixStack
	 * @param delta Number
	 * @see render[matrices:MatrixStack,delta:Number,x:Number,y:Number,size:Number]
	 */
	@Deprecated("use render(matrices: MatrixStack, delta: Number, x: Number, y: Number, size: Number)")
	override fun render(matrices: MatrixStack, delta: Number) {
	}

	fun render(matrices: MatrixStack, delta: Number, x: Number, y: Number, size: Number)
}