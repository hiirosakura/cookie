package org.hiirosakura.cookie.common

import net.minecraft.text.Text
import org.hiirosakura.cookie.util.tText

/**
 *

 * 项目名 cookie

 * 包名 org.hiirosakura.cookie.common

 * 文件名 MatchMode

 * 创建时间 2022/2/16 19:59

 * @author forpleuvoir

 */
enum class MatchMode(override val key: String) : Option {

	WhiteList("white_list"),
	BlackList("black_list"),
	None("none");

	override val displayKey: Text
		get() = "cookie.match_mode.${key}".tText

	override val allOption: List<MatchMode>
		get() = values().toList()

	override fun fromKey(key: String): MatchMode {
		allOption.forEach {
			if (it.key == key) return it
		}
		return None
	}
}