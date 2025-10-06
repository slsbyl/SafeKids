package com.example.kidsmovieapp.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kidsmovieapp.AnimatedBackground
import com.example.kidsmovieapp.R
import com.example.kidsmovieapp.ui.theme.BlueGradient
import com.example.kidsmovieapp.ui.theme.BrightPurpleText
import com.example.kidsmovieapp.ui.theme.DarkPurpleText
import com.example.kidsmovieapp.ui.theme.Gold
import com.example.kidsmovieapp.ui.theme.KidsMovieAppTheme
import com.example.kidsmovieapp.ui.theme.PinkAccent
import com.example.kidsmovieapp.ui.theme.PinkGradient
import com.example.kidsmovieapp.ui.theme.PurpleAccent
import com.example.kidsmovieapp.ui.theme.TealAccent
import com.example.kidsmovieapp.ui.theme.buttonGradient

data class Movie(
    val title: String,
    val posterResId: Int,
    val rating: String,
    val year: String,
    val genres: List<String>,
    val overview: String
)

val sampleMovie = Movie(
    title = "The Amazing Adventure",
    posterResId = R.drawable.placeholder,
    rating = "4.8",
    year = "2024",
    genres = listOf("Adventure"),
    overview = "Join our brave heroes on an incredible journey through magical lands filled with friendship, laughter, and amazing discoveries! This heartwarming adventure teaches kids about courage, teamwork, and believing in yourself."
)

class MovieDetailScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KidsMovieAppTheme {
                MovieDetailScreen(movie = sampleMovie)
            }
        }
    }
}

@Composable
fun MovieDetailScreen(movie: Movie) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        AnimatedBackground(modifier = Modifier.fillMaxSize())

        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(top = 26.dp, start = 20.dp, end = 20.dp, bottom = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { /* back */ },
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
                    .fillMaxSize()
                    .padding(horizontal = 20.dp)
            ) {
                Spacer(modifier = Modifier.height(20.dp))

                // Movie poster and info
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.Top
                ) {
                    Image(
                        painter = painterResource(id = movie.posterResId),
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
                            text = movie.title,
                            color = DarkPurpleText,
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            lineHeight = 28.sp
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            InfoTag(text = "G", backgroundColor = TealAccent, isCircle = true)
                            Spacer(modifier = Modifier.width(8.dp))
                            movie.genres.forEach { genre ->
                                InfoTag(text = genre, backgroundColor = PinkAccent)
                            }
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            IconInfoTag(
                                text = movie.rating,
                                backgroundColor = Gold,
                                icon = Icons.Default.Star,
                                isCircle = true
                            )

                            Spacer(modifier = Modifier.width(8.dp))
                            IconInfoTag(
                                text = movie.year,
                                backgroundColor = PurpleAccent,
                                icon = Icons.Default.DateRange,
                                isCircle = true
                            )
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
                        text = movie.overview,
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

                Button(
                    onClick = { /* Watch Trailer */ },
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

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = { /* Add to Favorites */ },
                    shape = RoundedCornerShape(50),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                    border = BorderStroke(2.dp, PinkAccent),
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Default.FavoriteBorder,
                            contentDescription = null,
                            tint = PinkAccent
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            "Add to Favorites 💖",
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
            fontSize = 16.sp,
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

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MovieDetailScreenPreview() {
    KidsMovieAppTheme {
        MovieDetailScreen(movie = sampleMovie)
    }
}
