package org.hiirosakura.cookie.common

import org.hiirosakura.cookie.mod.cookieMod

/**
 *

 * 项目名 cookie

 * 包名 org.hiirosakura.cookie.common

 * 文件名 CookieLogger

 * 创建时间 2022/2/16 15:03

 * @author forpleuvoir

 */
class CookieLogger(clazz: Class<*>) : ModLogger(clazz, cookieMod) {

	companion object {

		@JvmStatic
		fun getLogger(clazz: Class<*>): CookieLogger {
			return CookieLogger(clazz)
		}
	}
}