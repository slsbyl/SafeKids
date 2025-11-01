package com.example.kidsmovieapp.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import com.example.kidsmovieapp.ui.theme.*
@Composable
fun SplashScreen(onFinish: () -> Unit) {
    var showSubtitle by remember { mutableStateOf(false) }
    val screenAlpha = remember { Animatable(1f) }

    LaunchedEffect(Unit) {
        // The delaying of the subtitle and the fading out of the splashscreen
        delay(2000)
        showSubtitle = true
        delay(5000)
        screenAlpha.animateTo(0f, animationSpec = tween(800))
        onFinish()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .alpha(screenAlpha.value)
            .background(
                Brush.linearGradient(
                    listOf(Pink, purple)
                )
            )
    ) {

        // Background
        val circleColors = listOf(CandyPink, CandySky, CandyPurple)
        val infiniteTransition = rememberInfiniteTransition(label = "circles")

        val floatY by infiniteTransition.animateFloat(
            initialValue = -10f,
            targetValue = 10f,
            animationSpec = infiniteRepeatable(
                animation = tween(4000, easing = LinearEasing),
                repeatMode = RepeatMode.Reverse
            ),
            label = ""
        )
        Box(
            modifier = Modifier
                .size(180.dp)
                .offset(x = (-60).dp, y = (-40).dp + floatY.dp)
                .background(
                    Brush.radialGradient(
                        listOf(circleColors[0].copy(alpha = 0.35f), Color.Transparent)
                    ),
                    shape = CircleShape
                )
        )
        Box(
            modifier = Modifier
                .size(200.dp)
                .offset(x = (220).dp, y = (600).dp + floatY.dp)
                .background(
                    Brush.radialGradient(
                        listOf(circleColors[1].copy(alpha = 0.35f), Color.Transparent)
                    ),
                    shape = CircleShape
                )
        )
        Box(
            modifier = Modifier
                .size(160.dp)
                .offset(x = (280).dp, y = (300).dp - floatY.dp)
                .background(
                    Brush.radialGradient(
                        listOf(circleColors[2].copy(alpha = 0.35f), Color.Transparent)
                    ),
                    shape = CircleShape
                )
        )

        //  random animated small ballons
        val capsuleColors = listOf(CandyPink, CandyOrange, CandyLime, CandySky)
        repeat(20) { i ->
            val infiniteTransition = rememberInfiniteTransition(label = "capsule")

            val offsetY by infiniteTransition.animateFloat(
                initialValue = 1200f,
                targetValue = -300f,
                animationSpec = infiniteRepeatable(
                    animation = tween(4000 + i * 250, easing = LinearEasing),
                    repeatMode = RepeatMode.Restart
                ),
                label = ""
            )

            val offsetX by infiniteTransition.animateFloat(
                initialValue = -20f,
                targetValue = 20f,
                animationSpec = infiniteRepeatable(
                    animation = tween(1500 + i * 120, easing = LinearEasing),
                    repeatMode = RepeatMode.Reverse
                ),
                label = ""
            )
            val width = remember { (10..18).random() }
            val height = remember { (20..34).random() }
            val baseX = remember { (20..340).random().toFloat() }

            Box(
                modifier = Modifier
                    .size(width.dp, height.dp)
                    .offset(x = baseX.dp + offsetX.dp, y = offsetY.dp)
                    .clip(RoundedCornerShape(50))
                    .background(capsuleColors[i % capsuleColors.size])
            )
        }

        // Stars
        repeat(15) { i ->
            val infiniteTransition = rememberInfiniteTransition(label = "star")
            val alpha by infiniteTransition.animateFloat(
                initialValue = 0.2f,
                targetValue = 1f,
                animationSpec = infiniteRepeatable(
                    animation = tween(1200 + i * 150, easing = LinearEasing),
                    repeatMode = RepeatMode.Reverse
                ),
                label = ""
            )
            val offsetX = remember { (0..350).random().toFloat() }
            val offsetY = remember { (0..700).random().toFloat() }
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .offset(x = offsetX.dp, y = offsetY.dp)
                    .alpha(alpha)
                    .clip(CircleShape)
                    .background(CandyYellow)
            )
        }

        // Logo + Text
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id =com.example.kidsmovieapp.R.drawable.safemovies
                ),
                contentDescription = "App Logo",
                modifier = Modifier
                    .size(150.dp)
                    .clip(RoundedCornerShape(24.dp))

                    .padding(12.dp),
                contentScale = ContentScale.Fit
            )
            GradientText(
                text = "Safe Kids",
                gradient = Brush.linearGradient(listOf(CandyPink, CandyPurple, CandyTurquoise))
            )
            Text(
                text = "Movie Discovery",
                fontSize = 18.sp,
                color = CandyPurple
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Animated Subtitle
            AnimatedVisibility(
                visible = showSubtitle,
                enter = fadeIn(
                    animationSpec = tween(durationMillis = 800, easing = FastOutSlowInEasing)
                ) + slideInVertically(
                    initialOffsetY = { it / 2 },
                    animationSpec = tween(durationMillis = 800, easing = FastOutSlowInEasing)
                ),
                exit = fadeOut(
                    animationSpec = tween(durationMillis = 500, easing = FastOutLinearInEasing)
                ) + slideOutVertically(
                    targetOffsetY = { it / 2 },
                    animationSpec = tween(durationMillis = 500, easing = FastOutLinearInEasing)
                )
            ) {
                Text(
                    text = "🎬 Fun & Safe Movies for You! 🌟",
                    fontSize = 16.sp,
                    color = Color(0xFF3F51B5),
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}
@Composable
//styling the text
fun GradientText(
    text: String,
    fontSize: TextUnit = 34.sp,
    fontWeight: FontWeight = FontWeight.Bold,
    gradient: Brush
) {
    Text(
        text = text,
        fontSize = fontSize,
        fontWeight = fontWeight,
        style = androidx.compose.ui.text.TextStyle(
            brush = gradient
        )
    )
}
//preview
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SplashScreenPreview() {
    KidsMovieAppTheme {
        SplashScreen(onFinish = {})
    }
}

