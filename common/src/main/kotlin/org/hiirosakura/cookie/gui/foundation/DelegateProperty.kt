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

class MutableValue<T>(val key: String, private val screen: Screen, private var onValueChanged: (T) -> Unit = {}) : Delegate {

	@Suppress("UNCHECKED_CAST")
	operator fun getValue(thisRef: Any?, kProperty: KProperty<*>): T {
		return screen.rememberValue[key] as T
	}

	operator fun setValue(thisRef: Any?, kProperty: KProperty<*>, value: T) {
		if (screen.rememberValue[key] != value) {
			screen.rememberValue[key] = value
			onChanged()
		}
	}

	@Suppress("UNCHECKED_CAST")
	override fun onChanged() {
		screen.initialize()
		onValueChanged(screen.rememberValue[key] as T)
	}

}

class DelegateMutableList<T>(
	private val value: MutableList<T>,
	private val screen: Screen,
	private var onValueChanged: (MutableList<T>) -> Unit = {}
) : MutableList<T> by value, Delegate {

	override fun onChanged() {
		screen.initialize()
		onValueChanged(value)
	}

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


}

class DelegateMutableMap<K, V>(
	private val value: MutableMap<K, V>,
	private val screen: Screen,
	private var onValueChanged: (MutableMap<K, V>) -> Unit = {}
) : MutableMap<K, V> by value, Delegate {

	override fun onChanged() {
		screen.initialize()
		onValueChanged(value)
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
fun <T> Screen.rememberValueOf(key: String, value: T, onValueChanged: (T) -> Unit = {}): MutableValue<T> {
	return if (rememberValue.containsKey(key)) {
		MutableValue(key, this, onValueChanged)
	} else {
		rememberValue[key] = value as Any
		MutableValue(key, this, onValueChanged)
	}
}

@Suppress("UNCHECKED_CAST")
fun <T> Screen.rememberListOf(key: String, list: MutableList<T>, onValueChanged: (MutableList<T>) -> Unit = {}): MutableList<T> {
	return if (rememberValue.containsKey(key)) {
		DelegateMutableList(rememberValue[key] as MutableList<T>, this, onValueChanged)
	} else {
		rememberValue[key] = list as Any
		DelegateMutableList(rememberValue[key] as MutableList<T>, this, onValueChanged)
	}
}

@Suppress("UNCHECKED_CAST")
fun <K, V> Screen.rememberMapOf(key: String, list: MutableMap<K, V>, onValueChanged: (MutableMap<K, V>) -> Unit = {}): MutableMap<K, V> {
	return if (rememberValue.containsKey(key)) {
		DelegateMutableMap(rememberValue[key] as MutableMap<K, V>, this, onValueChanged)
	} else {
		rememberValue[key] = list as Any
		DelegateMutableMap(rememberValue[key] as MutableMap<K, V>, this, onValueChanged)
	}
}



