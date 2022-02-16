package org.hiirosakura.cookie.config

/**
 *

 * 项目名 cookie

 * 包名 org.hiirosakura.cookie.config

 * 文件名 ConfigHandler

 * 创建时间 2022/2/16 14:47

 * @author forpleuvoir

 */
interface ConfigHandler {

	fun load()

	fun save()

	fun onChanged() {
		save()
		load()
	}

	val allConfig: Collection<Config<*>>
}