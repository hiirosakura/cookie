package org.hiirosakura.cookie.platform.forge

import net.minecraftforge.fml.loading.FMLLoader
import net.minecraftforge.fml.loading.FMLPaths
import org.hiirosakura.cookie.mod.Cookie.id
import java.nio.file.Path

/**
 *

 * 项目名 cookie

 * 包名 org.hiirosakura.cookie.platform.forge

 * 文件名 MultiPlatformFunImpl

 * 创建时间 2022/2/17 15:51

 * @author forpleuvoir

 */
object MultiPlatformFunImpl {

	/**
	 * 获取Mod版本
	 * @return String
	 */
	@JvmStatic
	fun getModVersion(): String {
		return FMLLoader.getLoadingModList().mods.find { it.modId == id }!!.version.qualifier
	}

	/**
	 * 获取Mod名称
	 * @return String
	 */
	@JvmStatic
	fun getModName(): String {
		return FMLLoader.getLoadingModList().mods.find { it.modId == id }!!.displayName
	}


	/**
	 * 获取配置文件路径
	 * @return Path
	 */
	@JvmStatic
	fun configPath(): Path {
		return FMLPaths.CONFIGDIR.get()
	}

}