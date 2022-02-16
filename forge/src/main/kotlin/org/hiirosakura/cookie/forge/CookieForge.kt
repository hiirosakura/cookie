package org.hiirosakura.cookie.forge

import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent
import net.minecraftforge.fml.loading.FMLLoader
import org.hiirosakura.cookie.Test
import org.hiirosakura.cookie.api.ModInfo
import thedarkcolour.kotlinforforge.forge.MOD_BUS
import thedarkcolour.kotlinforforge.forge.runWhenOn

/**
 *

 * 项目名 cookie

 * 包名 org.hiirosakura.cookie.forge

 * 文件名 CookieForge

 * 创建时间 2022/2/16 4:41

 * @author forpleuvoir

 */
@Mod("cookie")
object CookieForge : ModInfo {

	override val id: String
		get() = "cookie"
	override val name: String
		get() = FMLLoader.getLoadingModList().mods.find { it.modId == id }!!.displayName
	override val version: String
		get() = FMLLoader.getLoadingModList().mods.find { it.modId == id }!!.version.majorVersion.toString()

	init {
		runWhenOn(Dist.CLIENT) {
			MOD_BUS.addListener(CookieForge::onClientSetup)
		}
	}

	private fun onClientSetup(event: FMLClientSetupEvent) {
		Test.test()
	}


}