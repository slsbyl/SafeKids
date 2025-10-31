package com.example.kidsmovieapp

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

object SafeKidsColors {
    val CandyTurquoise = Color(0xFF00D4FF)
    val CandyYellow = Color(0xFFFFEB3B)
    val CandyPink = Color(0xFFFF69B4)
    val CandyLime = Color(0xFF32FF7E)
    val CandyOrange = Color(0xFFFFA726)
    val CandySky = Color(0xFF87CEEB)
    val CandyPurple = Color(0xFF9C27B0)
    val CandyMint = Color(0xFF4ECDC4)
    val BgPinkLight = Color(0xFFFFC1E3)
    val BgPurpleLight = Color(0xFFE1C4FF)
    val BgCyanLight = Color(0xFFC4E8FF)
}

private fun Random.nextFloatInRange(min: Float, max: Float) =
    nextFloat() * (max - min) + min

private enum class FloatingShape { STAR, HEART, BALLOON, CLOUD }

private data class FloatingParticle(
    var x: Float,
    var y: Float,
    val size: Float,
    val speedY: Float,
    val speedX: Float,
    var rotation: Float,
    val rotationSpeed: Float,
    val shape: FloatingShape,
    val color: Color,
    val baseAlpha: Float
)

@Composable
fun AnimatedBackground(
    modifier: Modifier = Modifier,
    particleCount: Int = 18
) {
    val random = remember { Random(System.currentTimeMillis()) }
    val particles = remember { mutableStateListOf<FloatingParticle>() }

    val infiniteTransition = rememberInfiniteTransition(label = "bg_animation")
    val animationPhase by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "phase"
    )

    val gradientBrush = remember {
        Brush.verticalGradient(
            colors = listOf(
                SafeKidsColors.BgPinkLight,
                SafeKidsColors.BgPurpleLight,
                SafeKidsColors.BgCyanLight
            )
        )
    }

    Canvas(modifier = modifier.background(gradientBrush)) {
        if (particles.isEmpty()) {
            val shapes = FloatingShape.entries.toTypedArray()
            val colors = listOf(
                SafeKidsColors.CandyPink,
                SafeKidsColors.CandyTurquoise,
                SafeKidsColors.CandyLime,
                SafeKidsColors.CandyYellow,
                SafeKidsColors.CandyOrange,
                SafeKidsColors.CandySky,
                SafeKidsColors.CandyPurple,
                SafeKidsColors.CandyMint
            )

            repeat(particleCount) {
                particles.add(
                    FloatingParticle(
                        x = random.nextFloat() * size.width,
                        y = random.nextFloat() * size.height,
                        size = random.nextFloatInRange(24f, 48f),
                        speedY = random.nextFloatInRange(0.4f, 1.0f),
                        speedX = random.nextFloatInRange(-0.3f, 0.3f),
                        rotation = random.nextFloatInRange(0f, 360f),
                        rotationSpeed = random.nextFloatInRange(-1.5f, 1.5f),
                        shape = shapes.random(random),
                        color = colors.random(random),
                        baseAlpha = random.nextFloatInRange(0.15f, 0.3f)
                    )
                )
            }
        }

        particles.forEach { particle ->
            particle.y -= particle.speedY
            particle.x += particle.speedX
            particle.rotation += particle.rotationSpeed

            if (particle.y < -particle.size) {
                particle.y = size.height + particle.size
                particle.x = random.nextFloat() * size.width
            }
            if (particle.x < -particle.size) particle.x = size.width + particle.size
            if (particle.x > size.width + particle.size) particle.x = -particle.size

            val twinkle =
                0.5f + 0.5f * sin((animationPhase * 2 * Math.PI + particle.x / 100).toFloat())
            val alpha = (particle.baseAlpha * twinkle).coerceIn(0.1f, 0.35f)
            val color = particle.color.copy(alpha = alpha)

            when (particle.shape) {
                FloatingShape.STAR -> drawStar(
                    Offset(particle.x, particle.y),
                    particle.size * 0.5f,
                    color,
                    particle.rotation
                )

                FloatingShape.HEART -> drawHeart(
                    Offset(particle.x, particle.y),
                    particle.size * 0.6f,
                    color,
                    particle.rotation
                )

                FloatingShape.BALLOON -> drawBalloon(
                    Offset(particle.x, particle.y),
                    particle.size,
                    color
                )

                FloatingShape.CLOUD -> drawCloud(
                    Offset(particle.x, particle.y),
                    particle.size,
                    color
                )
            }
        }
    }
}

private fun rotatePoint(center: Offset, point: Offset, degrees: Float): Offset {
    val rad = degrees * Math.PI / 180.0
    val cosA = cos(rad).toFloat()
    val sinA = sin(rad).toFloat()
    val dx = point.x - center.x
    val dy = point.y - center.y
    val rx = dx * cosA - dy * sinA + center.x
    val ry = dx * sinA + dy * cosA + center.y
    return Offset(rx, ry)
}

private fun DrawScope.drawStar(
    center: Offset,
    radius: Float,
    color: Color,
    rotation: Float
) {
    val path = Path()
    val spikes = 5
    val outerRadius = radius
    val innerRadius = radius * 0.4f
    var angle = -90f
    val step = 360f / (spikes * 2)

    repeat(spikes) {
        // outer point
        val angRad = Math.toRadians(angle.toDouble())
        val x1 = center.x + outerRadius * cos(angRad).toFloat()
        val y1 = center.y + outerRadius * sin(angRad).toFloat()
        val p1 = rotatePoint(center, Offset(x1, y1), rotation)
        if (it == 0) path.moveTo(p1.x, p1.y) else path.lineTo(p1.x, p1.y)

        angle += step

        // inner point
        val angRad2 = Math.toRadians(angle.toDouble())
        val x2 = center.x + innerRadius * cos(angRad2).toFloat()
        val y2 = center.y + innerRadius * sin(angRad2).toFloat()
        val p2 = rotatePoint(center, Offset(x2, y2), rotation)
        path.lineTo(p2.x, p2.y)

        angle += step
    }
    path.close()
    drawPath(path, color)
}

private fun DrawScope.drawHeart(
    center: Offset,
    size: Float,
    color: Color,
    rotation: Float
) {
    val path = Path()

    val p0 = Offset(center.x, center.y + size * 0.3f)
    val c1 = Offset(center.x - size * 0.6f, center.y - size * 0.4f)
    val c2 = Offset(center.x - size, center.y + size * 0.3f)
    val p1 = Offset(center.x, center.y + size)
    val c3 = Offset(center.x + size, center.y + size * 0.3f)
    val c4 = Offset(center.x + size * 0.6f, center.y - size * 0.4f)

    // rotate points
    val rp0 = rotatePoint(center, p0, rotation)
    val rc1 = rotatePoint(center, c1, rotation)
    val rc2 = rotatePoint(center, c2, rotation)
    val rp1 = rotatePoint(center, p1, rotation)
    val rc3 = rotatePoint(center, c3, rotation)
    val rc4 = rotatePoint(center, c4, rotation)

    path.moveTo(rp0.x, rp0.y)
    path.cubicTo(rc1.x, rc1.y, rc2.x, rc2.y, rp1.x, rp1.y)
    path.cubicTo(rc3.x, rc3.y, rc4.x, rc4.y, rp0.x, rp0.y)
    drawPath(path, color)
}

private fun DrawScope.drawBalloon(
    center: Offset,
    size: Float,
    color: Color
) {
    drawOval(
        color = color,
        topLeft = Offset(center.x - size * 0.3f, center.y - size * 0.4f),
        size = androidx.compose.ui.geometry.Size(size * 0.6f, size * 0.8f)
    )
    drawLine(
        color = color.copy(alpha = 0.5f),
        start = Offset(center.x, center.y + size * 0.4f),
        end = Offset(center.x, center.y + size * 0.7f),
        strokeWidth = 2f
    )
}

private fun DrawScope.drawCloud(
    center: Offset,
    size: Float,
    color: Color
) {
    drawCircle(color, radius = size * 0.3f, center = Offset(center.x - size * 0.2f, center.y))
    drawCircle(color, radius = size * 0.4f, center = center)
    drawCircle(color, radius = size * 0.3f, center = Offset(center.x + size * 0.2f, center.y))
}
