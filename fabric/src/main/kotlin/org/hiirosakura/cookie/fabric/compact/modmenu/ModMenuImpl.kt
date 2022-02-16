package org.hiirosakura.cookie.fabric.compact.modmenu

import com.terraformersmc.modmenu.api.ConfigScreenFactory
import com.terraformersmc.modmenu.api.ModMenuApi

/**
 *

 * 项目名 cookie

 * 包名 org.hiirosakura.cookie.fabric.compact.modmenu

 * 文件名 ModMenuImpl

 * 创建时间 2022/2/16 14:16

 * @author forpleuvoir

 */
class ModMenuImpl : ModMenuApi {

	override fun getModConfigScreenFactory(): ConfigScreenFactory<*> {
		return ConfigScreenFactory {
			return@ConfigScreenFactory null
		}
	}
}