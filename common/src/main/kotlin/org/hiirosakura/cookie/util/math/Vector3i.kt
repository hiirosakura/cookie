package org.hiirosakura.cookie.util.math

import com.google.gson.JsonElement
import com.google.gson.JsonObject

/**
 *

 * 项目名 cookie

 * 包名 org.hiirosakura.cookie.util.math

 * 文件名 Vector3i

 * 创建时间 2022/2/19 17:10

 * @author forpleuvoir

 */
class Vector3i(
	override var x: Int = 0,
	override var y: Int = 0,
	override var z: Int = 0
) : Vector3<Int> {

	override fun setFromJson(jsonObject: JsonObject) {
		jsonObject.apply {
			x = this["x"].asInt
			y = this["y"].asInt
			z = this["z"].asInt
		}
	}

	override val asJsonElement: JsonElement
		get() = JsonObject().apply {
			addProperty("x", x)
			addProperty("y", y)
			addProperty("z", z)
		}

	override fun unaryMinus(): Vector3i {
		return Vector3i(-x, -y, -z)
	}

	override fun plus(x: Int, y: Int, z: Int): Vector3i {
		return Vector3i(this.x + x, this.y + y, this.z + z)
	}

	override fun plusAssign(x: Int, y: Int, z: Int) {
		this.x += x
		this.y += y
		this.z += z
	}

	override fun minus(x: Int, y: Int, z: Int): Vector3i {
		return Vector3i(this.x - x, this.y - y, this.z - z)
	}

	override fun minusAssign(x: Int, y: Int, z: Int) {
		this.x -= x
		this.y -= y
		this.z -= z
	}

	override fun times(x: Int, y: Int, z: Int): Vector3<Int> {
		return Vector3i(this.x * x, this.y * y, this.z * z)
	}

	override fun timesAssign(x: Int, y: Int, z: Int) {
		this.x *= x
		this.y *= y
		this.z *= z
	}

	override fun div(x: Int, y: Int, z: Int): Vector3<Int> {
		return Vector3i(this.x / x, this.y / y, this.z / z)
	}

	override fun divAssign(x: Int, y: Int, z: Int) {
		this.x /= x
		this.y /= y
		this.z /= z
	}

	override fun rem(x: Int, y: Int, z: Int): Vector3<Int> {
		return Vector3i(this.x % x, this.y % y, this.z % z)
	}

	override fun remAssign(x: Int, y: Int, z: Int) {
		this.x %= x
		this.y %= y
		this.z %= z
	}

}