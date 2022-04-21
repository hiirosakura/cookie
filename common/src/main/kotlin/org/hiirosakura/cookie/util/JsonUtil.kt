package org.hiirosakura.cookie.util

import com.google.gson.*
import org.hiirosakura.cookie.util.JsonUtil.toJsonObject

/**
 *

 * 项目名 cookie

 * 包名 org.hiirosakura.cookie.util

 * 文件名 JsonUtil

 * 创建时间 2022/2/17 1:55

 * @author forpleuvoir

 */
object JsonUtil {

	val gson: Gson = GsonBuilder().setPrettyPrinting().create()

	fun JsonObject.getNestedObject(key: String, create: Boolean = false): JsonObject? {
		return if (!this.has(key) || this[key].isJsonObject) {
			if (!create) {
				return null
			}
			val obj = JsonObject()
			this.add(key, obj)
			obj
		} else {
			this[key].asJsonObject
		}
	}

	fun toStringBuffer(obj: Any?): StringBuffer {
		return StringBuffer(gson.toJson(obj))
	}


	fun getObject(parent: JsonObject, key: String): JsonObject? {
		if (parent.has(key) && parent[key].isJsonObject) {
			return parent[key].asJsonObject
		}
		return null
	}

	/**
	 * 将对象转换成json字符串
	 *
	 * @param json 需要转换的对象
	 * @return json字符串
	 */
	fun Any.toJsonStr(): String {
		return gson.toJson(this)
	}

	/**
	 * 将json字符串转换成JsonObject
	 *
	 * @param json 需要转换的字符串对象
	 * @return JsonObject对象
	 */
	fun String.parseToJsonObject(): JsonObject {
		return JsonParser.parseString(this).asJsonObject
	}

	fun String.parseToJsonArray(): JsonArray {
		return JsonParser.parseString(this).asJsonArray
	}

	fun Any.toJsonObject(): JsonObject {
		return gson.toJsonTree(this).asJsonObject
	}

	inline fun <reified T> JsonObject.getOr(key: String, or: T): T {
		this.has(key).ifc {
			try {
				return gson.fromJson(this.get(key), T::class.java)
			} catch (_: Exception) {
			}
		}
		return or
	}

	fun JsonObject.getOr(key: String, or: Number): Number {
		this.has(key).ifc {
			try {
				return this.get(key).asNumber
			} catch (_: Exception) {
			}
		}
		return or
	}

	fun JsonObject.getOr(key: String, or: Boolean): Boolean {
		this.has(key).ifc {
			try {
				return this.get(key).asBoolean
			} catch (_: Exception) {
			}
		}
		return or
	}

	fun JsonObject.getOr(key: String, or: String): String {
		this.has(key).ifc {
			try {
				return this.get(key).asString
			} catch (_: Exception) {
			}
		}
		return or
	}

	fun JsonObject.getOr(key: String, or: JsonObject): JsonObject {
		this.has(key).ifc {
			try {
				return this.get(key).asJsonObject
			} catch (_: Exception) {
			}
		}
		return or
	}

	fun JsonObject.getOr(key: String, or: JsonArray): JsonArray {
		this.has(key).ifc {
			try {
				return this.get(key).asJsonArray
			} catch (_: Exception) {
			}
		}
		return or
	}

}

class JsonObjectScope {

	val jsonObject: JsonObject = JsonObject()

	infix fun String.to(value: String) {
		jsonObject.addProperty(this, value)
	}

	infix fun String.to(value: Number) {
		jsonObject.addProperty(this, value)
	}

	infix fun String.to(value: Boolean) {
		jsonObject.addProperty(this, value)
	}

	infix fun String.to(value: Char) {
		jsonObject.addProperty(this, value)
	}

	infix fun String.to(value: JsonElement) {
		jsonObject.add(this, value)
	}
}

fun jsonArray(vararg elements: Any): JsonArray {
	return JsonArray().apply {
		for (element in elements) {
			when (element) {
				is Boolean -> add(element)
				is Number -> add(element)
				is String -> add(element)
				is Char -> add(element)
				is JsonElement -> add(element)
				else -> add(element.toJsonObject())
			}
		}
	}
}

fun jsonObject(scope: JsonObjectScope.() -> Unit): JsonObject {
	val jsonObjectScope = JsonObjectScope()
	jsonObjectScope.scope()
	return jsonObjectScope.jsonObject
}