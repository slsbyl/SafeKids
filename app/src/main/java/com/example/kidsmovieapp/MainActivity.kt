package com.example.kidsmovieapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.kidsmovieapp.navigation.NavigationGraph
import com.example.kidsmovieapp.screens.HomeScreen
import com.example.kidsmovieapp.screens.SafeKidsHomeScreen
import com.example.kidsmovieapp.screens.SearchScreen
import com.example.kidsmovieapp.screens.SplashScreen
import com.example.kidsmovieapp.ui.theme.KidsMovieAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            KidsMovieAppTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    val navController = rememberNavController()
                    NavigationGraph(navController=navController)
                }
                }
        }
    }
}