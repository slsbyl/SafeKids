package com.example.kidsmoviesapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KidsMoviesApp()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KidsMoviesApp() {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Kids Movies 🎬") }
            )
        }
    ) { padding ->
        Text(
            text = "Hello! Jetpack Compose is working 🎉",
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewApp() {
    KidsMoviesApp()
}
