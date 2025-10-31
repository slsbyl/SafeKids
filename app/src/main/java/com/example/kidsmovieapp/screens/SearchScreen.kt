package com.example.kidsmovieapp.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.*
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.kidsmovieapp.R
import com.example.kidsmovieapp.ui.viewmodel.MovieViewModel
import androidx.compose.ui.tooling.preview.Preview
import com.example.kidsmovieapp.data.remote.dto.MovieDto

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    viewModel: MovieViewModel = viewModel(),
    onClose: () -> Unit = {},
    onMovieClick: (MovieDto) -> Unit = {}
) {
    var query by remember { mutableStateOf(TextFieldValue("")) }

    // loading movies
    LaunchedEffect(Unit) {
        viewModel.loadKidsMovies()
    }
    val kidsMovies by viewModel.kidsMovies.collectAsState()
    val filteredResults = kidsMovies.filter { movie ->
        query.text.isEmpty() || movie.title?.contains(query.text, ignoreCase = true) == true
    }

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
    // Top Bar
    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(8.dp)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(Color.White, Color(0xFFF8EAFE))
                        )
                    )
                    .padding(vertical = 16.dp, horizontal = 12.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    IconButton(onClick = onClose) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .background(
                                    brush = Brush.linearGradient(
                                        colors = listOf(
                                            Color(0xFFE91E63),
                                            Color(0xFF9C27B0)
                                        )
                                    ),
                                    shape = RoundedCornerShape(45.dp)
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Close",
                                tint = Color.White
                            )
                        }
                    }
                    IconButton(onClick = {},
                        enabled = false
                    ) {
                        Icon(
                            Icons.Default.Search,
                            contentDescription = "Search",
                            tint = Color(0xFF00B7D9)
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        "Search Movies ✨",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF6D11AF)
                    )
                }
            }
        }
    ) { paddingValues ->
        val focusManager = LocalFocusManager.current
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
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) {
                    focusManager.clearFocus()
                }
        ) {
            // Search Box
            var isFocused by remember { mutableStateOf(false) }
            OutlinedTextField(
                value = query,
                onValueChange = { query = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(12.dp, shape = RoundedCornerShape(50.dp))
                    .onFocusChanged { focusState ->
                        isFocused = focusState.isFocused
                    },
                placeholder = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            "Search for amazing kids movies!", color = Color(0xFFC079FD)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("🎬", fontSize = 16.sp)
                    }
                },
                shape = RoundedCornerShape(50.dp),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor =  if (isFocused) Color(0xFFFF69B4) else Color(0xFFC079FD),
                    unfocusedIndicatorColor = Color(0xFFC079FD),
                    cursorColor = Color(0xFFC079FD),
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White
                ),
                trailingIcon = {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .graphicsLayer {
                                rotationZ = rotation
                                alpha = if (isFocused) glowAlpha else 0.3f
                                spotShadowColor = Color.Gray
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text("✨", fontSize = 20.sp, color = Color(0xFFE91E63))
                    }
                }
            )

            Spacer(modifier = Modifier.height(24.dp))

            //Result
            if (query.text.isEmpty()) {
                CenteredDefaultContent()

            } else if (filteredResults.isEmpty()) {
                val infiniteTransitionSearch = rememberInfiniteTransition(label = "searchIconAnim")
                val rotationSearch by infiniteTransitionSearch.animateFloat(
                    initialValue = -10f,
                    targetValue = 10f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(1000, easing = LinearEasing),
                        repeatMode = RepeatMode.Reverse
                    ),
                    label = "rotationSearchAnim"
                )

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {
                    Text(
                        "No movies found! 😔",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF6D11AF)
                    )
                    Spacer(modifier = Modifier.height(50.dp))

                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .shadow(10.dp, shape = RoundedCornerShape(50.dp))
                            .background(
                                brush = Brush.linearGradient(
                                    colors = listOf(Color(0xFFB3E5FC), Color(0xFFE1BEE7))
                                ),
                                shape = RoundedCornerShape(50.dp)
                            )
                            .graphicsLayer { rotationZ = rotationSearch },
                        contentAlignment = Alignment.Center,

                        ) {
                        Text("🔍", fontSize = 30.sp)
                    }
                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        "No movies found! 🔍",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF6D11AF)
                    )

                    Text(
                        "Try searching for something else, explore! ✨",
                        fontSize = 16.sp,
                        color = Color(0xFF9710F8)
                    )
                }

            } else {
                Column {
                    Text(
                        "Found ${filteredResults.size} amazing movies! 🎉",
                        fontSize = 20.sp,
                        color = Color(0xFF6D11AF)
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(filteredResults) { movie ->
                            MovieItem(
                                title = movie.title ?:"No title",
                                posterPath = movie.poster_path,
                                rating = (movie.vote_average ?: 0.0).toFloat(),
                                onClick = { onMovieClick(movie) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CenteredDefaultContent(){
    val infiniteTransition = rememberInfiniteTransition(label = "logoAnim")
    val rotationLogo by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(4000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "logoRotationAnim"
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 15.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
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
                painter = painterResource(R.drawable.telescope),
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
            "Start your movie adventure!🌟",
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



@Composable
fun MovieItem(
    title: String,
    posterPath: String?,
    rating: Float,
    onClick: () -> Unit
){
    Card (
        modifier = Modifier
            .fillMaxSize()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ){
        Row(
            modifier = Modifier
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = "https://image.tmdb.org/t/p/w500${posterPath ?: ""}",
                contentDescription = title,
                modifier = Modifier
                    .size(100.dp)
                    .background(Color.LightGray.copy(alpha = 0.3f)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(title, style = MaterialTheme.typography.titleMedium, color = Color(0xFF2D1B69))
                Text("⭐ ${String.format("%.1f", rating)}", color = Color(0xFF6B5B95))
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewSearchScreen() {
    SearchScreen()
}
