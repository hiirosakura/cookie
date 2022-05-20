package org.hiirosakura.cookie.gui.widget

import net.minecraft.client.sound.PositionedSoundInstance
import net.minecraft.sound.SoundEvents
import org.hiirosakura.cookie.common.soundManager
import org.hiirosakura.cookie.gui.foundation.AbstractElement
import org.hiirosakura.cookie.gui.foundation.mouseHover

/**
 *

 * 项目名 cookie

 * 包名 org.hiirosakura.cookie.gui.widget

 * 文件名 ClickableElement

 * 创建时间 2022/3/31 21:38

 * @author forpleuvoir

 */
abstract class ClickableElement : AbstractElement() {

	/**
	 * 是否被按下
	 */
	var pressed: Boolean = false

	override var mouseClick: (mouseX: Number, mouseY: Number, button: Int) -> Boolean = { _, _, button ->
		if (button == 0) {
			soundManager.play(PositionedSoundInstance.master(SoundEvents.UI_BUTTON_CLICK, 1.0f))
			pressed = true
		}
		onClick.invoke(button)
		true
	}

	override var mouseRelease: (mouseX: Number, mouseY: Number, button: Int) -> Boolean = { _, _, button ->
		val b = !pressed
		if (!b) pressed = false
		onRelease(button)
		b
	}

	protected fun <T> status(normal: T, hovered: T, pressed: T): T {
		return if (active)
			if (mouseHover())
				if (this.pressed)
					pressed
				else hovered
			else normal
		else pressed
	}
}