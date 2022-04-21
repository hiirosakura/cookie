package org.hiirosakura.cookie.util

import net.minecraft.client.resource.language.I18n
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText
import org.hiirosakura.cookie.common.textRenderer
import java.util.*

/**
 *

 * 项目名 cookie

 * 包名 org.hiirosakura.cookie.util

 * 文件名 TextUtil

 * 创建时间 2022/2/16 16:22

 * @author forpleuvoir

 */

val String.text: Text get() = Text.of(this)


@JvmName("translatableText")
fun tText(string: String, vararg args: Any?): TranslatableText {
	return TranslatableText(string, args)
}

fun String.tText(vararg args: Any?): TranslatableText {
	return TranslatableText(this, *args)
}

val String.tText: TranslatableText get() = TranslatableText(this)

fun String.tString(vararg args: Any?): String {
	return I18n.translate(this, *args)
}

val String.tString: TranslatableText get() = TranslatableText(this)

fun List<String>.maxWidth(): Int {
	var temp = 0
	for (s in this) {
		if (temp < textRenderer.getWidth(s))
			temp = textRenderer.getWidth(s)
	}
	return temp
}

@JvmName("textMaxWidth")
fun List<Text>.maxWidth(): Int {
	var temp = 0
	for (t in this) {
		if (temp < textRenderer.getWidth(t))
			temp = textRenderer.getWidth(t)
	}
	return temp
}

fun String.wrapToLines(width: Int = 0): List<String> {
	val texts: LinkedList<String> = LinkedList()
	var temp = StringBuilder()
	for (element in this) {
		run {
			if (element != '\n') {
				if (width == 0) return@run
				if (textRenderer.getWidth(temp.toString() + element) <= width) return@run
			}
			texts.add(temp.toString())
			temp = StringBuilder()
		}
		if (element != '\n') {
			temp.append(element)
		}
	}
	texts.add(temp.toString())
	return texts
}

fun Text.wrapToLines(width: Int = 0): List<Text> {
	val texts: LinkedList<Text> = LinkedList()
	this.string.wrapToLines(width).forEach { texts.add(it.text) }
	return texts
}

fun List<String>.wrapToSingleText(width: Int = 0): String {
	val str = StringBuilder()
	this.forEachIndexed { index, text ->
		val wrapToLines = text.wrapToLines(width)
		wrapToLines.forEachIndexed { i, t ->
			str.append(t)
			if (i != wrapToLines.size - 1) str.append("\n")
		}
		if (index != this.size - 1) str.append("\n")
	}
	return str.toString()
}

fun List<Text>.wrapToSingleText(width: Int = 0): Text {
	val str = StringBuilder()
	this.forEachIndexed { index, text ->
		val wrapToLines = text.wrapToLines(width)
		wrapToLines.forEachIndexed { i, t ->
			str.append(t.string)
			if (i != wrapToLines.size - 1) str.append("\n")
		}
		if (index != this.size - 1) str.append("\n")
	}
	return str.toString().text
}

fun List<Text>.wrapToLines(width: Int = 0): List<Text> {
	val texts: LinkedList<Text> = LinkedList()
	for (text in this) {
		texts.addAll(text.wrapToLines(width))
	}
	return texts
}