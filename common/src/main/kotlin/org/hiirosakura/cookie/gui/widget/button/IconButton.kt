package org.hiirosakura.cookie.gui.widget.button

import net.minecraft.client.util.math.MatrixStack
import org.hiirosakura.cookie.gui.foundation.drawTexture
import org.hiirosakura.cookie.gui.texture.GuiTextures
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
		val texture = status(GuiTextures.BUTTON_0, GuiTextures.BUTTON_0_HOVERED, GuiTextures.BUTTON_0_PRESSED)
		drawTexture(matrices, x, y, width, height, texture, buttonColor)
	}

	var renderIcon: (matrices: MatrixStack, delta: Number, icon: Icon) -> Unit = { matrices, delta, icon ->
		val iconY = status(this.y + (height - icon.size) / 2 - 1, this.y + (height - icon.size) / 2 - 1, this.y + (height - icon.size) / 2)
		icon.render(matrices, delta, this.x + (width - icon.size) / 2, iconY, icon.size)
	}
}