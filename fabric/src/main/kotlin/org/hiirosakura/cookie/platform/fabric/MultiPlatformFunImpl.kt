package org.hiirosakura.cookie.platform.fabric

import net.fabricmc.loader.api.FabricLoader
import java.nio.file.Path

/**
 *

 * 项目名 cookie

 * 包名 org.hiirosakura.cookie.platform.fabric

 * 文件名 MultiPlatformFunImpl

 * 创建时间 2022/2/17 15:56

 * @author forpleuvoir

 */
object MultiPlatformFunImpl {

	/**
	 * 获取Mod版本
	 * @return String
	 */
	@JvmStatic
	fun getModVersion(): String {
		return FabricLoader.getInstance().getModContainer("cookie").get().metadata.version.friendlyString
	}

	/**
	 * 获取Mod名称
	 * @return String
	 */
	@JvmStatic
	fun getModName(): String {
		return FabricLoader.getInstance().getModContainer("cookie").get().metadata.name
	}


	/**
	 * 获取配置文件路径
	 * @return Path
	 */
	@JvmStatic
	fun configPath(): Path {
		return FabricLoader.getInstance().configDir
	}
}