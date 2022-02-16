package org.hiirosakura.cookie.common

import org.hiirosakura.cookie.api.ModInfo
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 *

 * 项目名 cookie

 * 包名 org.hiirosakura.cookie.common

 * 文件名 ModLogger

 * 创建时间 2022/2/16 13:26

 * @author forpleuvoir

 */
open class ModLogger(clazz: Class<*>, modInfo: ModInfo) {

	private val log: Logger

	init {
		log = LoggerFactory.getLogger("${modInfo.name}[${clazz.simpleName}]")
	}

	fun info(str: String, vararg params: Any?) {
		log.info(str, *params)
	}

	fun error(str: String, vararg params: Any?) {
		log.error(str, *params)
	}

	fun error(e: Exception) {
		log.error(e.message, e)
	}

	fun warn(str: String, vararg params: Any?) {
		log.warn(str, *params)
	}
}