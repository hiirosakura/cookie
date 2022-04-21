package org.hiirosakura.cookie.common

/**
 *

 * 项目名 cookie

 * 包名 org.hiirosakura.cookie.common

 * 文件名 Matchable

 * 创建时间 2022/2/16 14:38

 * @author forpleuvoir

 */
interface Matchable {

	/**
	 * 匹配
	 * @param regex 正则表达式
	 * @return 是否匹配成功
	 */
	infix fun matched(regex: Regex): Boolean

}