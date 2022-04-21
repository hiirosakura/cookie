package org.hiirosakura.cookie.config

import net.minecraft.text.Text
import org.hiirosakura.cookie.common.*

/**
 *

 * 项目名 cookie

 * 包名 org.hiirosakura.cookie.config

 * 文件名 Config

 * 创建时间 2022/2/16 14:23

 * @author forpleuvoir

 */
interface Config<T> : ConfigValue<T>, Initialization, Resettable, Notifiable<Config<T>>, Matchable, JsonData {

	val type: IConfigType

	val key: String

	val displayKey: Text

	val remark: Text

}