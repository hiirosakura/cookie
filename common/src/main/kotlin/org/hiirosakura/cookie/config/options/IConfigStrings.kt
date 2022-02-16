package org.hiirosakura.cookie.config.options

import org.hiirosakura.cookie.config.Config

/**
 *

 * 项目名 cookie

 * 包名 org.hiirosakura.cookie.config.options

 * 文件名 IConfigStrings

 * 创建时间 2022/2/16 19:16

 * @author forpleuvoir

 */
interface IConfigStrings : Config<MutableList<String>> {

	fun add(element: String)

	operator fun get(index: Int): String

	operator fun set(index: Int, element: String)

	fun remove(index: Int)

	fun remove(element: String)
}