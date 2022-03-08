package org.hiirosakura.cookie.gui.foundation

import net.minecraft.client.util.math.MatrixStack
import org.hiirosakura.cookie.gui.foundation.layout.Margin
import org.hiirosakura.cookie.gui.foundation.layout.Modifiable
import org.hiirosakura.cookie.gui.foundation.layout.Padding
import org.hiirosakura.cookie.gui.screen.ScreenManager
import org.hiirosakura.cookie.util.math.Vector3
import org.hiirosakura.cookie.util.math.Vector3d

/**
 *

 * 项目名 cookie

 * 包名 org.hiirosakura.cookie.gui.foundation

 * 文件名 AbstractElement

 * 创建时间 2022/2/20 18:26

 * @author forpleuvoir

 */
abstract class AbstractElement : Element, Modifiable {

	override val position: Vector3<Double> = Vector3d()

	override val margin: Margin = Margin()

	override var fixed: Boolean = false

	override fun margin(top: Double, bottom: Double, left: Double, right: Double) = margin.set(top, bottom, left, right)

	override fun margin(margin: Margin) = this.margin.set(margin)

	override val padding: Padding = Padding()

	override fun padding(top: Double, bottom: Double, left: Double, right: Double) = padding.set(top, bottom, left, right)

	override fun padding(padding: Padding) = this.padding.set(padding)

	override var parent: ParentElement? = ScreenManager.current

	override var active: Boolean = true

	abstract val render: (matrices: MatrixStack, delta: Number) -> Unit

	open var renderWith: AbstractElement.(matrices: MatrixStack, delta: Number) -> Unit = { matrices: MatrixStack, delta: Number ->
		render.invoke(matrices, delta)
	}

	override fun render(matrices: MatrixStack, delta: Number) = renderWith(matrices, delta)

	open var mouseMove: (mouseX: Number, mouseY: Number) -> Unit = { _: Number, _: Number -> }

	override fun mouseMove(mouseX: Number, mouseY: Number) = mouseMove.invoke(mouseX, mouseY)

	open var onClick: (button: Int) -> Unit = { }

	open var mouseClick: (mouseX: Number, mouseY: Number, button: Int) -> Boolean = { _: Number, _: Number, button: Int ->
		onClick(button)
		true
	}

	override fun mouseClick(mouseX: Number, mouseY: Number, button: Int): Boolean = mouseClick.invoke(mouseX, mouseY, button)

	open var mouseRelease: (mouseX: Number, mouseY: Number, button: Int) -> Boolean = { _: Number, _: Number, _: Int -> true }

	override fun mouseRelease(mouseX: Number, mouseY: Number, button: Int): Boolean = mouseRelease.invoke(mouseX, mouseY, button)

	open var mouseDragging: (mouseX: Number, mouseY: Number, button: Int, deltaX: Number, deltaY: Number) -> Boolean =
		{ _: Number, _: Number, _: Int, _: Number, _: Number -> true }

	override fun mouseDragging(mouseX: Number, mouseY: Number, button: Int, deltaX: Number, deltaY: Number): Boolean =
		mouseDragging.invoke(mouseX, mouseY, button, deltaX, deltaY)

	open var mouseScrolling: (mouseX: Number, mouseY: Number, amount: Number) -> Boolean = { _: Number, _: Number, _: Number -> true }

	override fun mouseScrolling(mouseX: Number, mouseY: Number, amount: Number): Boolean = mouseScrolling.invoke(mouseX, mouseY, amount)

	open var keyPress: (keyCode: Int, modifiers: Int) -> Boolean = { _: Int, _: Int -> true }

	override fun keyPress(keyCode: Int, modifiers: Int): Boolean = keyPress.invoke(keyCode, modifiers)

	open var keyRelease: (keyCode: Int, modifiers: Int) -> Boolean = { _: Int, _: Int -> true }

	override fun keyRelease(keyCode: Int, modifiers: Int): Boolean = keyRelease.invoke(keyCode, modifiers)

	open var charTyped: (chr: Char, modifiers: Int) -> Boolean = { _: Char, _: Int -> true }

	override fun charTyped(chr: Char, modifiers: Int): Boolean = charTyped.invoke(chr, modifiers)

}