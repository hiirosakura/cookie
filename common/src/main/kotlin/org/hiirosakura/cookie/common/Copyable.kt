package org.hiirosakura.cookie.common

/**
 *

 * 项目名 cookie

 * 包名 org.hiirosakura.cookie.common

 * 文件名 Copyable

 * 创建时间 2022/2/16 23:19

 * @author forpleuvoir

 */
interface Copyable<T> {

	/**
	 * 复制自 target
	 * @param target T
	 * @return Boolean 是否有变化
	 */
	fun copyOf(target: T): Boolean
}