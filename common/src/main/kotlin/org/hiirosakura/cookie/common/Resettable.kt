package org.hiirosakura.cookie.common

/**
 *

 * 项目名 cookie

 * 包名 org.hiirosakura.cookie.common

 * 文件名 Resettable

 * 创建时间 2022/2/16 14:24

 * @author forpleuvoir

 */
interface Resettable {

	/**
	 * 是否为默认值
	 */
	val isDefault: Boolean

	/**
	 * 重置为默认值
	 */
	fun resetDefault()
}