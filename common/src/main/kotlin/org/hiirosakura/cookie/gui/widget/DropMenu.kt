package org.hiirosakura.cookie.gui.widget

import net.minecraft.client.util.math.MatrixStack
import org.hiirosakura.cookie.common.WIDGET_TEXTURE
import org.hiirosakura.cookie.common.textRenderer
import org.hiirosakura.cookie.gui.foundation.*
import org.hiirosakura.cookie.gui.foundation.layout.ListLayout
import org.hiirosakura.cookie.gui.foundation.layout.Padding
import org.hiirosakura.cookie.gui.widget.button.IconButton
import org.hiirosakura.cookie.gui.widget.icon.CookieIcon
import org.hiirosakura.cookie.gui.widget.text.TextLabel
import org.hiirosakura.cookie.gui.widget.text.textLabel
import org.hiirosakura.cookie.util.color.Color4f
import org.hiirosakura.cookie.util.math.D
import org.hiirosakura.cookie.util.text

/**
 * 下拉菜单

 * 项目名 cookie

 * 包名 org.hiirosakura.cookie.gui.widget

 * 文件名 DropMenu

 * 创建时间 2022/3/14 20:22

 * @author forpleuvoir

 */
class DropMenu(
	val items: List<String>,
	var currentItem: String,
	var expandSize: Int = 10
) : AbstractParentElement() {

	override var width: Int = 60
		set(value) {
			field = value.coerceAtLeast(20)
			list.width = field - 8
			currentText.width = field
			x = this@DropMenu.x + this@DropMenu.width - width
		}

	override val renderLevel: Int = -10
	override val handleLevel: Int = 10

	private val fixedHeight = 18

	/**
	 * 高度固定为18
	 * 下拉按钮高18
	 * 选中项文本框高16
	 *
	 */
	override var height: Int = fixedHeight
		@Deprecated("无法设置高度")
		set(value) {
			field = fixedHeight
		}
		get() {
			return if (expanded) {
				field + list.height + 8
			} else {
				field
			}
		}


	var expanded: Boolean = false
		set(value) {
			field = value
			list.visible = field
			list.active = field
			dropButton.icon = if (field) CookieIcon.Down else CookieIcon.Left
		}

	val dropButton: IconButton = IconButton(CookieIcon.Left).apply {
		handleLevel = 10
		width = fixedHeight
		height = fixedHeight
		onClick = {
			expanded = !expanded
			println("ea")
		}
		x = this@DropMenu.x + this@DropMenu.width - width
		y = this@DropMenu.y
	}

	val currentText: TextLabel = TextLabel({ currentItem.text }, width = width, height = fixedHeight - 2).apply {
		x = this@DropMenu.x
		y = this@DropMenu.y + 1
		textColor = Color4f.BLACK
		padding.set(Padding(left = 4.0, top = 1.0, right = fixedHeight.D))
		align = Align.CENTER_LEFT
		renderWith = { matrices: MatrixStack, delta: Number ->
			setShaderTexture(WIDGET_TEXTURE)
			enableBlend()
			defaultBlendFunc()
			enableDepthTest()
			draw9Texture(matrices, this@apply.x, this@apply.y, 4, width, height, 48, 0, 16, 16)
			disableBlend()
			render.invoke(matrices, delta)
		}
	}

	val list: ListLayout = ListLayout(width - 8, expandSize.coerceAtMost(items.size) * textRenderer.fontHeight).apply {
		x = this@DropMenu.x + 4
		y = this@DropMenu.y + fixedHeight + 4
		visible = false
		active = false
		scrollerBar.amountDelta = { textRenderer.fontHeight.D }
		height = expandSize.coerceAtMost(items.size) * textRenderer.fontHeight
		items.forEach { str ->
			textLabel(
				text = str,
				width = contentSize,
				onClick = {
					currentItem = str
					expanded = false
				},
				align = Align.CENTER_LEFT
			) {
				textColor = Color4f.BLACK
				renderWith = { matrices, delta ->
					mouseHover { drawRect(matrices, x, y, this@apply.width - 8, height, Color4f.BLACK.alpha(0.2f)) }
					render.invoke(matrices, delta)
				}
			}
		}
		renderWith = { matrices, delta ->
			drawRect(matrices, x - 2, y - 2, width + 4, height + 4, Color4f.WHITE)
			setShaderTexture(WIDGET_TEXTURE)
			enableBlend()
			defaultBlendFunc()
			enableDepthTest()
			draw9Texture(matrices, x - 4, y - 4, 4, width + 8, height + 8, 0, 48, 32, 32)
			disableBlend()
			render.invoke(matrices, delta)
		}
	}

	init {
		addElement(currentText)
		addElement(dropButton)
		addElement(list)
	}

	override fun initialize() {
		width += 0
		height = 0
		list.x = this.x + 4
		list.y = this.y + fixedHeight + 4
		currentText.x = this.x
		currentText.y = this.y + 1
		dropButton.x = this.x + this.width - dropButton.width
		dropButton.y = this.y
	}

}


inline fun ParentElement.dropMenu(
	items: List<String>,
	currentItem: String,
	expandSize: Int = 10,
	scope: DropMenu.() -> Unit = {}
): DropMenu {
	return this.addElement(DropMenu(items, currentItem, expandSize).apply(scope))
}