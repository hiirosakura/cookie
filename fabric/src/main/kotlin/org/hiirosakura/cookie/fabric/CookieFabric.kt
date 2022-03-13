package org.hiirosakura.cookie.fabric

import net.fabricmc.api.ClientModInitializer
import org.hiirosakura.cookie.mod.Cookie
import javax.script.ScriptEngineManager

/**
 *

 * 项目名 cookie

 * 包名 org.hiirosakura.cookie.fabric

 * 文件名 CookieFabric

 * 创建时间 2022/2/16 12:55

 * @author forpleuvoir

 */
object CookieFabric : ClientModInitializer {

	override fun onInitializeClient() {
		Cookie.initialize()
		val scriptEngine = ScriptEngineManager().getEngineByName("nashorn")
		scriptEngine.eval(
			"""
			var i = 0;
			i++;
		
		""".trimIndent()
		)
	}


}