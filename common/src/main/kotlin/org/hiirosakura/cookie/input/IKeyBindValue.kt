package org.hiirosakura.cookie.input

import org.hiirosakura.cookie.common.Initialization

/**
 *

 * 项目名 cookie

 * 包名 org.hiirosakura.cookie.input

 * 文件名 IKeyBindValue

 * 创建时间 2022/2/17 1:07

 * @author forpleuvoir

 */
interface IKeyBindValue : Initialization {

	fun register() {
		InputHandler.register(getKeyBind())
	}

	fun unregister() {
		InputHandler.unregister(getKeyBind())
	}

	fun getKeyBind(): KeyBind

	fun setKeyBind(keyBind: KeyBind)

	override fun initialize() {
		register()
	}

}