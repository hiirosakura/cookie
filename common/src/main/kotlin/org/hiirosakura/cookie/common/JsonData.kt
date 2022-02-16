package org.hiirosakura.cookie.common

import com.google.gson.JsonElement

/**
 *

 * 项目名 cookie

 * 包名 org.hiirosakura.cookie.common

 * 文件名 JsonData

 * 创建时间 2022/2/16 13:35

 * @author forpleuvoir

 */
interface JsonData {

	/**
	 * 从JsonElement中获取数据
	 * @param jsonElement JsonElement
	 */
	fun setValueFromJsonElement(jsonElement: JsonElement)

	/**
	 * 转换为JsonElement
	 * @return JsonElement
	 */
	val asJsonElement: JsonElement

}