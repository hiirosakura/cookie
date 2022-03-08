package org.hiirosakura.cookie.util.math

import com.google.gson.JsonElement
import com.google.gson.JsonObject

/**
 *

 * 项目名 cookie

 * 包名 org.hiirosakura.cookie.util.math

 * 文件名 Vector3d

 * 创建时间 2022/2/19 18:02

 * @author forpleuvoir

 */
class Vector3d(
	override var x: Double = 0.0,
	override var y: Double = 0.0,
	override var z: Double = 0.0
) : Vector3<Double> {

	override fun setFromJson(jsonObject: JsonObject) {
		jsonObject.apply {
			x = this["x"].asDouble
			y = this["y"].asDouble
			z = this["z"].asDouble
		}
	}

	override val asJsonElement: JsonElement
		get() = JsonObject().apply {
			addProperty("x", x)
			addProperty("y", y)
			addProperty("z", z)
		}

	override fun unaryMinus(): Vector3d {
		return Vector3d(-x, -y, -z)
	}

	override fun plus(x: Double, y: Double, z: Double): Vector3d {
		return Vector3d(this.x + x, this.y + y, this.z + z)
	}

	override fun plusAssign(x: Double, y: Double, z: Double) {
		this.x += x
		this.y += y
		this.z += z
	}

	override fun minus(x: Double, y: Double, z: Double): Vector3d {
		return Vector3d(this.x - x, this.y - y, this.z - z)
	}

	override fun minusAssign(x: Double, y: Double, z: Double) {
		this.x -= x
		this.y -= y
		this.z -= z
	}

	override fun times(x: Double, y: Double, z: Double): Vector3<Double> {
		return Vector3d(this.x * x, this.y * y, this.z * z)
	}

	override fun timesAssign(x: Double, y: Double, z: Double) {
		this.x *= x
		this.y *= y
		this.z *= z
	}

	override fun div(x: Double, y: Double, z: Double): Vector3<Double> {
		return Vector3d(this.x / x, this.y / y, this.z / z)
	}

	override fun divAssign(x: Double, y: Double, z: Double) {
		this.x /= x
		this.y /= y
		this.z /= z
	}

	override fun rem(x: Double, y: Double, z: Double): Vector3<Double> {
		return Vector3d(this.x % x, this.y % y, this.z % z)
	}

	override fun remAssign(x: Double, y: Double, z: Double) {
		this.x %= x
		this.y %= y
		this.z %= z
	}

}