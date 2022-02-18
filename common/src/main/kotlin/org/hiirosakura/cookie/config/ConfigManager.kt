package org.hiirosakura.cookie.config

import org.hiirosakura.cookie.api.ModInfo
import org.hiirosakura.cookie.common.Initializable

/**
 *

 * 项目名 cookie

 * 包名 org.hiirosakura.cookie.config

 * 文件名 ConfigManager

 * 创建时间 2022/2/17 12:58

 * @author forpleuvoir

 */
object ConfigManager : Initializable {

	private val configHandlers: MutableMap<ModInfo, ConfigHandler> = HashMap()

	fun register(modInfo: ModInfo, configHandler: ConfigHandler) {
		configHandlers[modInfo] = configHandler
	}

	override fun initialize() {
		configHandlers.forEach { (m, c) ->
			println("${m.name} config initialing...")
			c.allConfig.forEach { it.initialize() }
		}
	}

	fun load() {
		configHandlers.forEach { (m, c) ->
			println("${m.name} config loading...")
			c.load()
		}
	}

}