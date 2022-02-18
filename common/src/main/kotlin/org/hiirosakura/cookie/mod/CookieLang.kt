package org.hiirosakura.cookie.mod

import org.hiirosakura.cookie.common.ModLang
import org.hiirosakura.cookie.platform.MultiPlatformFun

/**
 *

 * 项目名 cookie

 * 包名 org.hiirosakura.cookie.mod

 * 文件名 CookieLang

 * 创建时间 2022/2/16 16:48

 * @author forpleuvoir

 */
enum class CookieLang(override val key: String) : ModLang {

	Initializing("initializing"),
	Initialized("initialized"),
	Red("color.red"),
	Green("color.green"),
	Blue("color.blue"),
	Alpha("color.alpha"),
	On("on"),
	Off("off"),
	Confirm("confirm"),
	Cancel("cancel"),
	Post("post"),
	Add("add"),
	Delete("delete"),
	Update("update"),
	Apply("apply"),
	Toggle("toggle"),
	Value("value"),
	Hotkey("hotkey"),
	Execute("execute"),
	Edit("edit"),
	Enable("enable"),
	Enabled("enabled"),
	Disable("disable"),
	Disabled("disabled"),
	Status("status"),
	Rest("rest"),
	Save("save"),
	KeyEnvironment("key_environment"),
	SetFromJsonFailed("config.set_from_json_failed"),
	;

	override val modId: String
		get() = Cookie.id
}