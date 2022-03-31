package org.hiirosakura.cookie.gui.widget

import net.minecraft.client.util.math.MatrixStack
import org.hiirosakura.cookie.gui.foundation.ParentElement
import org.hiirosakura.cookie.gui.foundation.drawTexture
import org.hiirosakura.cookie.gui.texture.GuiTextures
import org.hiirosakura.cookie.util.clamp
import org.hiirosakura.cookie.util.ifc
import org.hiirosakura.cookie.util.math.D

/**
 * 滚动条

 * 项目名 cookie

 * 包名 org.hiirosakura.cookie.gui.widget

 * 文件名 ScrollerBar

 * 创建时间 2022/2/23 18:13

 * @author forpleuvoir

 */
open class ScrollerBar : ClickableElement() {

	/**
	 * 是否为水平滚动条
	 */
	var horizontal: Boolean = false

	override var width: Int = 8
		get() {
			return if (shouldRender) field else 0
		}
		set(value) {
			field = value.coerceAtLeast(if (shouldRender) 8 else 0)
		}

	override var height: Int = 8
		get() {
			return if (shouldRender) field else 0
		}
		set(value) {
			field = value.coerceAtLeast(if (shouldRender) 8 else 0)
		}

	/**
	 * 滚动条当前的计量
	 */
	var amount = 0.0
		set(value) {
			field = value.clamp(0, maxAmount())
			amountConsumer(field)
		}

	var amountConsumer: (Double) -> Unit = {}

	/**
	 * 滚动条最大计量
	 */
	var maxAmount: () -> Double = { 0.0 }

	/**
	 * 页面占总数的百分比
	 *
	 * 当前页面条目/总数
	 */
	var percent: () -> Double = { 0.0 }

	var amountDelta: () -> Double = { 6.0 }

	open val shouldRender: Boolean get() = maxAmount() > 0

	open operator fun minusAssign(amount: Double) {
		this.amount -= amount
	}

	open operator fun plusAssign(amount: Double) {
		this.amount += amount
	}

	override var mouseDragging: (mouseX: Number, mouseY: Number, button: Int, deltaX: Number, deltaY: Number) -> Boolean =
		{ mouseX, mouseY, _, _, _ ->
			setAmountFromMouse(mouseX, mouseY)
			false
		}

	override var mouseClick: (mouseX: Number, mouseY: Number, button: Int) -> Boolean = { mouseX, mouseY, button ->
		super.mouseClick.invoke(mouseX, mouseY, button)
		setAmountFromMouse(mouseX, mouseY)
		false
	}

	protected open fun setAmountFromMouse(mouseX: Number, mouseY: Number) {
		val percent: Double = if (!horizontal) {
			(mouseY.D - this.y) / height
		} else {
			(mouseX.D - this.x) / width
		}
		amount = maxAmount() * percent
	}

	override var mouseScrolling: (mouseX: Number, mouseY: Number, amount: Number) -> Boolean = { _, _, amount ->
		this -= amount.D * amountDelta()
		false
	}

	override val render: (matrices: MatrixStack, delta: Number) -> Unit = { matrices, delta ->
		shouldRender.ifc {
			renderBackground(matrices, delta)
			renderBar(matrices, delta)
		}
	}

	protected open fun renderBar(matrices: MatrixStack, delta: Number) {
		if (!horizontal) {
			val height = (percent() * this.height)
			val maxScrollLength = this.height - height
			val posY = this.y + ((this.amount / this.maxAmount()) * maxScrollLength).toInt()
			drawTexture(matrices, x, posY, width, height, GuiTextures.SCROLLER_BAR_VERTICAL)
		} else {
			val width = (percent() * this.width)
			val maxScrollLength = this.width - width
			val posX = this.x + ((this.amount / this.maxAmount()) * maxScrollLength).toInt()
			drawTexture(matrices, posX, y, width, height, GuiTextures.SCROLLER_BAR_HORIZONTAL)
		}
	}

	protected open fun renderBackground(matrices: MatrixStack, delta: Number) {
		drawTexture(matrices, x, y, width, height, GuiTextures.SCROLLER_BACKGROUND)
	}

}

inline fun ParentElement.scrollerBar(
	width: Int = 8,
	height: Int = 8,
	horizontal: Boolean = false,
	noinline percent: () -> Double,
	noinline maxAmount: () -> Double,
	noinline amountConsumer: (Double) -> Unit = {},
	noinline amountDelta: () -> Double = { 1.0 },
	scope: ScrollerBar.() -> Unit = {}
): ScrollerBar {
	return this.addElement(ScrollerBar().apply {
		this.width = width
		this.height = height
		this.horizontal = horizontal
		this.percent = percent
		this.maxAmount = maxAmount
		this.amountDelta = amountDelta
		this.amountConsumer = amountConsumer
		scope()
	})
}