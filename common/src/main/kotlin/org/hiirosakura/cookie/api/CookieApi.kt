package org.hiirosakura.cookie.api

import org.hiirosakura.cookie.config.ConfigHandler

/**
 *

 * 项目名 cookie

 * 包名 org.hiirosakura.cookie.api

 * 文件名 CookieApi

 * 创建时间 2022/2/16 14:20

 * @author forpleuvoir

 */
interface CookieApi {

	val modInfo: () -> ModInfo

	val configHandler: () -> ConfigHandler

}