package org.hiirosakura.cookie.gui.widget.button

import net.minecraft.client.util.math.MatrixStack
import org.hiirosakura.cookie.common.WIDGET_TEXTURE
import org.hiirosakura.cookie.gui.foundation.*
import org.hiirosakura.cookie.gui.widget.icon.Icon

/**
 * 图标按钮

 * 项目名 cookie

 * 包名 org.hiirosakura.cookie.gui.widget.button

 * 文件名 IconButton

 * 创建时间 2022/3/14 21:16

 * @author forpleuvoir

 */
open class IconButton(var icon: Icon) : Button("") {

	override var width: Int = 20

	override val render: (matrices: MatrixStack, delta: Number) -> Unit = { matrices, delta ->
		drawBackground(matrices, delta)
		renderIcon(matrices, delta, icon)
	}

	override fun drawBackground(matrices: MatrixStack, delta: Number) {
		setShaderTexture(WIDGET_TEXTURE)
		enableBlend()
		defaultBlendFunc()
		enableDepthTest()
		setShaderColor(buttonColor)
		val u = if (active) if (mouseHover()) 16 else 0 else 32
		draw9Texture(matrices, x, y, 4, width, height, 0, u, 16, 16)
		disableBlend()
	}

	var renderIcon: (matrices: MatrixStack, delta: Number, icon: Icon) -> Unit = { matrices, delta, icon ->
		icon.render(matrices, delta, this.x + (width - icon.size) / 2, this.y + (height - icon.size) / 2 - 1, icon.size)
	}
}