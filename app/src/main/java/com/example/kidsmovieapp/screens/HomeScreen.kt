package com.example.kidsmovieapp.screens

import androidx.compose.ui.text.style.TextOverflow

import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import androidx.compose.runtime.collectAsState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.clickable
import androidx.compose.material3.*
import androidx.compose.ui.draw.scale
import kotlinx.coroutines.delay
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import com.example.kidsmovieapp.data.remote.dto.MovieDto

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


private fun Random.nextFloatInRange(min: Float, max: Float) = nextFloat() * (max - min) + min


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
fun AnimatedPlayfulBackground(
    modifier: Modifier = Modifier.Companion, particleCount: Int = 18
) {
    val random = remember { Random(System.currentTimeMillis()) }
    val particles = remember { mutableStateListOf<FloatingParticle>() }

    val infiniteTransition = rememberInfiniteTransition(label = "bg_animation")
    val animationPhase by infiniteTransition.animateFloat(
        initialValue = 0f, targetValue = 1f, animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = LinearEasing), repeatMode = RepeatMode.Restart
        ), label = "phase"
    )

    val gradientBrush = remember {
        Brush.Companion.verticalGradient(
            colors = listOf(
                SafeKidsColors.BgPinkLight, SafeKidsColors.BgPurpleLight, SafeKidsColors.BgCyanLight
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
                    Offset(particle.x, particle.y), particle.size * 0.5f, color, particle.rotation
                )

                FloatingShape.HEART -> drawHeart(
                    Offset(particle.x, particle.y), particle.size * 0.6f, color, particle.rotation
                )

                FloatingShape.BALLOON -> drawBalloon(
                    Offset(particle.x, particle.y), particle.size, color, particle.rotation
                )

                FloatingShape.CLOUD -> drawCloud(
                    Offset(particle.x, particle.y), particle.size, color
                )
            }
        }
    }
}

private fun DrawScope.drawStar(
    center: Offset, radius: Float, color: Color, rotation: Float
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
    center: Offset, size: Float,

    color: Color, rotation: Float
) {
    val path = Path()
    rotate(rotation, pivot = center) {
        path.moveTo(center.x, center.y + size * 0.3f)
        path.cubicTo(
            center.x - size * 0.6f,
            center.y - size * 0.4f,
            center.x - size,
            center.y + size * 0.3f,
            center.x,
            center.y + size
        )
        path.cubicTo(
            center.x + size,
            center.y + size * 0.3f,
            center.x + size * 0.6f,
            center.y - size * 0.4f,
            center.x,
            center.y + size * 0.3f
        )
        drawPath(path, color)
    }
}

private fun DrawScope.drawBalloon(
    center: Offset, size: Float, color: Color, rotation: Float
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
    center: Offset, size: Float, color: Color
) {
    drawCircle(color, radius = size * 0.3f, center = Offset(center.x - size * 0.2f, center.y))
    drawCircle(color, radius = size * 0.4f, center = center)
    drawCircle(color, radius = size * 0.3f, center = Offset(center.x + size * 0.2f, center.y))
}


@Composable
fun SafeKidsLogo(
    size: Dp = 50.dp, modifier: Modifier = Modifier.Companion
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
                ), contentAlignment = Alignment.Companion.Center
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


            Icon(
                imageVector = Icons.Default.Shield,
                contentDescription = "Safe Kids Shield",
                modifier = Modifier.Companion.size(size * 0.5f),
                tint = Color.Companion.White
            )


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
    onClick: () -> Unit = {}, modifier: Modifier = Modifier.Companion
) {
    var isPressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.9f else 1f, animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessLow
        ), label = "search_button_scale"
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
                }), contentAlignment = Alignment.Companion.Center) {
        Box(
            modifier = Modifier.Companion
                .size(36.dp)
                .clip(CircleShape)
                .background(Color.Companion.White), contentAlignment = Alignment.Companion.Center
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
fun FavoriteButton(
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    var isPressed by remember { mutableStateOf(false) }

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.9f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "favorite_button_scale"
    )

    Box(
        modifier = modifier
            .size(48.dp)
            .scale(scale)
            .clip(CircleShape)
            .background(
                brush = Brush.radialGradient(
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
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = "Favorites",
                tint = SafeKidsColors.CandyPink,
                modifier = Modifier.size(20.dp)
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


@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun SafeKidsHomeScreen(
    viewModel: MovieViewModel = viewModel(),
    onMovieClick: (MovieDto) -> Unit,
    onSearchClick: () -> Unit,
    onFavoritesClick: () -> Unit
) {
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
        val kidsMovies by viewModel.kidsMovies.collectAsState()
        val isLoading by viewModel.isLoading.collectAsState()

        LaunchedEffect(Unit) {
            viewModel.loadKidsMovies()
        }

        Scaffold(
            topBar = {
                TopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = SafeKidsColors.HeaderBackground
                    ), title = { SafeKidsLogo(size = 48.dp) }, actions = {
                        FavoriteButton(onClick = onFavoritesClick)

                        Box(modifier = Modifier.padding(end = 12.dp)) {
                            SearchButton(onClick = onSearchClick)
                        }
                    }, modifier = Modifier.shadow(elevation = 2.dp)
                )
            }) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                AnimatedPlayfulBackground(
                    modifier = Modifier.matchParentSize(), particleCount = 18
                )

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    item(span = { GridItemSpan(2) }) {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(24.dp),
                            colors = CardDefaults.cardColors(containerColor = SafeKidsColors.CardBackground),
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(20.dp),
                                verticalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Text(
                                    text = "Discover Amazing Movies! ✨",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = SafeKidsColors.TextPrimary
                                )
                                Text(
                                    text = "Safe and fun adventures just for you! 🎬",
                                    fontSize = 14.sp,
                                    color = SafeKidsColors.TextSecondary
                                )
                            }
                        }
                    }

                    item(span = { GridItemSpan(2) }) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Favorite,
                                contentDescription = null,
                                tint = SafeKidsColors.CandyPink,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Popular Kids Movies",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = SafeKidsColors.TextPrimary
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = null,
                                tint = SafeKidsColors.CandyYellow,
                                modifier = Modifier.size(22.dp)
                            )
                        }
                    }

                    items(kidsMovies.size) { index ->
                        val movie = kidsMovies[index]

                        MovieCardDynamic(
                            title = movie.title ?: "No Title",
                            rating = (movie.vote_average ?: 0.0).toFloat(),
                            imageUrl = "https://image.tmdb.org/t/p/w500${movie.poster_path}",
                            modifier = Modifier.fillMaxWidth(),
                            onClick = { onMovieClick(movie) })

                        if (index >= kidsMovies.size - 4 && !isLoading) {
                            viewModel.loadMoreKidsMovies()
                        }
                    }

                    item(span = { GridItemSpan(2) }) {
                        if (isLoading) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(24.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator(
                                    color = SafeKidsColors.CandyPurple
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}


private data class MovieData(
    val title: String, val genre: String, val rating: Float, val posterGradient: List<Color>
)


@Preview(showBackground = true)
@Composable
private fun PreviewSafeKidsHomeScreen() {
    SafeKidsHomeScreen(onMovieClick = {}, onSearchClick = {}, onFavoritesClick = {})
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

@Composable
fun MovieCardDynamic(
    title: String,
    rating: Float,
    imageUrl: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
        var isPressed by remember { mutableStateOf(false) }

        val scale by animateFloatAsState(
            targetValue = if (isPressed) 0.95f else 1f, animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessMedium
            ), label = "poster_scale"
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
                    }),
            shape = RoundedCornerShape(24.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {

                AsyncImage(
                    model = imageUrl,
                    contentDescription = title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp)
                        .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                        .background(Color.LightGray.copy(alpha = 0.2f))
                )

                Column(
                    modifier = Modifier.padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = title,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = SafeKidsColors.TextPrimary,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )


                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            tint = SafeKidsColors.CandyYellow,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(Modifier.width(4.dp))
                        Text(
                            text = String.format("%.1f", rating),
                            fontSize = 12.sp,
                            color = SafeKidsColors.TextSecondary
                        )
                    }
                }
            }
        }


        LaunchedEffect(isPressed) {
            if (isPressed) {
                delay(150)
                isPressed = false
            }
        }
    }
}
