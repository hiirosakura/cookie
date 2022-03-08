package org.hiirosakura.cookie.gui.foundation

import net.minecraft.client.util.math.MatrixStack
import org.hiirosakura.cookie.gui.foundation.layout.Margin
import org.hiirosakura.cookie.gui.foundation.layout.Modifiable
import org.hiirosakura.cookie.gui.foundation.layout.Padding
import org.hiirosakura.cookie.gui.screen.ScreenManager
import org.hiirosakura.cookie.util.math.Vector3
import org.hiirosakura.cookie.util.math.Vector3d
import java.util.*

/**
 *

 * 项目名 cookie

 * 包名 org.hiirosakura.cookie.gui.foundation

 * 文件名 AbstractParentElement

 * 创建时间 2022/2/22 15:03

 * @author forpleuvoir

 */
abstract class AbstractParentElement : ParentElement, Modifiable {

	override val children: LinkedList<Element> = LinkedList()

	override var fixed: Boolean = false

	override var dragging: Boolean = false

	override var focused: Element? = null

	override val position: Vector3<Double> = Vector3d()

	override var parent: ParentElement? = ScreenManager.current

	override var active: Boolean = true

	override val margin: Margin = Margin()

	override fun margin(top: Double, bottom: Double, left: Double, right: Double) = margin.set(top, bottom, left, right)

	override fun margin(margin: Margin) = this.margin.set(margin)

	override val padding: Padding = Padding()

	override fun padding(top: Double, bottom: Double, left: Double, right: Double) = padding.set(top, bottom, left, right)

	override fun padding(padding: Padding) = this.padding.set(padding)

	var render: (matrices: MatrixStack, delta: Number) -> Unit = { _, _ -> }

	open var renderWith: AbstractParentElement.(matrices: MatrixStack, delta: Number) -> Unit = { matrices: MatrixStack, delta: Number ->
		render.invoke(matrices, delta)
		super.render(matrices, delta)
	}

	override fun render(matrices: MatrixStack, delta: Number) = renderWith(matrices, delta)
}