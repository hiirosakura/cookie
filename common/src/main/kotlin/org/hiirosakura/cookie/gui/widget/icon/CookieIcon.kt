package org.hiirosakura.cookie.gui.widget.icon

import net.minecraft.client.util.math.MatrixStack
import org.hiirosakura.cookie.common.ICON_TEXTURE
import org.hiirosakura.cookie.gui.foundation.*
import org.hiirosakura.cookie.util.color.Color
import org.hiirosakura.cookie.util.color.Color4f
import org.hiirosakura.cookie.util.color.Color4i
import org.hiirosakura.cookie.util.math.D
import org.hiirosakura.cookie.util.math.I

/**
 *

 * 项目名 cookie

 * 包名 org.hiirosakura.cookie.gui.widget.icon

 * 文件名 CookieIcon

 * 创建时间 2022/3/14 18:26

 * @author forpleuvoir

 */
enum class CookieIcon(
	override val u: Int,
	override val v: Int,
	override val color: Color<out Number> = Color4f.BLACK,
	override val hoverColor: Color<out Number> = Color4i(100, 100, 100),
	override val size: Int = 16,
	override val textureWidth: Int = 256,
	override val textureHeight: Int = 256
) : Icon {

	Search(0, 0, color = Color4f.WHITE, hoverColor = Color4i.WHITE),
	Close(16, 0),
	Alpha(32, 0),
	Save(48, 0),
	Plus(64, 0),
	Minus(80, 0),
	Setting(96, 0),
	Toggle(112, 0),
	Refresh(128, 0),
	Left(144, 0),
	Right(160, 0),
	Up(176, 0),
	Down(192, 0)
	;

	override fun render(matrices: MatrixStack, delta: Number, x: Number, y: Number, size: Number) {
		setShaderTexture(ICON_TEXTURE)
		enableBlend()
		defaultBlendFunc()
		enableDepthTest()
		if (mouseX.D >= x.D && mouseX.D <= x.D + size.D && mouseY.D >= y.D && mouseY.D <= y.D + size.D) setShaderColor(hoverColor)
		else setShaderColor(color)
		drawTexture(matrices, x, y, size, size, u, v, this.size.I, this.size.I, textureWidth, textureHeight)
		setShaderColor(Color4f.WHITE)
		disableBlend()
	}

}