package com.example.kidsmovieapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.kidsmovieapp.screens.DetailScreen
import com.example.kidsmovieapp.screens.SafeKidsHomeScreen
import com.example.kidsmovieapp.ui.theme.KidsMovieAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KidsMovieAppTheme {
                // This state will hold the ID of the movie to show in the detail screen.
                // If it's null, we show the home screen.
                var selectedMovieId by remember { mutableStateOf<Int?>(null) }

                if (selectedMovieId == null) {
                    // --- Show Home Screen ---
                    SafeKidsHomeScreen(
                        onMovieClick = { movie ->
                            // When a movie is clicked, update the state with its ID
                            selectedMovieId = movie.id
                        }
                    )
                } else {
                    // --- Show Detail Screen ---
                    DetailScreen(
                        movieId = selectedMovieId!!,
                        onBackClicked = {
                            selectedMovieId = null
                        }
                    )
                }
            }
        }
    }
}
