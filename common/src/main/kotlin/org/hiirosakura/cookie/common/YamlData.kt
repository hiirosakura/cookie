package org.hiirosakura.cookie.common

/**
 *

 * 项目名 cookie

 * 包名 org.hiirosakura.cookie.common

 * 文件名 YamlData

 * 创建时间 2022/4/17 15:18

 * @author forpleuvoir

 */
interface YamlData {

	val asYamlData: String

	fun setValueFromYaml(yamlData: String)
}