package com.example.kidsmovieapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.kidsmovieapp.screens.SafeKidsHomeScreen
import com.example.kidsmovieapp.screens.SearchScreen
import com.example.kidsmovieapp.screens.SplashScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SearchScreen()
        }
    }
}