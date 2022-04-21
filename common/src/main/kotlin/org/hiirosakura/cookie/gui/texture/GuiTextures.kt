package org.hiirosakura.cookie.gui.texture

import com.google.common.collect.Lists
import com.google.common.io.CharStreams
import com.mojang.blaze3d.systems.RenderSystem
import net.minecraft.resource.ResourceManager
import org.hiirosakura.cookie.common.Initialization
import org.hiirosakura.cookie.common.resourceManager
import org.hiirosakura.cookie.util.JsonUtil.parseToJsonObject
import org.hiirosakura.cookie.util.resources
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets
import java.util.concurrent.CompletableFuture

/**
 *

 * 项目名 cookie

 * 包名 org.hiirosakura.cookie.gui.texture

 * 文件名 GuiTextures

 * 创建时间 2022/3/31 0:38

 * @author forpleuvoir

 */
object GuiTextures : Initialization {

	private val TEXTURE_INFO = resources("texture/gui/cookie_widget.json")

	private val TEXTURE = resources("texture/gui/cookie_widget.png")

	lateinit var BUTTON_0: GuiTexture
		private set

	lateinit var BUTTON_0_HOVERED: GuiTexture
		private set

	lateinit var BUTTON_0_PRESSED: GuiTexture
		private set

	lateinit var BUTTON_1: GuiTexture
		private set

	lateinit var BUTTON_1_HOVERED: GuiTexture
		private set

	lateinit var BUTTON_1_PRESSED: GuiTexture
		private set

	lateinit var SCROLLER_BACKGROUND: GuiTexture
		private set

	lateinit var SCROLLER_BAR_HORIZONTAL: GuiTexture
		private set

	lateinit var SCROLLER_BAR_VERTICAL: GuiTexture
		private set

	lateinit var BORDER: GuiTexture
		private set

	lateinit var TEXT_FIELD: GuiTexture
		private set

	private val textures: MutableList<GuiTexture> = Lists.newArrayList()


	override fun initialize() {
		parse(resourceManager)
		apply()
		resourceManager.registerReloader { synchronizer, manager, _, _, _, applyExecutor ->
			CompletableFuture.runAsync {
				parse(manager)
			}.thenCompose {
				synchronizer.whenPrepared(it)
			}.thenApplyAsync({
				apply()
				it
			}, applyExecutor)
		}
	}

	private fun apply() {
		for (texture in textures) {
			RenderSystem.setShaderTexture(0, texture.texture)
		}
	}

	private fun parse(manager: ResourceManager) {
		textures.clear()
		val json = CharStreams.toString(InputStreamReader(manager.getResource(TEXTURE_INFO).inputStream, StandardCharsets.UTF_8))
		json.parseToJsonObject().apply {
			BUTTON_0 = GuiTexture.getFromJsonObject(
				getAsJsonObject("button_0"), GuiTexture(
					texture = TEXTURE, corner = Corner(2, 2, 2, 4),
					u = 0, v = 0, regionWidth = 16, regionHeight = 16, textureWidth = 256, textureHeight = 256
				)
			)
			textures.add(BUTTON_0)
			BUTTON_0_HOVERED = GuiTexture.getFromJsonObject(
				getAsJsonObject("button_0_hovered"), GuiTexture(
					texture = TEXTURE, corner = Corner(2, 2, 2, 3),
					u = 0, v = 16, regionWidth = 16, regionHeight = 16, textureWidth = 256, textureHeight = 256
				)
			)
			textures.add(BUTTON_0_HOVERED)
			BUTTON_0_PRESSED = GuiTexture.getFromJsonObject(
				getAsJsonObject("button_0_pressed"), GuiTexture(
					texture = TEXTURE, corner = Corner(2, 2, 3, 2),
					u = 0, v = 32, regionWidth = 16, regionHeight = 16, textureWidth = 256, textureHeight = 256
				)
			)
			textures.add(BUTTON_0_PRESSED)
			BUTTON_1 = GuiTexture.getFromJsonObject(
				getAsJsonObject("button_1"), GuiTexture(
					texture = TEXTURE, corner = Corner(3, 3, 3, 4),
					u = 16, v = 0, regionWidth = 16, regionHeight = 16, textureWidth = 256, textureHeight = 256
				)
			)
			textures.add(BUTTON_1)
			BUTTON_1_HOVERED = GuiTexture.getFromJsonObject(
				getAsJsonObject("button_1_hovered"), GuiTexture(
					texture = TEXTURE, corner = Corner(3, 3, 3, 4),
					u = 16, v = 16, regionWidth = 16, regionHeight = 16, textureWidth = 256, textureHeight = 256
				)
			)
			textures.add(BUTTON_1_HOVERED)
			BUTTON_1_PRESSED = GuiTexture.getFromJsonObject(
				getAsJsonObject("button_1_pressed"), GuiTexture(
					texture = TEXTURE, corner = Corner(3, 3, 4, 3),
					u = 16, v = 32, regionWidth = 16, regionHeight = 16, textureWidth = 256, textureHeight = 256
				)
			)
			textures.add(BUTTON_1_PRESSED)
			SCROLLER_BACKGROUND = GuiTexture.getFromJsonObject(
				getAsJsonObject("scroller_background"), GuiTexture(
					texture = TEXTURE, corner = Corner(2),
					u = 32, v = 0, regionWidth = 16, regionHeight = 16, textureWidth = 256, textureHeight = 256
				)
			)
			textures.add(SCROLLER_BACKGROUND)
			SCROLLER_BAR_HORIZONTAL = GuiTexture.getFromJsonObject(
				getAsJsonObject("scroller_bar_horizontal"), GuiTexture(
					texture = TEXTURE, corner = Corner(2, 2, 2, 3),
					u = 0, v = 0, regionWidth = 16, regionHeight = 15, textureWidth = 256, textureHeight = 256
				)
			)
			textures.add(SCROLLER_BAR_HORIZONTAL)
			SCROLLER_BAR_VERTICAL = GuiTexture.getFromJsonObject(
				getAsJsonObject("scroller_bar_vertical"), GuiTexture(
					texture = TEXTURE, corner = Corner(2, 2, 2, 4),
					u = 0, v = 0, regionWidth = 16, regionHeight = 16, textureWidth = 256, textureHeight = 256
				)
			)
			textures.add(SCROLLER_BAR_VERTICAL)
			BORDER = GuiTexture.getFromJsonObject(
				getAsJsonObject("border"), GuiTexture(
					texture = TEXTURE, corner = Corner(3, 3, 4, 3),
					u = 0, v = 48, regionWidth = 32, regionHeight = 32, textureWidth = 256, textureHeight = 256
				)
			)
			textures.add(BORDER)
			TEXT_FIELD = GuiTexture.getFromJsonObject(
				getAsJsonObject("text_field"), GuiTexture(
					texture = TEXTURE, corner = Corner(4, 4, 5, 4),
					u = 48, v = 0, regionWidth = 16, regionHeight = 16, textureWidth = 256, textureHeight = 256
				)
			)
			textures.add(TEXT_FIELD)
		}
	}


}