package com.example.kidsmovieapp.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.kidsmovieapp.data.remote.dto.MovieDto
import com.example.kidsmovieapp.screens.DetailScreen
import com.example.kidsmovieapp.screens.SafeKidsHomeScreen
import com.example.kidsmovieapp.screens.SearchScreen
import com.example.kidsmovieapp.screens.SplashScreen
import com.example.kidsmovieapp.ui.viewmodel.MovieViewModel

@Composable
fun NavigationGraph(navController: NavHostController) {
    val viewModel: MovieViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = "splash"
    ) {
        composable("splash") {
            SplashScreen(
                onFinish = {
                    navController.navigate("home") {
                        popUpTo("splash") { inclusive = true }
                    }
                }
            )
        }

        composable("home") {
            SafeKidsHomeScreen(
                viewModel = viewModel,
                onMovieClick = { movie ->
                    navController.navigate("detail/${movie.id}")
                }
            )
        }

        composable("search") {
            SearchScreen(
                viewModel = viewModel,
                onClose = { navController.popBackStack() }
            )
        }

        composable("detail/{movieId}") { backStackEntry ->
            val movieId = backStackEntry.arguments?.getString("movieId")?.toIntOrNull()
            if (movieId != null) {
                DetailScreen(
                    movieId = movieId,
                    viewModel = viewModel,
                    onBackClicked = { navController.popBackStack() }
                )
            }
        }
    }
}














