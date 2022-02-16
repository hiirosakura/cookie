package org.hiirosakura.cookie.util

import net.minecraft.client.resource.language.I18n
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText

/**
 *

 * 项目名 cookie

 * 包名 org.hiirosakura.cookie.util

 * 文件名 TextUtil

 * 创建时间 2022/2/16 16:22

 * @author forpleuvoir

 */

val String.text: Text get() = Text.of(this)

fun String.tText(vararg args: Any?): TranslatableText {
	return TranslatableText(this, *args)
}

val String.tText: TranslatableText get() = TranslatableText(this)

fun String.tString(vararg args: Any?): String {
	return I18n.translate(this, *args)
}

val String.tString: TranslatableText get() = TranslatableText(this)