package org.hiirosakura.cookie.common

import net.minecraft.client.MinecraftClient
import net.minecraft.client.font.TextRenderer
import net.minecraft.client.sound.SoundManager

/**
 *

 * 项目名 cookie

 * 包名 org.hiirosakura.cookie.common

 * 文件名 Common

 * 创建时间 2022/2/16 20:19

 * @author forpleuvoir

 */
val mc: MinecraftClient by lazy { MinecraftClient.getInstance() }

val textRenderer: TextRenderer by lazy { mc.textRenderer }

val soundManager: SoundManager by lazy { mc.soundManager }

/**
 * 是否为开发环境
 */
var isDevEnv: Boolean = false
	set(value) {
		field = value
		println("开发环境")
	}