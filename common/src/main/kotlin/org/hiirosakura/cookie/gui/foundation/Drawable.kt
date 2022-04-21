package org.hiirosakura.cookie.gui.foundation

import com.mojang.blaze3d.systems.RenderSystem
import net.minecraft.client.render.*
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.text.Text
import net.minecraft.util.Identifier
import org.hiirosakura.cookie.common.textRenderer
import org.hiirosakura.cookie.gui.foundation.HorizontalAlign.*
import org.hiirosakura.cookie.gui.texture.GuiTexture
import org.hiirosakura.cookie.util.color.Color
import org.hiirosakura.cookie.util.color.Color4f
import org.hiirosakura.cookie.util.math.D
import org.hiirosakura.cookie.util.math.F
import kotlin.reflect.KFunction0

/**
 * 可绘制

 * 项目名 cookie

 * 包名 org.hiirosakura.cookie.gui.foundation

 * 文件名 Drawable

 * 创建时间 2022/2/18 23:39

 * @author forpleuvoir

 */
interface Drawable {

	/**
	 * 可见的
	 */
	val visible: Boolean get() = true

	val zOffset: Double get() = 0.0

	/**
	 * 渲染优先级 越高越先渲染
	 */
	val renderLevel: Int get() = 0

	/**
	 * 渲染
	 * @param matrices MatrixStack 矩阵
	 * @param delta 时间增量
	 */
	fun render(matrices: MatrixStack, delta: Number)

}


fun Drawable.drawRect(matrices: MatrixStack, x: Number, y: Number, width: Number, height: Number, color: Color<out Number>) {
	setShader(GameRenderer::getPositionColorShader)
	enableBlend()
	defaultBlendFunc()
	disableTexture()
	val buffer = Tessellator.getInstance().buffer
	val matrix4f = matrices.peek().positionMatrix
	buffer.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR)
	buffer.vertex(matrix4f, x.F, y.F, zOffset.F).color(color.rgba).next()
	buffer.vertex(matrix4f, x.F, (y.F + height.F), zOffset.F).color(color.rgba).next()
	buffer.vertex(matrix4f, (x.F + width.F), (y.F + height.F), zOffset.F).color(color.rgba).next()
	buffer.vertex(matrix4f, (x.F + width.F), y.F, zOffset.F).color(color.rgba).next()
	buffer.end()
	BufferRenderer.draw(buffer)
	enableTexture()
	disableBlend()
}


fun Drawable.drawOutline(
	matrices: MatrixStack,
	x: Number,
	y: Number,
	width: Number,
	height: Number,
	color: Color<out Number>,
	borderWidth: Number = 1
) {
	drawRect(matrices, x, y, borderWidth, height, color)
	drawRect(matrices, x.D + width.D - borderWidth.D, y, borderWidth, height, color)
	drawRect(matrices, x.D + borderWidth.D, y, width.D - 2 * borderWidth.D, borderWidth, color)
	drawRect(
		matrices,
		x.D + borderWidth.D,
		y.D + height.D - borderWidth.D,
		width.D - 2 * borderWidth.D,
		borderWidth,
		color
	)
}

fun Drawable.drawOutlinedBox(
	matrices: MatrixStack,
	x: Number,
	y: Number,
	width: Number,
	height: Number,
	color: Color<out Number>,
	outlineColor: Color<out Number>,
	borderWidth: Number = 1,
	innerOutline: Boolean = true
) {
	if (innerOutline) {
		drawRect(matrices, x, y, width, height, color)
		drawOutline(
			matrices,
			x.D - borderWidth.D,
			y.D - borderWidth.D,
			width.D + borderWidth.D * 2,
			height.D + borderWidth.D * 2,
			outlineColor,
			borderWidth
		)
	} else {
		drawRect(matrices, x.D + borderWidth.D, y.D + borderWidth.D, width.D - 2 * borderWidth.D, height.D - 2 * borderWidth.D, color)
		drawOutline(matrices, x.D, y.D, width.D, height.D, outlineColor, borderWidth)
	}
}

fun Drawable.drawGradient(
	matrices: MatrixStack,
	startX: Number,
	startY: Number,
	endX: Number,
	endY: Number,
	startColor: Color<out Number>,
	endColor: Color<out Number>
) {
	disableTexture()
	enableBlend()
	setShader(GameRenderer::getPositionColorShader)
	val tess = Tessellator.getInstance()
	val buffer = tess.buffer
	buffer.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR)
	buffer.vertex(matrices.peek().positionMatrix, endX.F, startY.F, zOffset.F).color(startColor.rgba).next()
	buffer.vertex(matrices.peek().positionMatrix, startX.F, startY.F, zOffset.F).color(startColor.rgba).next()
	buffer.vertex(matrices.peek().positionMatrix, startX.F, endY.F, zOffset.F).color(endColor.rgba).next()
	buffer.vertex(matrices.peek().positionMatrix, endX.F, endY.F, zOffset.F).color(endColor.rgba).next()
	tess.draw()
	disableBlend()
	enableTexture()
}

fun Drawable.drawTexture(
	matrices: MatrixStack,
	x: Number,
	y: Number,
	width: Number,
	height: Number,
	u: Number,
	v: Number,
	regionWidth: Int,
	regionHeight: Int,
	textureWidth: Int = 256,
	textureHeight: Int = 256
) {
	val matrix = matrices.peek().positionMatrix
	val bufferBuilder = Tessellator.getInstance().buffer
	setShader(GameRenderer::getPositionTexShader)
	bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE)
	bufferBuilder.vertex(matrix, x.F, y.F + height.F, zOffset.F).texture(u.F / textureWidth, (v.F + regionHeight) / textureHeight).next()
	bufferBuilder.vertex(matrix, x.F + width.F, y.F + height.F, zOffset.F)
		.texture((u.F + regionWidth) / textureWidth, (v.F + regionHeight) / textureHeight).next()
	bufferBuilder.vertex(matrix, x.F + width.F, y.F, zOffset.F).texture((u.F + regionWidth) / textureWidth, v.F / textureHeight).next()
	bufferBuilder.vertex(matrix, x.F, y.F, zOffset.F).texture(u.F / textureWidth, v.F / textureHeight).next()
	bufferBuilder.end()
	BufferRenderer.draw(bufferBuilder)
}

/**
 * 渲染.9格式的材质
 * 只适用于边角为相同大小的正方形的材质
 * @receiver Drawable
 */
fun Drawable.draw9Texture(
	matrices: MatrixStack,
	x: Number,
	y: Number,
	/**
	 * 边角大小
	 */
	cornerSize: Int,
	width: Number,
	height: Number,
	u: Number,
	v: Number,
	regionWidth: Int,
	regionHeight: Int,
	textureWidth: Int = 256,
	textureHeight: Int = 256
) {
	draw9Texture(
		matrices,
		x,
		y,
		cornerSize,
		cornerSize,
		cornerSize,
		cornerSize,
		width,
		height,
		u,
		v,
		regionWidth,
		regionHeight,
		textureWidth,
		textureHeight
	)
}

/**
 * 渲染.9格式的材质
 * @receiver Drawable
 */
fun Drawable.draw9Texture(
	matrices: MatrixStack,
	x: Number,
	y: Number,
	cornerLeftWidth: Int,
	cornerRightWidth: Int,
	cornerTopHeight: Int,
	cornerBottomHeight: Int,
	width: Number,
	height: Number,
	u: Number,
	v: Number,
	regionWidth: Int,
	regionHeight: Int,
	textureWidth: Int = 256,
	textureHeight: Int = 256
) {
	if (cornerLeftWidth == 0 &&
		cornerRightWidth == 0 &&
		cornerTopHeight == 0 &&
		cornerBottomHeight == 0
	) {
		drawTexture(matrices, x, y, width, height, u, v, regionWidth, regionHeight, textureWidth, textureHeight)
	}

	/**
	 * centerWidth
	 */
	val cw = width.D - (cornerLeftWidth + cornerRightWidth)

	/**
	 * centerHeight
	 */
	val ch = height.D - (cornerTopHeight + cornerBottomHeight)

	/**
	 * centerRegionWidth
	 */
	val crw = regionWidth - (cornerLeftWidth + cornerRightWidth)

	/**
	 *  centerRegionHeight
	 */
	val crh = regionHeight - (cornerTopHeight + cornerBottomHeight)

	val centerU = u.D + cornerLeftWidth
	val rightU = u.D + (regionWidth - cornerRightWidth)
	val centerV = v.D + cornerTopHeight
	val bottomV = v.D + (regionHeight - cornerBottomHeight)
	val centerX = x.D + cornerLeftWidth
	val rightX = x.D + (width.D - cornerRightWidth)
	val centerY = y.D + cornerTopHeight
	val bottomY = y.D + (height.D - cornerBottomHeight)
	//top left
	drawTexture(matrices, x, y, cornerLeftWidth, cornerTopHeight, u, v, cornerLeftWidth, cornerTopHeight, textureWidth, textureHeight)
	//top center
	drawTexture(matrices, centerX, y, cw, cornerTopHeight, centerU, v, crw, cornerTopHeight, textureWidth, textureHeight)
	//top right
	drawTexture(
		matrices,
		rightX,
		y,
		cornerRightWidth,
		cornerTopHeight,
		rightU,
		v,
		cornerRightWidth,
		cornerTopHeight,
		textureWidth,
		textureHeight
	)
	//center left
	drawTexture(matrices, x, centerY, cornerLeftWidth, ch, u, centerV, cornerLeftWidth, crh, textureWidth, textureHeight)
	//center
	drawTexture(matrices, centerX, centerY, cw, ch, centerU, centerV, crw, crh, textureWidth, textureHeight)
	//center right
	drawTexture(matrices, rightX, centerY, cornerRightWidth, ch, rightU, centerV, cornerRightWidth, crh, textureWidth, textureHeight)
	//bottom left
	drawTexture(
		matrices,
		x,
		bottomY,
		cornerLeftWidth,
		cornerBottomHeight,
		u,
		bottomV,
		cornerLeftWidth,
		cornerBottomHeight,
		textureWidth,
		textureHeight
	)
	//bottom center
	drawTexture(matrices, centerX, bottomY, cw, cornerBottomHeight, centerU, bottomV, crw, cornerBottomHeight, textureWidth, textureHeight)
	//bottom right
	drawTexture(
		matrices,
		rightX,
		bottomY,
		cornerRightWidth,
		cornerBottomHeight,
		rightU,
		bottomV,
		cornerRightWidth,
		cornerBottomHeight,
		textureWidth,
		textureHeight
	)
}

fun Drawable.drawTexture(
	matrices: MatrixStack,
	x: Number,
	y: Number,
	width: Number,
	height: Number,
	texture: GuiTexture,
	shaderColor: Color<out Number> = Color4f.WHITE
) {
	setShaderTexture(texture.texture)
	enableBlend()
	defaultBlendFunc()
	enableDepthTest()
	setShaderColor(shaderColor)
	draw9Texture(
		matrices,
		x,
		y,
		texture.corner.left,
		texture.corner.right,
		texture.corner.top,
		texture.corner.bottom,
		width,
		height,
		texture.u,
		texture.v,
		texture.regionWidth,
		texture.regionHeight,
		texture.textureWidth,
		texture.textureHeight
	)
	disableBlend()
	setShaderColor(Color4f.WHITE)
}


fun Drawable.drawCenteredText(
	matrices: MatrixStack,
	text: Text,
	x: Number,
	y: Number,
	width: Number,
	height: Number,
	shadow: Boolean = true,
	rightToLeft: Boolean = false,
	color: Color<out Number> = Color4f.WHITE,
	backgroundColor: Color<out Number> = Color4f(alpha = 0.0f)
) {
	val centerX = x.F + width.F / 2
	val centerY = y.F + height.F / 2
	val textWidth = textRenderer.getWidth(text)
	val immediate = VertexConsumerProvider.immediate(Tessellator.getInstance().buffer)
	textRenderer.draw(
		text.string,
		centerX - textWidth / 2,
		centerY - textRenderer.fontHeight / 2,
		color.rgba,
		shadow,
		matrices.peek().positionMatrix,
		immediate,
		false,
		backgroundColor.rgba,
		LightmapTextureManager.MAX_LIGHT_COORDINATE,
		rightToLeft
	)
	immediate.draw()
}


fun Drawable.drawText(
	matrices: MatrixStack,
	text: String,
	x: Number,
	y: Number,
	shadow: Boolean = true,
	color: Color<out Number> = Color4f.WHITE,
) {
	textRenderer.draw(
		text,
		x.F,
		y.F,
		color.rgba,
		shadow,
		matrices.peek().positionMatrix,
		VertexConsumerProvider.immediate(Tessellator.getInstance().buffer),
		false,
		Color4f.WHITE.rgba,
		LightmapTextureManager.MAX_LIGHT_COORDINATE,
		false
	)
}

fun Drawable.drawStringLines(
	matrices: MatrixStack,
	lines: List<String>,
	x: Number,
	y: Number,
	lineSpacing: Number = 1,
	color: Color<out Number> = Color4f.BLACK,
	shadow: Boolean = false,
	align: HorizontalAlign = Left,
	rightToLeft: Boolean = false,
) {
	val drawText: (text: String, x: Float, y: Float) -> Unit = { text, x, y ->
		val immediate = VertexConsumerProvider.immediate(Tessellator.getInstance().buffer)
		textRenderer.draw(
			text, x, y, color.rgba, shadow, matrices.peek().positionMatrix, immediate, false, 0,
			LightmapTextureManager.MAX_LIGHT_COORDINATE,
			rightToLeft
		)
		immediate.draw()
	}
	var textY = y.F
	when (align) {
		Left -> {
			val textX = x.F
			for (text in lines) {
				drawText(text, textX, textY)
				textY += textRenderer.fontHeight + lineSpacing.F
			}
		}
		Center -> {
			for (text in lines) {
				val textX = x.F - (textRenderer.getWidth(text) / 2)
				drawText(text, textX, textY)
				textY += textRenderer.fontHeight + lineSpacing.F
			}
		}
		Right -> {
			for (text in lines) {
				val textX = x.F - textRenderer.getWidth(text)
				drawText(text, textX, textY)
				textY += textRenderer.fontHeight + lineSpacing.F
			}
		}
	}

}

fun Drawable.drawTextLines(
	matrices: MatrixStack,
	lines: List<Text>,
	x: Number,
	y: Number,
	lineSpacing: Number = 1,
	color: Color<out Number> = Color4f.BLACK,
	shadow: Boolean = false,
	align: HorizontalAlign = Left,
	rightToLeft: Boolean = false
) {
	drawStringLines(matrices, ArrayList<String>().apply { lines.forEach { add(it.string) } }, x, y, lineSpacing, color, shadow, align, rightToLeft)
}

fun Drawable.setShader(shaderSupplier: KFunction0<Shader?>) = RenderSystem.setShader(shaderSupplier)

fun Drawable.setShader(shaderSupplier: (() -> Shader)?) = RenderSystem.setShader(shaderSupplier)

fun Drawable.setShaderTexture(texture: Identifier) = RenderSystem.setShaderTexture(0, texture)

fun Drawable.enableTexture() = RenderSystem.enableTexture()

fun Drawable.disableTexture() = RenderSystem.disableTexture()

fun Drawable.setShaderColor(color: Color<out Number>) = setShaderColor(Color4f().fromInt(color.rgba))

fun Drawable.setShaderColor(color: Color4f) = RenderSystem.setShaderColor(color.red, color.green, color.blue, color.alpha)

fun Drawable.enablePolygonOffset() = RenderSystem.enablePolygonOffset()

fun Drawable.polygonOffset(factor: Number, units: Number) = RenderSystem.polygonOffset(factor.F, units.F)

fun Drawable.disablePolygonOffset() = RenderSystem.disablePolygonOffset()

fun Drawable.enableBlend() = RenderSystem.enableBlend()

fun Drawable.defaultBlendFunc() = RenderSystem.defaultBlendFunc()

fun Drawable.disableBlend() = RenderSystem.disableBlend()

fun Drawable.enableDepthTest() = RenderSystem.enableDepthTest()

fun Drawable.disableDepthTest() = RenderSystem.disableDepthTest()


