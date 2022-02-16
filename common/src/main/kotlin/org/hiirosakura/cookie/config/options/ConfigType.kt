package org.hiirosakura.cookie.config.options

import org.hiirosakura.cookie.config.IConfigType

/**
 *

 * 项目名 cookie

 * 包名 org.hiirosakura.cookie.config.options

 * 文件名 ConfigType

 * 创建时间 2022/2/16 16:28

 * @author forpleuvoir

 */
enum class ConfigType : IConfigType {

	BOOLEAN,
	INTEGER,
	DOUBLE,
	COLOR,
	STRING,
	STRINGS,
	OPTIONS,
	HOTKEY,
	GROUP,
	MAP,
	BOOLEAN_WITH_KEY_BIND
	;

	override val type: String
		get() = name
}