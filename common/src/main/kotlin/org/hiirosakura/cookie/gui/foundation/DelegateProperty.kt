package org.hiirosakura.cookie.gui.foundation

import org.hiirosakura.cookie.gui.screen.Screen
import java.util.function.BiFunction
import java.util.function.Predicate
import java.util.function.UnaryOperator
import kotlin.reflect.KProperty

/**
 *

 * 项目名 cookie

 * 包名 org.hiirosakura.cookie.gui.foundation

 * 文件名 DelegateProperty

 * 创建时间 2022/2/22 18:34

 * @author forpleuvoir

 */

interface Delegate {

	fun onChanged()
}

class MutableValue<T>(value: T, val key: String, private val screen: Screen) : Delegate {

	@Suppress("UNCHECKED_CAST")
	operator fun getValue(thisRef: Any?, kProperty: KProperty<*>): T {
		return screen.remembers[key] as T
	}

	operator fun setValue(thisRef: Any?, kProperty: KProperty<*>, value: T) {
		if (screen.remembers[key] != value) {
			screen.remembers[key] = value
			onChanged()
		}
	}

	override fun onChanged() {
		screen.initialize()
	}

}

class DelegateMutableList<T>(private val value: MutableList<T>, private val screen: Screen) : MutableList<T> by value, Delegate {

	override fun add(element: T): Boolean {
		if (value.add(element)) {
			onChanged()
			return true
		}
		return false
	}

	override fun add(index: Int, element: T) {
		value.add(index, element)
		onChanged()
	}

	override fun addAll(elements: Collection<T>): Boolean {
		if (value.addAll(elements)) {
			onChanged()
			return true
		}
		return false
	}

	override fun addAll(index: Int, elements: Collection<T>): Boolean {
		if (value.addAll(index, elements)) {
			onChanged()
			return true
		}
		return false
	}

	override fun remove(element: T): Boolean {
		if (value.remove(element)) {
			onChanged()
			return true
		}
		return false
	}

	override fun removeAt(index: Int): T {
		onChanged()
		return removeAt(index)
	}

	override fun removeAll(elements: Collection<T>): Boolean {
		if (value.removeAll(elements)) {
			onChanged()
			return true
		}
		return false
	}

	override fun removeIf(filter: Predicate<in T>): Boolean {
		if (value.removeIf(filter)) {
			onChanged()
			return true
		}
		return false
	}

	override fun replaceAll(operator: UnaryOperator<T>) {
		super.replaceAll(operator)
		onChanged()
	}

	override fun onChanged() {
		screen.initialize()
	}

}

class DelegateMutableMap<K, V>(private val value: MutableMap<K, V>, private val screen: Screen) : MutableMap<K, V> by value, Delegate {

	override fun onChanged() {
		screen.initialize()
	}

	override fun put(key: K, value: V): V? {
		val origin = this.value[key]
		this.value[key] = value
		onChanged()
		return origin
	}

	override fun putAll(from: Map<out K, V>) {
		this.value.putAll(from)
		onChanged()
	}

	override fun remove(key: K): V? {
		val origin = this.value[key]
		this.value.remove(key)
		onChanged()
		return origin
	}

	override fun remove(key: K, value: V): Boolean {
		if (this.value.remove(key, value)) {
			onChanged()
			return true
		}
		return false
	}

	override fun replace(key: K, value: V): V? {
		val origin = this.value[key]
		this.value.replace(key, value)
		onChanged()
		return origin
	}

	override fun replace(key: K, oldValue: V, newValue: V): Boolean {
		if (this.value.replace(key, oldValue, newValue)) {
			onChanged()
			return true
		}
		return false
	}

	override fun replaceAll(function: BiFunction<in K, in V, out V>) {
		this.value.replaceAll(function)
		onChanged()
	}

}

@Suppress("UNCHECKED_CAST")
fun <T> Screen.rememberValueOf(key: String, value: T): MutableValue<T> {
	return if (remembers.containsKey(key)) {
		MutableValue(remembers[key] as T, key, this)
	} else {
		remembers[key] = value as Any
		MutableValue(value, key, this)
	}
}

@Suppress("UNCHECKED_CAST")
fun <T> Screen.rememberListOf(key: String, list: MutableList<T>): MutableList<T> {
	return if (remembers.containsKey(key)) {
		DelegateMutableList(remembers[key] as MutableList<T>, this)
	} else {
		remembers[key] = list as Any
		DelegateMutableList(remembers[key] as MutableList<T>, this)
	}
}

@Suppress("UNCHECKED_CAST")
fun <K, V> Screen.rememberMapOf(key: String, list: MutableMap<K, V>): MutableMap<K, V> {
	return if (remembers.containsKey(key)) {
		DelegateMutableMap(remembers[key] as MutableMap<K, V>, this)
	} else {
		remembers[key] = list as Any
		DelegateMutableMap(remembers[key] as MutableMap<K, V>, this)
	}
}



