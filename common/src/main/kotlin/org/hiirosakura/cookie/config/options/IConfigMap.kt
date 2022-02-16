package org.hiirosakura.cookie.config.options

import org.hiirosakura.cookie.config.Config

/**
 *

 * 项目名 cookie

 * 包名 org.hiirosakura.cookie.config.options

 * 文件名 IConfigMap

 * 创建时间 2022/2/17 1:42

 * @author forpleuvoir

 */
interface IConfigMap<K, V> : Config<MutableMap<K, V>> {

	operator fun set(key: K, value: V)

	operator fun get(key: K): V?

	fun remove(key: K)

	fun rename(origin: K, current: K)

	fun rest(originKey: K, currentKey: K, value: V)

}