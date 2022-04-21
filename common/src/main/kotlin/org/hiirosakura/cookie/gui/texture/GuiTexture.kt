package org.hiirosakura.cookie.gui.texture

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import net.minecraft.util.Identifier
import org.hiirosakura.cookie.util.JsonUtil.getOr
import org.hiirosakura.cookie.util.math.I

/**
 *

 * 项目名 cookie

 * 包名 org.hiirosakura.cookie.gui.texture

 * 文件名 GuiTexture

 * 创建时间 2022/3/31 0:23

 * @author forpleuvoir

 */
class GuiTexture(
	val texture: Identifier,
	val corner: Corner,
	val u: Int,
	val v: Int,
	val regionWidth: Int,
	val regionHeight: Int,
	val textureWidth: Int,
	val textureHeight: Int
) {

	companion object {

		fun getFromJsonObject(obj: JsonObject?, default: GuiTexture): GuiTexture {
			return try {
				val texture = Identifier(obj!!.getOr("texture", default.texture.toString()))
				val corner = Corner.getFromJson(obj.get("corner"), default.corner)
				val u = obj.getOr("u", default.u).I
				val v = obj.getOr("v", default.v).I
				val regionWidth = obj.getOr("region_width", default.regionWidth).I
				val regionHeight = obj.getOr("region_height", default.regionHeight).I
				val textureWidth = obj.getOr("texture_width", default.textureWidth).I
				val textureHeight = obj.getOr("texture_height", default.textureHeight).I
				GuiTexture(texture, corner, u, v, regionWidth, regionHeight, textureWidth, textureHeight)
			} catch (_: Exception) {
				default
			}

		}
	}
}

class Corner(
	val left: Int,
	val right: Int,
	val top: Int,
	val bottom: Int
) {

	constructor(vertical: Int, horizontal: Int) : this(left = vertical, right = vertical, top = horizontal, bottom = horizontal)

	constructor(corner: Int) : this(corner, corner, corner, corner)

	companion object {

		fun getFromJson(obj: JsonElement, default: Corner): Corner {
			return try {
				if (obj.isJsonObject)
					obj.asJsonObject.let {
						val left: Int
						val right: Int
						if (it.has("vertical")) {
							left = it.get("vertical").asInt
							right = it.get("vertical").asInt
						} else {
							left = it.getOr("left", default.left).I
							right = it.getOr("right", default.right).I
						}
						val top: Int
						val bottom: Int
						if (it.has("horizontal")) {
							top = it.get("horizontal").asInt
							bottom = it.get("horizontal").asInt
						} else {
							top = it.getOr("top", default.top).I
							bottom = it.getOr("bottom", default.bottom).I
						}
						Corner(left, right, top, bottom)
					}
				else if (obj.isJsonPrimitive)
					obj.asJsonPrimitive.let {
						Corner(it.asInt)
					}
				else throw Exception()
			} catch (_: Exception) {
				default
			}

		}

	}
}

