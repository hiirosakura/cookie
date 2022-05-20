package org.hiirosakura.cookie.gui.widget

import net.minecraft.client.util.math.MatrixStack
import net.minecraft.text.Text
import org.hiirosakura.cookie.common.textRenderer
import org.hiirosakura.cookie.gui.foundation.*
import org.hiirosakura.cookie.gui.foundation.layout.ListLayout
import org.hiirosakura.cookie.gui.foundation.layout.Padding
import org.hiirosakura.cookie.gui.texture.GuiTextures
import org.hiirosakura.cookie.gui.widget.button.IconButton
import org.hiirosakura.cookie.gui.widget.icon.CookieIcon
import org.hiirosakura.cookie.gui.widget.text.TextLabel
import org.hiirosakura.cookie.gui.widget.text.textLabel
import org.hiirosakura.cookie.util.Direction
import org.hiirosakura.cookie.util.color.Color4f
import org.hiirosakura.cookie.util.math.D
import org.hiirosakura.cookie.util.maxWidth
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

	private val fixedHeight = 18

	override var width: Int = items.maxWidth() + fixedHeight + 8
		set(value) {
			field = value.coerceAtLeast(fixedHeight + 4)
			list.width = field - 8
			currentText.width = field
			x = this@DropMenu.x + this@DropMenu.width - width
		}

	override val renderLevel: Int = 10
	override val handleLevel: Int = 10

	var itemTip: (item: String) -> Text? = { null }
	var itemTipDirection: (item: String) -> Direction? = { null }

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

	var onToggle: DropMenu.(currentItem: String) -> Unit = {}

	var expanded: Boolean = false
		set(value) {
			field = value
			list.visible = field
			list.active = field
			dropButton.icon = if (field) CookieIcon.Down else CookieIcon.Left
		}

	val dropButton: IconButton = IconButton(CookieIcon.Left).apply {
		handleLevel = 10
		width = fixedHeight - 1
		height = fixedHeight
		onClick = {
			expanded = !expanded
		}
		x = this@DropMenu.x + this@DropMenu.width - width
		y = this@DropMenu.y
	}

	val currentText: TextLabel = TextLabel({ currentItem.text }, width = width, height = fixedHeight - 2).apply {
		x = this@DropMenu.x
		y = this@DropMenu.y + 1
		textColor = Color4f.BLACK
		padding.set(Padding(left = 4.0, top = 0.5, right = fixedHeight.D))
		align = Align.CenterLeft
		renderWith = { matrices: MatrixStack, delta: Number ->
			drawTexture(matrices, this@apply.x, this@apply.y, width, height, GuiTextures.TEXT_FIELD)
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
					onToggle(currentItem)
				},
				align = Align.CenterLeft
			) {
				tip = { itemTip(str) }
				tipDirection = { itemTipDirection(str) }
				textColor = Color4f.BLACK
				renderWith = { matrices, delta ->
					mouseHover {
						drawRect(matrices, x, y - 0.5, this@apply.width - scrollerBar.width, height, Color4f.BLACK.alpha(0.2f))
					}
					render.invoke(matrices, delta)
				}
			}
		}
		renderWith = { matrices, delta ->
			drawRect(matrices, x - 2, y - 2, width + 4, height + 4, Color4f.WHITE)
			render.invoke(matrices, delta)
			drawTexture(matrices, x - 4, y - 4 - 1, width + 8, height + 8 + 2, GuiTextures.BORDER)
		}
	}

	init {
		addElement(list)
		addElement(currentText)
		addElement(dropButton)
	}

	override fun initialize() {
		width += 0
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