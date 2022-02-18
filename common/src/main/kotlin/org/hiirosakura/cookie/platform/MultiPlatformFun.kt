package org.hiirosakura.cookie.platform

import dev.architectury.injectables.annotations.ExpectPlatform
import java.nio.file.Path

/**
 * 多平台方法

 * 项目名 cookie

 * 包名 org.hiirosakura.cookie.platform

 * 文件名 MultiPlatformFun

 * 创建时间 2022/2/17 15:46

 * @author forpleuvoir

 */
object MultiPlatformFun {

	/**
	 * 获取Mod版本
	 * @return String
	 */
	@ExpectPlatform
	@JvmStatic
	fun getModVersion(): String {
		throw AssertionError()
	}

	/**
	 * 获取Mod名称
	 * @return String
	 */
	@ExpectPlatform
	@JvmStatic
	fun getModName(): String {
		throw AssertionError()
	}


	/**
	 * 获取配置文件路径
	 * @return Path
	 */
	@ExpectPlatform
	@JvmStatic
	fun configPath(): Path {
		throw AssertionError()
	}


}