package org.hiirosakura.cookie.forge

import net.minecraftforge.fml.common.Mod
import org.hiirosakura.cookie.mod.Cookie
import javax.script.ScriptEngineManager

/**
 *

 * 项目名 cookie

 * 包名 org.hiirosakura.cookie.forge

 * 文件名 CookieForge

 * 创建时间 2022/2/16 4:41

 * @author forpleuvoir

 */
@Mod("cookie")
object CookieForge {

	init {
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