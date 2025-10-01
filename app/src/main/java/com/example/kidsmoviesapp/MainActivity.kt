package com.example.kidsmoviesapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KidsMoviesApp()
        }
    }
}

@Composable
fun KidsMoviesApp() {
    var movies by remember { mutableStateOf(listOf<Movie>()) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        scope.launch {
            val response = RetrofitInstance.api.getKidsMovies(TmdbApi.API_KEY)
            movies = response.results
        }
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.padding(8.dp),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(movies) { movie ->
            MovieCard(movie)
        }
    }
}

@Composable
fun MovieCard(movie: Movie) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
    ) {
        Column {
            val imageUrl = "https://image.tmdb.org/t/p/w500${movie.poster_path}"
            Image(
                painter = rememberAsyncImagePainter(imageUrl),
                contentDescription = movie.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp),
                contentScale = ContentScale.Crop
            )
            Text(
                text = movie.title,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}
