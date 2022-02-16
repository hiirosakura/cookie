package org.hiirosakura.cookie.common

/**
 *

 * 项目名 cookie

 * 包名 org.hiirosakura.cookie.common

 * 文件名 Notifiable

 * 创建时间 2022/2/16 14:29

 * @author forpleuvoir

 */
interface Notifiable<T> {

	/**
	 * 变动时调用
	 */
	fun onChanged()

	/**
	 * 设置回调
	 * @param callback Function1<T, Unit>
	 */
	fun setOnChangedCallback(callback: T.() -> Unit)

}