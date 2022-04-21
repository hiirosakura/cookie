package org.hiirosakura.cookie.gui.screen

import org.hiirosakura.cookie.gui.foundation.layout.RowLayout
import org.hiirosakura.cookie.gui.foundation.layout.row
import org.hiirosakura.cookie.gui.widget.button.button
import org.hiirosakura.cookie.util.text

/**
 *

 * 项目名 cookie

 * 包名 org.hiirosakura.cookie.gui.screen

 * 文件名 TestScreen

 * 创建时间 2022/2/20 18:14

 * @author forpleuvoir

 */
fun ScreenManager.testScreen(): Screen =
	screen {
		row {
			button(
				text = { "test".text },
				onClick = {
					println("test")
				},
			)
			group()
		}
	}

fun RowLayout.group() {
	button(
		text = { "test1".text },
		onClick = {
			println("test2")
		}
	)
}