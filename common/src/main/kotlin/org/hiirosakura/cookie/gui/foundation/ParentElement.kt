package org.hiirosakura.cookie.gui.foundation

import net.minecraft.client.util.math.MatrixStack
import org.hiirosakura.cookie.common.Initializable
import org.hiirosakura.cookie.util.fe
import org.hiirosakura.cookie.util.math.D
import org.hiirosakura.cookie.util.notc
import java.util.*

/**
 *

 * 项目名 cookie

 * 包名 org.hiirosakura.cookie.gui.foundation

 * 文件名 ParentElement

 * 创建时间 2022/2/19 0:29

 * @author forpleuvoir

 */
interface ParentElement : Element, Initializable {

	fun children(): List<Element> = children

	val children: LinkedList<Element>

	var focused: Element?

	var dragging: Boolean

	val hoveredElement: Element?
		get() {
			val iterator = children().reverseOrder().iterator()
			var element: Element
			do {
				if (!iterator.hasNext()) return null
				element = iterator.next()
			} while (!element.mouseHover())
			return element
		}

	infix fun setInitialFocus(element: Element?) {
		focused = element
	}

	override fun initialize()

	fun addElement(element: Element): Element {
		this.children.addLast(element)
		if (element is ParentElement) element.initialize()
		return element
	}

	fun elementPre(element: Element): Element? {
		val indexOf = children.indexOf(element)
		if (indexOf < 1) return null
		return children[indexOf - 1]
	}

	fun elementNext(element: Element): Element? {
		val indexOf = children.indexOf(element)
		if (indexOf != -1 && indexOf < children.size - 1) return null
		return children[indexOf + 1]
	}

	fun elementIndexOf(element: Element): Int = this.children.indexOf(element)

	fun removeElement(element: Element): Boolean {
		return this.children.remove(element)
	}

	override fun render(matrices: MatrixStack, delta: Number) {
		visible.notc { return }
		children().drawableSort().fe { if (visible) render(matrices, delta) }
	}

	override fun tick() {
		active.notc { return }
		children().fe { tick() }
	}

	override fun mouseMove(mouseX: Number, mouseY: Number) {
		hoveredElement?.mouseMove(mouseX, mouseY)
	}

	override fun mouseClick(mouseX: Number, mouseY: Number, button: Int): Boolean {
		active.notc { return true }
		hoveredElement?.let {
			it.active.notc { return true }
		}
		focused = hoveredElement
		if (button == 0) dragging = true
		return if (focused?.active == true)
			focused?.mouseClick(mouseX, mouseY, button) ?: true
		else
			true
	}

	override fun mouseRelease(mouseX: Number, mouseY: Number, button: Int): Boolean {
		active.notc { return true }
		dragging = false
		return focused?.mouseRelease(mouseX, mouseY, button) ?: true
	}

	override fun mouseDragging(mouseX: Number, mouseY: Number, button: Int, deltaX: Number, deltaY: Number): Boolean {
		active.notc { return true }
		focused?.apply {
			if (dragging && button == 0) return mouseDragging(mouseX, mouseY, button, deltaX, deltaY)
		}
		return true
	}

	override fun mouseScrolling(mouseX: Number, mouseY: Number, amount: Number): Boolean {
		active.notc { return true }
		return hoveredElement?.mouseScrolling(mouseX, mouseY, amount) ?: true
	}

	override fun keyPress(keyCode: Int, modifiers: Int): Boolean {
		active.notc { return true }
		return focused?.keyPress(keyCode, modifiers) ?: true
	}

	override fun keyRelease(keyCode: Int, modifiers: Int): Boolean {
		active.notc { return true }
		return focused?.keyRelease(keyCode, modifiers) ?: true
	}

	override fun charTyped(chr: Char, modifiers: Int): Boolean {
		active.notc { return true }
		return focused?.charTyped(chr, modifiers) ?: true
	}

	override fun setPosition(x: Number, y: Number, z: Number) {
		val delta = this.position.minus(x.D, y.D, z.D)
		super.setPosition(x, y, z)
		children().fe { if (!fixed) deltaPosition(delta) }
	}

	override fun deltaPosition(deltaX: Number, deltaY: Number, deltaZ: Number) {
		super.deltaPosition(deltaX, deltaY, deltaZ)
		children().fe { deltaPosition(deltaX, deltaY, deltaZ) }
	}
}