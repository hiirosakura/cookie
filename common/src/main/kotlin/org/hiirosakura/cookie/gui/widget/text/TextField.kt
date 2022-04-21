package org.hiirosakura.cookie.gui.widget.text

import net.minecraft.client.util.InputUtil
import net.minecraft.client.util.math.MatrixStack
import org.hiirosakura.cookie.common.mc
import org.hiirosakura.cookie.common.textRenderer
import org.hiirosakura.cookie.gui.foundation.AbstractElement
import org.hiirosakura.cookie.gui.foundation.drawRect
import org.hiirosakura.cookie.gui.foundation.drawText
import org.hiirosakura.cookie.gui.foundation.drawTexture
import org.hiirosakura.cookie.gui.texture.GuiTextures
import org.hiirosakura.cookie.input.InputHandler
import org.hiirosakura.cookie.util.clamp
import org.hiirosakura.cookie.util.color.Color
import org.hiirosakura.cookie.util.color.Color4f
import java.util.*

/**
 *

 * 项目名 cookie

 * 包名 org.hiirosakura.cookie.gui.widget.text

 * 文件名 TextField

 * 创建时间 2022/4/2 21:48

 * @author forpleuvoir

 */
open class TextField(text: String = "") : AbstractElement() {

	override var width: Int = 60
	override var height: Int = 20

	/**
	 * 之前的文本
	 */

	var text: String = text
		set(value) {
			val old = field
			if (old != value) {
				if (!undoMode) textHistory.add(field)
				field = value
				textChange(field)
			}
		}
	protected var textHistory: Queue<String> = LinkedList<String>().apply { add(text) }

	var textColor: Color<out Number> = Color4f.WHITE

	/**
	 * 最大文本长度
	 */
	var maxLength: Int = 255
		set(value) {
			field = value.clamp(0, 65535)
		}

	var cursorPosition: Int = 0
		set(value) {
			val old = field
			if (old != value) {
				if (!undoMode) cursorPositionHistory.add(field)
				field = value.clamp(0, text.length)
			}
		}

	protected var cursorPositionHistory: Queue<Int> = LinkedList<Int>().apply { add(cursorPosition) }

	protected var undoMode: Boolean = false

	val selectionStart: Int get() = 0

	val selectionEnd: Int get() = 0

	val selectionText: String get() = text.substring(selectionStart, selectionEnd)

	var textChange: (String) -> Unit = {}


	/**
	 * 在光标位置插入文本
	 * @param text String
	 */
	protected open fun insert(text: String) {
		val old = this.text
		val new = old.substring(0, cursorPosition) + text + old.substring(cursorPosition)
		this.text = new
		cursorPosition += text.length
	}

	override fun keyPress(keyCode: Int, modifiers: Int): Boolean {
		if (InputHandler.hasKey(InputUtil.GLFW_KEY_LEFT_CONTROL)) {
			when (keyCode) {
				InputUtil.GLFW_KEY_V         -> {
					insert(mc.keyboard.clipboard)
					return true
				}
				InputUtil.GLFW_KEY_C         -> {
					mc.keyboard.clipboard = selectionText
					return true
				}
				InputUtil.GLFW_KEY_X         -> {
					mc.keyboard.clipboard = selectionText
					this.text = this.text.substring(0, selectionStart) + this.text.substring(selectionEnd)
					return true
				}
				InputUtil.GLFW_KEY_BACKSPACE -> {
					if (selectionStart == selectionEnd) {
						if (cursorPosition > 0) {
							this.text = this.text.substring(0, cursorPosition - 1) + this.text.substring(cursorPosition)
							cursorPosition--
						}
					} else {
						this.text = this.text.substring(0, selectionStart) + this.text.substring(selectionEnd)
						cursorPosition = selectionStart
					}
					return true
				}
				InputUtil.GLFW_KEY_Z         -> {
					undo()
					return true
				}
			}
		}
		return super.keyPress(keyCode, modifiers)
	}

	override fun charTyped(chr: Char, modifiers: Int): Boolean {
		return super.charTyped(chr, modifiers)
	}

	protected open fun drawBackground(matrices: MatrixStack, delta: Number) {
		drawTexture(matrices, x, y, width, height, GuiTextures.TEXT_FIELD)
	}

	protected open fun drawSelectionText(matrices: MatrixStack, delta: Number) {
		val selectionSize: Int = selectionStart - selectionEnd
		if (selectionSize != 0) {
			val startX = x + padding.left + textRenderer.getWidth(text.substring(0, selectionStart))
			val width = textRenderer.getWidth(selectionText)
			val startY = y + padding.top
			drawRect(matrices, startX, startY, width, textRenderer.fontHeight, textColor)
			drawText(matrices, selectionText, startX, startY, false, textColor)
		}
	}

	/**
	 * 撤回
	 */
	protected open fun undo() {
		undoMode = true
		if (textHistory.isNotEmpty()) {
			text = textHistory.poll()
			cursorPosition = cursorPositionHistory.poll()
		}
		undoMode = false
	}

	/**
	 * 渲染
	 * @param matrices MatrixStack
	 * @param delta Number
	 */
	override val render: (matrices: MatrixStack, delta: Number) -> Unit = { matrices, delta ->
		drawBackground(matrices, delta)
		if (selectionText.isNotEmpty()) drawSelectionText(matrices, delta)
	}
}