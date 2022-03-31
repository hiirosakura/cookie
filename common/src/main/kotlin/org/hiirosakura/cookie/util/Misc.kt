package org.hiirosakura.cookie.util

import net.minecraft.util.Identifier
import org.hiirosakura.cookie.mod.Cookie
import java.util.stream.Stream

/**
 *

 * 项目名 cookie

 * 包名 org.hiirosakura.cookie.util

 * 文件名 Misc

 * 创建时间 2022/2/19 0:40

 * @author forpleuvoir

 */


fun resources(path: String): Identifier = Identifier(Cookie.id, path)

/**
 * 如果是的话执行action
 * @receiver Boolean
 * @param action [@kotlin.ExtensionFunctionType] Function1<Boolean, Unit>
 */
inline fun Boolean?.ifc(action: () -> Unit) = if (this != null && this) action() else Unit

/**
 * 如果否的话执行action
 * @receiver Boolean
 * @param action [@kotlin.ExtensionFunctionType] Function1<Boolean, Unit>
 */
inline fun Boolean?.notc(action: () -> Unit) = if (this != null) {
	if (!this) action() else Unit
} else Unit


/**
 * forEach
 * @receiver Iterable<T>
 * @param action [@kotlin.ExtensionFunctionType] Function1<T, Unit>
 */
inline fun <T> Iterable<T>.fe(action: T.() -> Unit) {
	for (element in this) action(element)
}


/**
 * forEach
 * @receiver Iterable<T>
 * @param action [@kotlin.ExtensionFunctionType] Function1<T, Unit>
 */
inline fun <T> List<T>.fe(action: T.() -> Unit) {
	for (element in this) action(element)
}


/**
 * forEach
 * @receiver Iterable<T>
 * @param action [@kotlin.ExtensionFunctionType] Function1<T, Unit>
 */
fun <T> Stream<T>.fe(action: T.() -> Unit) {
	this.forEach { it.action() }
}
