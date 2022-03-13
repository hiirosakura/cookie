package org.hiirosakura.cookie.mod

import org.hiirosakura.cookie.api.ModInfo
import org.hiirosakura.cookie.common.Initializable
import org.hiirosakura.cookie.common.isDevEnv
import org.hiirosakura.cookie.gui.foundation.drawOutlinedBox
import org.hiirosakura.cookie.gui.foundation.layout.Padding
import org.hiirosakura.cookie.gui.foundation.layout.column
import org.hiirosakura.cookie.gui.foundation.layout.row
import org.hiirosakura.cookie.gui.foundation.rememberListOf
import org.hiirosakura.cookie.gui.foundation.rememberValueOf
import org.hiirosakura.cookie.gui.screen.ScreenManager.openScreen
import org.hiirosakura.cookie.gui.screen.simpleScreen
import org.hiirosakura.cookie.gui.widget.ScrollerBar
import org.hiirosakura.cookie.gui.widget.button.button
import org.hiirosakura.cookie.input.InputHandler
import org.hiirosakura.cookie.input.KeyBind
import org.hiirosakura.cookie.platform.MultiPlatformFun
import org.hiirosakura.cookie.util.color.Color4f
import org.lwjgl.glfw.GLFW


/**
 *

 * 项目名 cookie

 * 包名 org.hiirosakura.cookie.mod

 * 文件名 Cookie

 * 创建时间 2022/2/17 15:44

 * @author forpleuvoir

 */
object Cookie : ModInfo, Initializable {

	override val id: String
		get() = "cookie"
	override val name: String
		get() = MultiPlatformFun.getModName()
	override val version: String
		get() = MultiPlatformFun.getModVersion()

	override fun initialize() {
		InputHandler.register(KeyBind(GLFW.GLFW_KEY_I) {
			openScreen {
				simpleScreen {
					row(
						padding = Padding(2.0, 2.0, 2.0, 2.0)
					) {
						if (isDevEnv)
							render = { matrices, _ ->
								drawOutlinedBox(matrices, x, y, width, height, Color4f.RED.alpha(0.6f), Color4f.WHITE, innerOutline = true)
							}
						var text by rememberValueOf("text", "a ")
						var active by rememberValueOf("active", true) {
							println("当前状态$it")
						}
						var clickCounter by rememberValueOf("clickCounter", 0)
						val list = rememberListOf("list", ArrayList<String>().apply {
							for (i in 0..5) {
								add("按钮$i")
							}
						})
						button(
							text = text,
							onClick = {
								println("我点击了按钮")
								active = !active
							}
						) {
							this.active = active
						}
						button(
							text = "测试按钮eeeeeeeee",
							onClick = {
								println("我点击了按钮")
								clickCounter++
								text = "我超$clickCounter"
							}
						) {
							margin(left = 2.0)
						}
						var padding by rememberValueOf("padding", 2.0)
						column(padding = Padding().apply {
							horizontal = padding
							vertical = padding
						}) {
							if (isDevEnv)
								render = { matrices, _ ->
									drawOutlinedBox(matrices, x, y, width, height, Color4f.BLUE.alpha(0.6f), Color4f.WHITE)
								}
							row {
								if (isDevEnv)
									render = { matrices, _ ->
										drawOutlinedBox(matrices, x, y, width, height, Color4f.GREEN.alpha(0.6f), Color4f.WHITE)
									}
								button(
									text = "测试按钮ddddddddd",
									onClick = {
										println("我点击了添加按钮")
									}
								)
								this.addElement(ScrollerBar().apply {
									width = 8
									height = 120
									horizontal = false
									maxAmount = { 200.0 }
									percent = { 0.1 }
								})
							}
							this.addElement(ScrollerBar().apply {
								width = 120
								height = 8
								horizontal = true
								maxAmount = { 200.0 }
								percent = { 0.1 }
								amountDelta = 2.0
							})
							button(
								text = "测试按钮ddddddddd",
								onClick = {
									println("我点击了添加按钮")
									active = !active
									list.add("按钮${list.size}")
									padding += 1.0
								}
							)
							list.forEachIndexed { index, element ->
								button(
									text = element,
									onClick = {
										println("我是按钮${index}")
									}
								)
							}
						}
					}

				}
			}
		})
	}


}

