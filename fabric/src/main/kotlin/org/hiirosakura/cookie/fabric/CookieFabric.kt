package org.hiirosakura.cookie.fabric

import net.fabricmc.api.ClientModInitializer
import net.fabricmc.loader.api.FabricLoader
import org.hiirosakura.cookie.Test
import org.hiirosakura.cookie.api.ModInfo

/**
 *

 * 项目名 cookie

 * 包名 org.hiirosakura.cookie.fabric

 * 文件名 CookieFabric

 * 创建时间 2022/2/16 12:55

 * @author forpleuvoir

 */
object CookieFabric : ClientModInitializer, ModInfo {

	override val id: String
		get() = "cookie"

	override val name: String
		get() = FabricLoader.getInstance().getModContainer("cookie").get().metadata.name

	override val version: String
		get() = FabricLoader.getInstance().getModContainer("cookie").get().metadata.version.friendlyString

	override fun onInitializeClient() {
		Test.test()
	}


}