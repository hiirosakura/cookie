package org.hiirosakura.cookie.gui.foundation

import net.minecraft.text.Text
import org.hiirosakura.cookie.common.Tickable
import org.hiirosakura.cookie.common.mc
import org.hiirosakura.cookie.gui.foundation.layout.Margin
import org.hiirosakura.cookie.gui.foundation.layout.Padding
import org.hiirosakura.cookie.util.Direction
import org.hiirosakura.cookie.util.ifc
import org.hiirosakura.cookie.util.math.D
import org.hiirosakura.cookie.util.math.Vector3
import java.util.stream.Stream


/**
 *

 * 项目名 cookie

 * 包名 org.hiirosakura.cookie.gui.foundation

 * 文件名 Element

 * 创建时间 2022/2/18 23:39

 * @author forpleuvoir

 */
interface Element : Drawable, PositionElement, Tickable {

	/**
	 * 位置
	 */
	val position: Vector3<Double>

	override var visible: Boolean

	/**
	 * 固定的
	 *
	 * 被固定的元素不会被父元素主动调整位置
	 */
	val fixed: Boolean get() = false

	var x: Double
		get() = position.x
		set(value) {
			position.x = value
		}

	var y: Double
		get() = position.y
		set(value) {
			position.y = value
		}

	var z: Double
		get() = position.z
		set(value) {
			position.z = value
		}

	override var zOffset: Double
		get() = position.z
		set(value) {
			position.z = value
		}

	/**
	 * 宽
	 */
	var width: Int

	/**
	 * 高
	 */
	var height: Int

	/**
	 * 提示
	 */
	var tip: () -> Text?

	fun isEmptyTip(): Boolean {
		tip()?.let { return it.string.isEmpty() }
		return true
	}

	/**
	 * 提示出现的位置 为空则自动选择合适的位置 优先级 up->right->down->left
	 */
	var tipDirection: () -> Direction?

	/**
	 * 左侧的位置
	 */
	val left: Number get() = position.x

	/**
	 * 右侧位置
	 */
	val right: Number get() = position.x.D + width.D

	/**
	 * 顶部位置
	 */
	val top: Number get() = position.y

	/**
	 * 底部位置
	 */
	val bottom: Number get() = position.y.D + height.D

	val margin: Margin

	val padding: Padding

	/**
	 * 可用的
	 */
	val active: Boolean get() = true

	/**
	 * 处理及渲染优先级 值越大优先度越高
	 */
	val handleLevel: Int get() = 0

	var parent: ParentElement?

	/**
	 * 鼠标移动
	 * @param mouseX Number
	 * @param mouseY Number
	 */
	fun mouseMove(mouseX: Number, mouseY: Number) {}

	/**
	 * 鼠标是否在此元素[Element]内部
	 * @param mouseX Number
	 * @param mouseY Number
	 * @return Boolean
	 */
	fun isMouseOvered(mouseX: Number, mouseY: Number): Boolean =
		mouseX.D >= this.left.D && mouseX.D <= this.right.D && mouseY.D >= this.top.D && mouseY.D <= this.bottom.D

	/**
	 * 鼠标点击
	 * @param button Int
	 * @param mouseX Number
	 * @param mouseY Number
	 * @return 是否处理之后的同类操作
	 */
	fun mouseClick(mouseX: Number, mouseY: Number, button: Int): Boolean = true

	/**
	 * 鼠标释放
	 * @param button Int
	 * @param mouseX Number
	 * @param mouseY Number
	 * @return 是否处理之后的同类操作
	 */
	fun mouseRelease(mouseX: Number, mouseY: Number, button: Int): Boolean = true

	/**
	 * 鼠标拖动
	 * @param mouseX Number
	 * @param mouseY Number
	 * @param button Int
	 * @param deltaX Number
	 * @param deltaY Number
	 * @return 是否处理之后的同类操作
	 */
	fun mouseDragging(mouseX: Number, mouseY: Number, button: Int, deltaX: Number, deltaY: Number): Boolean = true

	/**
	 * 鼠标滚动
	 * @param mouseX Number
	 * @param mouseY Number
	 * @param amount Number
	 * @return 是否处理之后的同类操作
	 */
	fun mouseScrolling(mouseX: Number, mouseY: Number, amount: Number): Boolean = true

	/**
	 * 按键按下
	 * @param keyCode Int
	 * @param modifiers Int
	 * @return 是否处理之后的同类操作
	 */
	fun keyPress(keyCode: Int, modifiers: Int): Boolean = true

	/**
	 * 按键按下
	 * @param keyCode Int
	 * @param modifiers Int
	 * @return 是否处理之后的同类操作
	 */
	fun keyRelease(keyCode: Int, modifiers: Int): Boolean = true

	/**
	 * 字符输入
	 * @param chr Char
	 * @param modifiers Int
	 * @return 是否处理之后的同类操作
	 */
	fun charTyped(chr: Char, modifiers: Int): Boolean = true

	override fun setPosition(x: Number, y: Number, z: Number) {
		this.position.set(x.D, y.D, z.D)
	}

	override fun deltaPosition(deltaX: Number, deltaY: Number, deltaZ: Number) {
		this.position.plusAssign(deltaX.D, deltaY.D, deltaZ.D)
	}

	override fun tick() {
		if (!active) return
	}
}

val mouseX: Double get() = mc.mouse.x * mc.window.scaledWidth.D / mc.window.width.D

val mouseY: Double get() = mc.mouse.y * mc.window.scaledHeight.D / mc.window.height.D

/**
 * 当鼠标位于此元素[Element]内部时调用
 * @param action [@kotlin.ExtensionFunctionType] Function1<Element, Unit>
 */
inline fun <T : Element> T.mouseHover(action: T.() -> Unit) = isMouseOvered(mouseX, mouseY).ifc { action() }

/**
 * 鼠标是否在此元素内
 * @receiver T
 * @return Boolean
 */
fun <T : Element> T.mouseHover(): Boolean = isMouseOvered(mouseX, mouseY)

/**
 * 转换为排序后的元素
 * @receiver Collection<Element>
 * @return Stream<Element>?
 */
fun Collection<Element>.sort(): Stream<Element> {
	return this.stream().sorted { o1, o2 -> o1.handleLevel - o2.handleLevel }
}

/**
 * 转换为反向排序后的元素
 * @receiver Collection<Element>
 * @return Stream<Element>?
 */
fun Collection<Element>.reverseOrder(): Stream<Element> {
	return this.stream().sorted { o1, o2 -> o2.handleLevel - o1.handleLevel }
}

/**
 * 转换为排序后的元素
 * @receiver Collection<Element>
 * @return Stream<Element>?
 */
fun Collection<Drawable>.drawableSort(): Stream<Drawable> {
	return this.stream().sorted { o1, o2 -> o1.renderLevel - o2.renderLevel }
}

/**
 * 转换为反向排序后的元素
 * @receiver Collection<Element>
 * @return Stream<Element>?
 */
fun Collection<Drawable>.drawableReverseOrder(): Stream<Drawable> {
	return this.stream().sorted { o1, o2 -> o2.renderLevel - o1.renderLevel }
}

