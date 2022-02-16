package org.hiirosakura.cookie.common

import net.minecraft.client.resource.language.I18n
import net.minecraft.text.TranslatableText

/**
 *

 * 项目名 cookie

 * 包名 org.hiirosakura.cookie.common

 * 文件名 ModLang

 * 创建时间 2022/2/16 13:32

 * @author forpleuvoir

 */
interface ModLang {

	val key: String

	val modId: String

	fun tText(vararg args: Any?): TranslatableText {
		return TranslatableText("$modId$key", args)
	}

	val tText: TranslatableText get() = TranslatableText("$modId$key")

	fun tString(vararg args: Any?): String {
		return I18n.translate("$modId.$key", *args)
	}

	val tString: TranslatableText get() = TranslatableText("$modId$key")
}


