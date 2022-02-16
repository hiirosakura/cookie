package org.hiirosakura.cookie.common

import net.minecraft.text.Text
import org.hiirosakura.cookie.util.tText

/**
 *

 * 项目名 cookie

 * 包名 org.hiirosakura.cookie.common

 * 文件名 Option

 * 创建时间 2022/2/16 19:45

 * @author forpleuvoir

 */
interface Option {

	val key: String

	val displayKey: Text get() = key.tText

	val remark: Text get() = "${key}.remark".tText

	fun cycle(): Option {
		val size = allOption.size
		val index = allOption.indexOf(this)
		return if (index < size - 1) {
			allOption[index + 1]
		} else {
			allOption[0]
		}
	}

	val allOption: List<Option>

	fun fromKey(key: String): Option

}