package org.hiirosakura.cookie.config

/**
 *

 * 项目名 cookie

 * 包名 org.hiirosakura.cookie.config

 * 文件名 ConfigValue

 * 创建时间 2022/2/17 1:19

 * @author forpleuvoir

 */
interface ConfigValue<T> {

	val defaultValue: T

	fun setValue(value: T)

	fun getValue(): T
}