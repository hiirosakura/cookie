package org.hiirosakura.cookie.gui.foundation

import com.mojang.blaze3d.systems.RenderSystem
import net.minecraft.client.render.*
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.text.Text
import net.minecraft.util.Identifier
import org.hiirosakura.cookie.common.textRenderer
import org.hiirosakura.cookie.util.color.Color
import org.hiirosakura.cookie.util.color.Color4f
import org.hiirosakura.cookie.util.math.D
import org.hiirosakura.cookie.util.math.F
import org.hiirosakura.cookie.util.math.I
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
	 * 渲染优先级 越低越先渲染
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
 * 只适用于边角为正方形的材质
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
	/**
	 * centerWidth
	 */
	val cw = width.D - (cornerSize * 2)

	/**
	 * centerHeight
	 */
	val ch = height.D - (cornerSize * 2)

	/**
	 * centerRegionWidth
	 */
	val crw = regionWidth - (cornerSize.I * 2)

	/**
	 *  centerRegionHeight
	 */
	val crh = regionHeight - (cornerSize.I * 2)

	val centerU = u.D + cornerSize
	val rightU = u.D + (regionWidth - cornerSize)
	val centerV = v.D + cornerSize
	val bottomV = v.D + (regionHeight - cornerSize)
	val centerX = x.D + cornerSize
	val rightX = x.D + (width.D - cornerSize)
	val centerY = y.D + cornerSize
	val bottomY = y.D + (height.D - cornerSize)
	//top left
	drawTexture(matrices, x, y, cornerSize, cornerSize, u, v, cornerSize, cornerSize, textureWidth, textureHeight)
	//top center
	drawTexture(matrices, centerX, y, cw, cornerSize, centerU, v, crw, cornerSize, textureWidth, textureHeight)
	//top right
	drawTexture(matrices, rightX, y, cornerSize, cornerSize, rightU, v, cornerSize, cornerSize, textureWidth, textureHeight)
	//center left
	drawTexture(matrices, x, centerY, cornerSize, ch, u, centerV, cornerSize, crh, textureWidth, textureHeight)
	//center
	drawTexture(matrices, centerX, centerY, cw, ch, centerU, centerV, crw, crh, textureWidth, textureHeight)
	//center right
	drawTexture(matrices, rightX, centerY, cornerSize, ch, rightU, centerV, cornerSize, crh, textureWidth, textureHeight)
	//bottom left
	drawTexture(matrices, x, bottomY, cornerSize, cornerSize, u, bottomV, cornerSize, cornerSize, textureWidth, textureHeight)
	//bottom center
	drawTexture(matrices, centerX, bottomY, cw, cornerSize, centerU, bottomV, crw, cornerSize, textureWidth, textureHeight)
	//bottom right
	drawTexture(matrices, rightX, bottomY, cornerSize, cornerSize, rightU, bottomV, cornerSize, cornerSize, textureWidth, textureHeight)
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

fun Drawable.setShader(shaderSupplier: KFunction0<Shader?>) = RenderSystem.setShader(shaderSupplier)

fun Drawable.setShader(shaderSupplier: (() -> Shader)?) = RenderSystem.setShader(shaderSupplier)

fun Drawable.setShaderTexture(texture: Identifier) = RenderSystem.setShaderTexture(0, texture)

fun Drawable.enableTexture() = RenderSystem.enableTexture()

fun Drawable.disableTexture() = RenderSystem.disableTexture()

fun Drawable.setShaderColor(color: Color<out Number>) {
	val color4f = Color4f().fromInt(color.rgba)
	setShaderColor(color4f)
}

fun Drawable.setShaderColor(color: Color4f) = RenderSystem.setShaderColor(color.red, color.green, color.blue, color.alpha)

fun Drawable.enablePolygonOffset() = RenderSystem.enablePolygonOffset()

fun Drawable.polygonOffset(factor: Number, units: Number) = RenderSystem.polygonOffset(factor.F, units.F)

fun Drawable.disablePolygonOffset() = RenderSystem.disablePolygonOffset()

fun Drawable.enableBlend() = RenderSystem.enableBlend()

fun Drawable.defaultBlendFunc() = RenderSystem.defaultBlendFunc()

fun Drawable.disableBlend() = RenderSystem.disableBlend()

fun Drawable.enableDepthTest() = RenderSystem.enableDepthTest()

fun Drawable.disableDepthTest() = RenderSystem.disableDepthTest()


