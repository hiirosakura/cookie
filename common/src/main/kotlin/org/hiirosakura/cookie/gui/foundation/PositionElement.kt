package org.hiirosakura.cookie.gui.foundation

import org.hiirosakura.cookie.util.math.Vector3

/**
 *

 * 项目名 cookie

 * 包名 org.hiirosakura.cookie.gui.foundation

 * 文件名 PositionElement

 * 创建时间 2022/2/19 16:53

 * @author forpleuvoir

 */
interface PositionElement {

	fun setPosition(x: Number, y: Number, z: Number)

	fun setPosition(vector3: Vector3<out Number>) = setPosition(vector3.x, vector3.y, vector3.z)

	fun deltaPosition(deltaX: Number, deltaY: Number, deltaZ: Number)

	fun deltaPosition(deltaVector3: Vector3<out Number>) = deltaPosition(deltaVector3.x, deltaVector3.y, deltaVector3.z)

}