package org.hiirosakura.cookie.initialize

import org.hiirosakura.cookie.config.ConfigManager
import org.hiirosakura.cookie.gui.texture.GuiTextures

/**
 * 客户端初始化

 * 项目名 cookie

 * 包名 org.hiirosakura.cookie.initialize

 * 文件名 _Initialize

 * 创建时间 2022/2/17 12:39

 * @author forpleuvoir

 */


/**
 * 初始化中
 */
@JvmName("initialing")
fun initialing() {
	println("Cookie initialing...")

	ConfigManager.initialize()
	GuiTextures.initialize()
}

/**
 * 初始化完成
 */
@JvmName("initialized")
fun initialized() {
	ConfigManager.load()

	println("Cookie initialed...")
}