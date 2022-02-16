package org.hiirosakura.cookie.config.options

import org.hiirosakura.cookie.common.Option
import org.hiirosakura.cookie.common.Toggleable
import org.hiirosakura.cookie.config.Config

/**
 *

 * 项目名 cookie

 * 包名 org.hiirosakura.cookie.config.options

 * 文件名 IConfigOptions

 * 创建时间 2022/2/16 19:43

 * @author forpleuvoir

 */
interface IConfigOptions : Config<Option>, Toggleable {

	override fun toggle() {
		this.setValue(this.getValue().cycle())
	}
}