package com.example.kidsmovieapp.screens


import android.content.Intent
import android.net.Uri
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.kidsmovieapp.data.remote.dto.MovieDto
import com.example.kidsmovieapp.ui.theme.BlueGradient
import com.example.kidsmovieapp.ui.theme.BrightPurpleText
import com.example.kidsmovieapp.ui.theme.DarkPurpleText
import com.example.kidsmovieapp.ui.theme.Gold
import com.example.kidsmovieapp.ui.theme.PinkAccent
import com.example.kidsmovieapp.ui.theme.PinkGradient
import com.example.kidsmovieapp.ui.theme.PurpleAccent
import com.example.kidsmovieapp.ui.theme.TealAccent
import com.example.kidsmovieapp.ui.theme.buttonGradient
import com.example.kidsmovieapp.AnimatedBackground
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection


val genreMap = mapOf(
    28 to "Action",
    12 to "Adventure",
    16 to "Animation",
    35 to "Comedy",
    80 to "Crime",
    99 to "Documentary",
    18 to "Drama",
    10751 to "Family",
    14 to "Fantasy",
    36 to "History",
    27 to "Horror",
    10402 to "Music",
    9648 to "Mystery",
    10749 to "Romance",
    878 to "Science Fiction",
    10770 to "TV Movie",
    53 to "Thriller",
    10752 to "War",
    37 to "Western"
)


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    movieId: Int,
    viewModel: MovieViewModel = viewModel(),
    onBackClicked: () -> Unit
) {

    val isFavorite by viewModel.isFavorite.collectAsState()

    LaunchedEffect(key1 = movieId) {
        viewModel.getMovieDetails(movieId)

        viewModel.checkIsFavorite(movieId)
    }

    val movie by viewModel.selectedMovie.collectAsState()

    Scaffold(
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(Color.White)
            ) {
                if (movie == null || movie?.id != movieId) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                } else {
                    MovieDetailScreen(
                        movie = movie!!,
                        isFavorite = isFavorite,
                        onToggleFavorite = { viewModel.toggleFavorite(movie!!) },
                        onBackClicked = onBackClicked
                    )
                }
            }
        }
    )
}


@Composable
fun MovieDetailScreen(
    movie: MovieDto,
    isFavorite: Boolean,
    onToggleFavorite: () -> Unit,
    onBackClicked: () -> Unit
) {
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
        val context = LocalContext.current

        val scrollState = rememberScrollState()

        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            AnimatedBackground(modifier = Modifier.fillMaxSize())

            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                //App bar
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(top = 26.dp, start = 20.dp, end = 20.dp, bottom = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = onBackClicked,
                        modifier = Modifier.size(40.dp)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White,
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    brush = Brush.horizontalGradient(
                                        listOf(
                                            BlueGradient,
                                            PinkGradient
                                        )
                                    ),
                                    shape = CircleShape
                                )
                                .padding(8.dp)
                        )
                    }

                    Text(
                        text = "Movie Details ✨",
                        fontWeight = FontWeight.Bold,
                        color = DarkPurpleText,
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Center,
                        fontSize = 25.sp
                    )

                    Spacer(modifier = Modifier.size(40.dp))
                }

                // Scrollable below app bar
                Column(
                    modifier = Modifier
                        .verticalScroll(scrollState)
                        .fillMaxSize()
                        .padding(horizontal = 20.dp)
                ) {
                    Spacer(modifier = Modifier.height(20.dp))

                    // Movie poster and info
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.Top
                    ) {
                        AsyncImage(
                            model = "https://image.tmdb.org/t/p/w500${movie.poster_path}",
                            contentDescription = movie.title,
                            modifier = Modifier
                                .width(130.dp)
                                .height(190.dp)
                                .clip(RoundedCornerShape(16.dp)),
                            contentScale = ContentScale.Crop
                        )

                        Spacer(modifier = Modifier.width(16.dp))

                        Column {
                            Text(
                                text = movie.title ?: "Untitled",
                                color = DarkPurpleText,
                                fontSize = 28.sp,
                                fontWeight = FontWeight.Bold,
                                lineHeight = 30.sp
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            //Show Genres
                            val genreNames = movie.genres?.map { it.name }
                                ?: movie.genre_ids?.map { genreMap[it] ?: "Unknown" }
                                ?: emptyList()

                            FlowRow(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                genreNames.forEach { genreName ->
                                    InfoTag(
                                        text = genreName,
                                        backgroundColor = TealAccent
                                    )
                                }
                            }


                            Spacer(modifier = Modifier.height(12.dp))

                            //Show rating and year
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                IconInfoTag(
                                    text = String.format("%.1f", movie.vote_average ?: 0.0),
                                    backgroundColor = Gold,
                                    icon = Icons.Default.Star,
                                    isCircle = true
                                )

                                Spacer(modifier = Modifier.width(8.dp))

                                movie.release_date?.let {
                                    IconInfoTag(
                                        text = it.take(4),
                                        backgroundColor = PurpleAccent,
                                        icon = Icons.Default.DateRange,
                                        isCircle = true
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // About section
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Default.Info,
                            contentDescription = "About",
                            tint = PinkAccent
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "About this amazing movie!",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = DarkPurpleText
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Card(
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = movie.overview ?: "No overview available.",
                            modifier = Modifier.padding(18.dp),
                            lineHeight = 22.sp,
                            color = BrightPurpleText
                        )
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    // Buttons
                    //Rotate play icon
                    val rotate = rememberInfiniteTransition(label = "rotate_play_icon")
                    val rotation by rotate.animateFloat(
                        initialValue = 0f,
                        targetValue = 360f,
                        animationSpec = infiniteRepeatable(
                            animation = tween(durationMillis = 3000, easing = LinearEasing),
                            repeatMode = RepeatMode.Restart
                        ),
                        label = "icon_rotation"
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    movie.trailerUrl?.let { trailerUrl ->
                        Button(
                            onClick = {
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(trailerUrl))
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                context.startActivity(intent)
                            },
                            shape = RoundedCornerShape(50),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp),
                            contentPadding = PaddingValues(),
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(buttonGradient),
                                contentAlignment = Alignment.Center
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        Icons.Default.PlayArrow,
                                        contentDescription = null,
                                        tint = Color.White,
                                        modifier = Modifier
                                            .graphicsLayer {
                                                rotationZ = rotation
                                            }
                                            .size(24.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        "Watch Trailer 📽️",
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 20.sp
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    //Beats heart icon
                    val heartTransition = rememberInfiniteTransition(label = "heartBeat")
                    val scale by heartTransition.animateFloat(
                        initialValue = 1f,
                        targetValue = 1.4f,
                        animationSpec = infiniteRepeatable(
                            animation = tween(400, easing = LinearOutSlowInEasing),
                            repeatMode = RepeatMode.Reverse
                        ),
                        label = "scaleAnim"
                    )

                    Button(
                        onClick = onToggleFavorite,
                        shape = RoundedCornerShape(50),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                        border = BorderStroke(2.dp, PinkAccent),
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(

                                imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                contentDescription = null,
                                tint = PinkAccent,
                                modifier = Modifier
                                    .graphicsLayer(
                                        scaleX = scale,
                                        scaleY = scale
                                    )
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(

                                text = if (isFavorite) "Added To Favorites💖" else "Add To Favorites❤️",
                                color = PinkAccent,
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(80.dp))
                }
            }
        }
    }
}


@Composable
fun InfoTag(text: String, backgroundColor: Color, isCircle: Boolean = false) {
    val shape = if (isCircle) CircleShape else RoundedCornerShape(50)
    Box(
        modifier = Modifier
            .background(backgroundColor, shape)
            .padding(horizontal = 10.dp, vertical = 4.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = Color.White,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun IconInfoTag(
    text: String,
    backgroundColor: Color,
    icon: ImageVector,
    isCircle: Boolean = false
) {
    val shape = if (isCircle) CircleShape else RoundedCornerShape(50)

    Row(
        modifier = Modifier
            .background(backgroundColor, shape)
            .padding(horizontal = 10.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = text,
            tint = Color.White,
            modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = text,
            color = Color.White,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )
    }
}