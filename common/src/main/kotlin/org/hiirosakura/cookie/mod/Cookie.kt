package org.hiirosakura.cookie.mod

import org.hiirosakura.cookie.api.ModInfo
import org.hiirosakura.cookie.common.Initializable
import org.hiirosakura.cookie.platform.MultiPlatformFun
import javax.script.ScriptEngineManager


/**
 *

 * 项目名 cookie

 * 包名 org.hiirosakura.cookie.mod

 * 文件名 Cookie

 * 创建时间 2022/2/17 15:44

 * @author forpleuvoir

 */
object Cookie : ModInfo, Initializable {

	override val id: String
		get() = "cookie"
	override val name: String
		get() = MultiPlatformFun.getModName()
	override val version: String
		get() = MultiPlatformFun.getModVersion()

	override fun initialize() {
		println(id)
		println(name)
		println(version)
		var haveNashorn = false
		val engine = ScriptEngineManager().getEngineByName("javascript")
		engine?.let {
			engine.put("_this", this)
			engine.eval(
				"""
				_this.sout();
				""".trimIndent()
			)
			haveNashorn = true
		}
		if (!haveNashorn)
			println("没找到javascript")
	}

	fun sout() {
		println("找到了javascript")
	}
}