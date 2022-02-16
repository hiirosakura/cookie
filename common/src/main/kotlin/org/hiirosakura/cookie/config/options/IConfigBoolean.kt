package org.hiirosakura.cookie.config.options

import org.hiirosakura.cookie.common.Toggleable
import org.hiirosakura.cookie.config.Config

/**
 *

 * 项目名 cookie

 * 包名 org.hiirosakura.cookie.config.options

 * 文件名 ConfigBoolean

 * 创建时间 2022/2/16 16:12

 * @author forpleuvoir

 */
interface IConfigBoolean : Toggleable, Config<Boolean> {

	override fun toggle() {
		this.setValue(!this.getValue())
	}

}