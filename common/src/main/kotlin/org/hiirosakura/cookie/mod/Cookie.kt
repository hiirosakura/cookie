package org.hiirosakura.cookie.mod

import org.hiirosakura.cookie.api.ModInfo
import org.hiirosakura.cookie.common.Initializable
import org.hiirosakura.cookie.common.isDevEnv
import org.hiirosakura.cookie.gui.foundation.*
import org.hiirosakura.cookie.gui.foundation.layout.*
import org.hiirosakura.cookie.gui.screen.ScreenManager.openScreen
import org.hiirosakura.cookie.gui.screen.simpleScreen
import org.hiirosakura.cookie.gui.widget.button.button
import org.hiirosakura.cookie.gui.widget.dropMenu
import org.hiirosakura.cookie.input.InputHandler
import org.hiirosakura.cookie.input.KeyBind
import org.hiirosakura.cookie.platform.MultiPlatformFun
import org.hiirosakura.cookie.util.Direction
import org.hiirosakura.cookie.util.color.Color4f
import org.hiirosakura.cookie.util.text
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
					renderWith = { matrices, delta ->
						drawCenteredText(matrices, "x:$mouseX,y:$mouseY".text, this.x, this.y, this.width, this.height, false)
						render.invoke(matrices, delta)
					}
					val list = rememberListOf("list", ArrayList<String>().apply {
						for (i in 1..8) {
							add("按钮${i}")
						}
					})
					dropMenu(list, list[0]) {
						fixed = true
						x = 300.0
						y = 20.0
						itemTip = { "${it}的tip".text }
						itemTipDirection = { Direction.Left }
					}
					column(
						padding = Padding(top = 30.0)
					) {
						if (isDevEnv) {
							renderWith = { matrices, delta ->
								drawRect(matrices, this.x, this.y, this.width, this.height, Color4f.RED.alpha(0.5f))
								render.invoke(matrices, delta)
							}
						}
						row {
							button("添加", onClick = {
								list.add("按钮${list.size + 1}")
							})
						}
						list(
							width = 120,
							height = 200,
							padding = Padding(4.0),
							margin = Margin(4.0)
						) {
							if (isDevEnv) {
								renderWith = { matrices, delta ->
									drawRect(matrices, this.x, this.y, this.width, this.height, Color4f.GREEN.alpha(0.5f))
									render.invoke(matrices, delta)
								}
							}
							dropMenu(list, list[0]) {
								fixed = true
								x = 20.0
								y = 40.0
								itemTip = { "${it}的tip".text }
								itemTipDirection = { Direction.Left }
							}
							for (s in list) {
								button(
									text = s,
									onClick = {
										println("我是${text.string}")
									}
								) {
									if (s == "按钮5") tip = {
										"""
										|测试啊
										|超长的文本测试................
										|超长的文本测试2................${'\n'}换行
										""".trimMargin("|").text
									}
									this.margin.set(Margin(top = 2.0))
								}
							}
						}
						list(
							width = 200,
							height = 40,
							horizontal = true,
							padding = Padding(4.0),
							margin = Margin(4.0)
						) {
							if (isDevEnv) {
								renderWith = { matrices, delta ->
									drawRect(matrices, this.x, this.y, this.width, this.height, Color4f.GREEN.alpha(0.5f))
									render.invoke(matrices, delta)
								}
							}
							for (s in list) {
								button(s, onClick = {
									println("我是${text.string}")
								}) {
									this.margin.set(Margin(left = 2.0))
								}
							}
						}
					}
				}
			}
		})
	}


}

