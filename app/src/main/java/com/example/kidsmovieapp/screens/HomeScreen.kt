package com.example.kidsmovieapp.screens
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue


import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

class HomeScreen {
}

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

    val HeaderBackground = Color(0xFFFFFBFE)
    val CardBackground = Color(0xFFFFFFFF)

    // Text colors
    val TextPrimary = Color(0xFF2D1B69)
    val TextSecondary = Color(0xFF6B5B95)
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
private fun AnimatedPlayfulBackground(
    modifier: Modifier = Modifier.Companion,
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
        Brush.Companion.verticalGradient(
            colors = listOf(
                SafeKidsColors.BgPinkLight,
                SafeKidsColors.BgPurpleLight,
                SafeKidsColors.BgCyanLight
            )
        )
    }

    Canvas(modifier = modifier.background(gradientBrush)) {
        if (particles.isEmpty()) {
            val shapes = FloatingShape.values()
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
                    color,
                    particle.rotation
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

    rotate(rotation, pivot = center) {
        repeat(spikes) {
            val x1 = center.x + outerRadius * cos(Math.toRadians(angle.toDouble())).toFloat()
            val y1 = center.y + outerRadius * sin(Math.toRadians(angle.toDouble())).toFloat()
            if (it == 0) path.moveTo(x1, y1) else path.lineTo(x1, y1)
            angle += step
            val x2 = center.x + innerRadius * cos(Math.toRadians(angle.toDouble())).toFloat()
            val y2 = center.y + innerRadius * sin(Math.toRadians(angle.toDouble())).toFloat()
            path.lineTo(x2, y2)
            angle += step
        }
        path.close()
        drawPath(path, color)
    }
}

private fun DrawScope.drawHeart(
    center: Offset,
    size: Float,
    color: Color,
    rotation: Float
) {
    val path = Path()
    rotate(rotation, pivot = center) {
        path.moveTo(center.x, center.y + size * 0.3f)
        path.cubicTo(
            center.x - size * 0.6f, center.y - size * 0.4f,
            center.x - size, center.y + size * 0.3f,
            center.x, center.y + size
        )
        path.cubicTo(
            center.x + size, center.y + size * 0.3f,
            center.x + size * 0.6f, center.y - size * 0.4f,
            center.x, center.y + size * 0.3f
        )
        drawPath(path, color)
    }
}

private fun DrawScope.drawBalloon(
    center: Offset,
    size: Float,
    color: Color,
    rotation: Float
) {
    rotate(rotation, pivot = center) {
        drawOval(
            color = color,
            topLeft = Offset(center.x - size * 0.3f, center.y - size * 0.4f),
            size = Size(size * 0.6f, size * 0.8f)
        )
        drawLine(
            color = color.copy(alpha = 0.5f),
            start = Offset(center.x, center.y + size * 0.4f),
            end = Offset(center.x, center.y + size * 0.7f),
            strokeWidth = 2f
        )
    }
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


@Composable
fun SafeKidsLogo(
    size: Dp = 50.dp,
    modifier: Modifier = Modifier.Companion
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.Companion.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        Box(
            modifier = Modifier.Companion
                .size(size)
                .clip(CircleShape)
                .background(
                    brush = Brush.Companion.radialGradient(
                        colors = listOf(
                            SafeKidsColors.CandyTurquoise,
                            SafeKidsColors.CandyPink,
                            SafeKidsColors.CandyPurple
                        )
                    )
                ),
            contentAlignment = Alignment.Companion.Center
        ) {

            Box(
                modifier = Modifier.Companion
                    .fillMaxSize()
                    .clip(CircleShape)
                    .background(
                        brush = Brush.Companion.radialGradient(
                            colors = listOf(
                                Color.Companion.White.copy(alpha = 0.3f),
                                Color.Companion.Transparent
                            )
                        )
                    )
            )

            // Shield icon
            Icon(
                imageVector = Icons.Default.Shield,
                contentDescription = "Safe Kids Shield",
                modifier = Modifier.Companion.size(size * 0.5f),
                tint = Color.Companion.White
            )

            // Star accent
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = null,
                modifier = Modifier.Companion
                    .size(14.dp)
                    .offset(x = (size * 0.28f), y = -(size * 0.28f)),
                tint = SafeKidsColors.CandyYellow
            )
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(0.dp)
        ) {
            Text(
                text = "Safe Kids",
                fontSize = 20.sp,
                fontWeight = FontWeight.Companion.Bold,
                style = TextStyle(
                    brush = Brush.Companion.linearGradient(
                        colors = listOf(
                            SafeKidsColors.CandyPink,
                            SafeKidsColors.CandyPurple,
                            SafeKidsColors.CandyTurquoise
                        )
                    )
                ),
                lineHeight = 20.sp
            )

            Text(
                text = "Movie Discovery ✨",
                fontSize = 11.sp,
                color = SafeKidsColors.CandyPurple,
                lineHeight = 11.sp,
                modifier = Modifier.Companion.padding(top = 2.dp)
            )
        }
    }
}


@Composable
private fun SearchButton(
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier.Companion
) {
    var isPressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.9f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "search_button_scale"
    )

    Box(
        modifier = modifier
            .size(48.dp)
            .scale(scale)
            .clip(CircleShape)
            .background(
                brush = Brush.Companion.radialGradient(
                    colors = listOf(
                        SafeKidsColors.CandyTurquoise,
                        SafeKidsColors.CandyPink,
                        SafeKidsColors.CandyPurple
                    )
                )
            )
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = {
                    isPressed = true
                    onClick()
                }
            ),
        contentAlignment = Alignment.Companion.Center
    ) {
        Box(
            modifier = Modifier.Companion
                .size(36.dp)
                .clip(CircleShape)
                .background(Color.Companion.White),
            contentAlignment = Alignment.Companion.Center
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search Movies",
                tint = SafeKidsColors.CandyPurple,
                modifier = Modifier.Companion.size(20.dp)
            )
        }
    }

    LaunchedEffect(isPressed) {
        if (isPressed) {
            delay(150)
            isPressed = false
        }
    }
}

@Composable
private fun MovieCard(
    title: String,
    genre: String,
    ageRating: String,
    rating: Float,
    posterGradient: List<Color>,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier.Companion
) {
    var isPressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "movie_card_scale"
    )

    Card(
        modifier = modifier
            .fillMaxWidth()
            .scale(scale)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple(),
                onClick = {
                    isPressed = true
                    onClick()
                }
            ),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = SafeKidsColors.CardBackground
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        )
    ) {
        Column {

            Box(
                modifier = Modifier.Companion
                    .fillMaxWidth()
                    .height(180.dp)
                    .background(
                        brush = Brush.Companion.verticalGradient(colors = posterGradient)
                    )
            ) {

                Surface(
                    modifier = Modifier.Companion
                        .align(Alignment.Companion.TopStart)
                        .padding(8.dp),
                    color = SafeKidsColors.CandyLime,
                    shape = CircleShape,
                    shadowElevation = 2.dp
                ) {
                    Text(
                        text = ageRating,
                        color = Color.Companion.White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Companion.Bold,
                        modifier = Modifier.Companion.padding(horizontal = 10.dp, vertical = 5.dp)
                    )
                }


                Surface(
                    modifier = Modifier.Companion
                        .align(Alignment.Companion.TopEnd)
                        .padding(8.dp),
                    color = SafeKidsColors.CandyYellow,
                    shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp),
                    shadowElevation = 2.dp
                ) {
                    Row(
                        verticalAlignment = Alignment.Companion.CenterVertically,
                        modifier = Modifier.Companion.padding(horizontal = 8.dp, vertical = 5.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            tint = Color(0xFF8B6E00),
                            modifier = Modifier.Companion.size(14.dp)
                        )
                        Spacer(Modifier.Companion.width(4.dp))
                        Text(
                            text = String.format("%.1f", rating),
                            color = Color(0xFF8B6E00),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Companion.Bold
                        )
                    }
                }
            }

            Column(
                modifier = Modifier.Companion.padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = title,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Companion.Bold,
                    color = SafeKidsColors.TextPrimary,
                    maxLines = 2
                )

                Surface(
                    color = SafeKidsColors.CandyPurple.copy(alpha = 0.12f),
                    shape = androidx.compose.foundation.shape.RoundedCornerShape(10.dp)
                ) {
                    Text(
                        text = genre,
                        fontSize = 11.sp,
                        color = SafeKidsColors.CandyPurple,
                        fontWeight = FontWeight.Companion.SemiBold,
                        modifier = Modifier.Companion.padding(horizontal = 10.dp, vertical = 5.dp)
                    )
                }
            }
        }
    }

    LaunchedEffect(isPressed) {
        if (isPressed) {
            delay(100)
            isPressed = false
        }
    }
}

@Composable
private fun CategoryCard(
    title: String,
    description: String,
    emoji: String,
    gradientColors: List<Color>,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier.Companion
) {
    var isPressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "category_card_scale"
    )

    Card(
        modifier = modifier
            .fillMaxWidth()
            .scale(scale)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple(),
                onClick = {
                    isPressed = true
                    onClick()
                }
            ),
        shape = androidx.compose.foundation.shape.RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Companion.Transparent
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        )
    ) {
        Box(
            modifier = Modifier.Companion
                .fillMaxWidth()
                .background(
                    brush = Brush.Companion.horizontalGradient(colors = gradientColors)
                )
                .padding(16.dp)
        ) {

            Text(
                text = emoji,
                fontSize = 36.sp,
                modifier = Modifier.Companion
                    .align(Alignment.Companion.TopEnd)
                    .padding(4.dp),
                color = Color.Companion.White.copy(alpha = 0.2f)
            )

            Column {
                Text(
                    text = title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Companion.Bold,
                    color = Color.Companion.White
                )
                Spacer(Modifier.Companion.height(4.dp))
                Text(
                    text = description,
                    fontSize = 12.sp,
                    color = Color.Companion.White.copy(alpha = 0.9f),
                    fontWeight = FontWeight.Companion.Medium
                )
            }


            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = null,
                tint = Color.Companion.White.copy(alpha = 0.3f),
                modifier = Modifier.Companion
                    .size(14.dp)
                    .align(Alignment.Companion.TopStart)
            )
        }
    }


    LaunchedEffect(isPressed) {
        if (isPressed) {
            delay(100)
            isPressed = false
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SafeKidsHomeScreen() {
    val scrollState = rememberScrollState()

    val movies = remember {
        listOf(
            MovieData(
                "The Amazing Adventure",
                "Adventure",
                4.8f,
                listOf(
                    SafeKidsColors.CandyPurple.copy(0.4f),
                    SafeKidsColors.CandyTurquoise.copy(0.4f)
                )
            ),
            MovieData(
                "Space Friends Forever",
                "Animation",
                4.6f,
                listOf(SafeKidsColors.CandyPink.copy(0.4f), SafeKidsColors.CandyOrange.copy(0.4f))
            ),
            MovieData(
                "Ocean Quest",
                "Family",
                4.7f,
                listOf(
                    SafeKidsColors.CandyTurquoise.copy(0.4f),
                    SafeKidsColors.CandyMint.copy(0.4f)
                )
            ),
            MovieData(
                "Magic School Bus",
                "Educational",
                4.9f,
                listOf(SafeKidsColors.CandyYellow.copy(0.4f), SafeKidsColors.CandyLime.copy(0.4f))
            ),
            MovieData(
                "Superhero Pets",
                "Comedy",
                4.5f,
                listOf(SafeKidsColors.CandyOrange.copy(0.4f), SafeKidsColors.CandyPink.copy(0.4f))
            ),
            MovieData(
                "Rainbow Kingdom",
                "Fantasy",
                4.8f,
                listOf(SafeKidsColors.CandyPurple.copy(0.4f), SafeKidsColors.CandyPink.copy(0.4f))
            )
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = SafeKidsColors.HeaderBackground
                ),
                title = {
                    SafeKidsLogo(size = 48.dp)
                },
                actions = {
                    Box(modifier = Modifier.Companion.padding(end = 12.dp)) {
                        SearchButton()
                    }
                },
                modifier = Modifier.Companion.shadow(elevation = 2.dp)
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier.Companion
                .fillMaxSize()
                .padding(paddingValues)
        ) {

            AnimatedPlayfulBackground(
                modifier = Modifier.Companion.matchParentSize(),
                particleCount = 18
            )

            Column(
                modifier = Modifier.Companion
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {

                Card(
                    modifier = Modifier.Companion.fillMaxWidth(),
                    shape = androidx.compose.foundation.shape.RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = SafeKidsColors.CardBackground
                    ),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 4.dp
                    )
                ) {
                    Column(
                        modifier = Modifier.Companion.padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(
                            text = "Discover Amazing Movies! ✨",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Companion.Bold,
                            color = SafeKidsColors.TextPrimary
                        )
                        Text(
                            text = "Safe and fun adventures just for you! 🎬",
                            fontSize = 14.sp,
                            color = SafeKidsColors.TextSecondary
                        )
                    }
                }

                Row(
                    verticalAlignment = Alignment.Companion.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = SafeKidsColors.CandyYellow,
                        modifier = Modifier.Companion.size(22.dp)
                    )
                    Text(
                        text = "Popular Kids Movies",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Companion.Bold,
                        color = SafeKidsColors.TextPrimary
                    )
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = null,
                        tint = SafeKidsColors.CandyPink,
                        modifier = Modifier.Companion.size(20.dp)
                    )
                }

                Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    movies.chunked(2).forEach { rowMovies ->
                        Row(
                            modifier = Modifier.Companion.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            rowMovies.forEach { movie ->
                                MovieCard(
                                    title = movie.title,
                                    genre = movie.genre,
                                    ageRating = "G",
                                    rating = movie.rating,
                                    posterGradient = movie.posterGradient,
                                    modifier = Modifier.Companion.weight(1f)
                                )
                            }
                            // Fill empty space if odd number
                            if (rowMovies.size == 1) {
                                Spacer(Modifier.Companion.weight(1f))
                            }
                        }
                    }
                }

                Row(
                    verticalAlignment = Alignment.Companion.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Category,
                        contentDescription = null,
                        tint = SafeKidsColors.CandyTurquoise,
                        modifier = Modifier.Companion.size(22.dp)
                    )
                    Text(
                        text = "Browse by Category",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Companion.Bold,
                        color = SafeKidsColors.TextPrimary
                    )
                }

                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {

                    Row(
                        modifier = Modifier.Companion.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        CategoryCard(
                            title = "Adventure",
                            description = "Exciting journeys!",
                            emoji = "🗺️",
                            gradientColors = listOf(
                                SafeKidsColors.CandyPink,
                                Color(0xFFFF6347),
                                SafeKidsColors.CandyOrange
                            ),
                            modifier = Modifier.Companion.weight(1f)
                        )
                        CategoryCard(
                            title = "Comedy",
                            description = "Laugh out loud!",
                            emoji = "😂",
                            gradientColors = listOf(
                                SafeKidsColors.CandyLime,
                                SafeKidsColors.CandyMint,
                                SafeKidsColors.CandyTurquoise
                            ),
                            modifier = Modifier.Companion.weight(1f)
                        )
                    }

                    Row(
                        modifier = Modifier.Companion.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        CategoryCard(
                            title = "Musical",
                            description = "Sing along!",
                            emoji = "🎵",
                            gradientColors = listOf(
                                SafeKidsColors.CandyYellow,
                                SafeKidsColors.CandyOrange,
                                Color(0xFFFF6347)
                            ),
                            modifier = Modifier.Companion.weight(1f)
                        )
                        CategoryCard(
                            title = "Fantasy",
                            description = "Magical worlds!",
                            emoji = "✨",
                            gradientColors = listOf(
                                SafeKidsColors.CandyPurple,
                                SafeKidsColors.CandyPink,
                                Color(0xFF6366F1)
                            ),
                            modifier = Modifier.Companion.weight(1f)
                        )
                    }
                }


                Spacer(Modifier.Companion.height(20.dp))
            }
        }
    }
}


private data class MovieData(
    val title: String,
    val genre: String,
    val rating: Float,
    val posterGradient: List<Color>
)

@Preview(showBackground = true)
@Composable
private fun PreviewSafeKidsHomeScreen() {
    SafeKidsHomeScreen()
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun PreviewSafeKidsLogo() {
    Box(
        modifier = Modifier.Companion
            .background(Color.Companion.White)
            .padding(24.dp)
    ) {
        SafeKidsLogo()
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun PreviewSearchButton() {
    Box(
        modifier = Modifier.Companion
            .background(Color.Companion.White)
            .padding(24.dp)
    ) {
        SearchButton()
    }
}