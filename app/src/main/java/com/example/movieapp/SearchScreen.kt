package com.example.movieapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.animation.core.*



@OptIn(ExperimentalMaterial3Api::class)


@Composable
fun SearchScreen(
    onClose: () -> Unit = {}
) {
    var query by remember { mutableStateOf("") }

    // Animation
    val infiniteTransition = rememberInfiniteTransition(label = "starAnim")

    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(4000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotationAnim"
    )
    val glowAlpha by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "glowAnim"
    )
    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(13.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(top = 15.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {

                    Row {
                        IconButton(onClick = onClose){
                            Box(
                                modifier = Modifier
                                    .size(35.dp)
                                    .background(
                                        brush = Brush.linearGradient(
                                            colors = listOf(
                                                Color(0xFFE91E63),
                                                Color(0xFF9C27B0))
                                        ),
                                        shape = RoundedCornerShape(50.dp)
                                    ),
                                contentAlignment = Alignment.Center
                            ) {

                                Icon(
                                    Icons.Default.Close,
                                    contentDescription = "Close",
                                    tint = Color.White
                                )
                            }
                        }
                        IconButton(onClick = {  }) {
                            Icon(
                                Icons.Default.Search,
                                contentDescription = "Search",
                                tint = Color(0xFF00B7D9)
                            )
                        }
                    }

                    Column {
                        Text(
                            "Search Movies ✨",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF6D11AF)
                        )

                    }

                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color(0xFFFFC1E3), Color(0xFFB3E5FC))
                    )
                )
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            // Search Box
            OutlinedTextField(
                value = query,
                onValueChange = { query = it },
                placeholder = {
                    Row (verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            "Search for amazing kids movies!", color = Color(0xFFC079FD)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("🎬", fontSize = 16.sp)
                    }
                },
                singleLine = true,

                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(12.dp, shape = RoundedCornerShape(50.dp)),

                shape = RoundedCornerShape(50.dp),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color(0xFFFF69B4),
                    unfocusedIndicatorColor = Color(0xFFC079FD),
                    cursorColor = Color(0xFFC079FD),
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White
                ),
                trailingIcon = {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .graphicsLayer{
                                rotationZ = rotation
                                alpha = glowAlpha
                                shadowElevation = 20f
                            },
                        contentAlignment = Alignment.Center
                    ){
                        Text("✨", fontSize = 20.sp, color = Color(0xFFE91E63))
                    }
                }
            )

            Spacer(modifier = Modifier.height(48.dp))

            // Center Message
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 15.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {

                // Telescope rotation
                val rotationLogo by infiniteTransition.animateFloat(
                    initialValue = 0f,
                    targetValue = 360f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(4000, easing = LinearEasing),
                        repeatMode = RepeatMode.Restart
                    ),
                    label = "logoRotationAnim"
                )

                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .shadow(10.dp, shape = RoundedCornerShape(50.dp))
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf(
                                    Color(0xFFB3E5FC),
                                    Color(0xFFE1BEE7)
                                )
                            ),
                            shape = RoundedCornerShape(50.dp)
                        )
                        .graphicsLayer{
                            rotationZ = rotationLogo
                        },
                    contentAlignment = Alignment.Center
                ){
                    Image(
                        painter = painterResource(id = R.drawable.telescope),
                        contentDescription = "App Logo",
                        modifier = Modifier
                            .size(150.dp)
                            .clip(RoundedCornerShape(24.dp))
                            .padding(12.dp),
                        contentScale = ContentScale.Fit
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    "Start your movie adventure! 🌟",
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF6D11AF)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    "Type in the magical search box above",
                    fontSize = 16.sp,
                    color = Color(0xFF9710F8)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    "to discover amazing movies! 🎬✨",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFAC45FD)
                )
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun PreviewSearchScreen() {
    SearchScreen()
}