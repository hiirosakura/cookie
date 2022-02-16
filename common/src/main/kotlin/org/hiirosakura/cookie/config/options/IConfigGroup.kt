package org.hiirosakura.cookie.config.options

import org.hiirosakura.cookie.config.Config

/**
 *

 * 项目名 cookie

 * 包名 org.hiirosakura.cookie.config.options

 * 文件名 IConfigGroup

 * 创建时间 2022/2/16 18:10

 * @author forpleuvoir

 */
interface IConfigGroup : Config<List<Config<*>>> {

	fun getConfigFromKey(key: String): Config<*>?

	fun getKeys(): Set<String>

	fun containsKey(key: String): Boolean = getKeys().contains(key)

}