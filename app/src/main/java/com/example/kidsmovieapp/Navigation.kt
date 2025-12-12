package com.example.kidsmovieapp

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.kidsmovieapp.data.repository.MovieRepository
import com.example.kidsmovieapp.screens.DetailScreen
import com.example.kidsmovieapp.screens.FavoritesScreen
import com.example.kidsmovieapp.screens.MovieViewModelFactory
import com.example.kidsmovieapp.screens.SafeKidsHomeScreen
import com.example.kidsmovieapp.screens.SearchScreen
import com.example.kidsmovieapp.screens.SplashScreen
import com.example.kidsmovieapp.screens.MovieViewModel



@Composable
fun NavigationGraph(navController: NavHostController) {
    val context = LocalContext.current
    val application = remember { context.applicationContext as Application }
    val movieRepository = remember { MovieRepository() }
    val factory = remember {
        MovieViewModelFactory(
            application = application,
            repository = movieRepository
        )
    }
    val viewModel: MovieViewModel = viewModel(factory = factory)
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
                },
                onSearchClick ={
                    navController.navigate("search")
                },
                onFavoritesClick = {
                    navController.navigate("favorites")
                }
            )
        }

        composable("search") {
            SearchScreen(
                viewModel = viewModel,
                onClose = { navController.popBackStack() },
                onMovieClick = { movie ->
                    navController.navigate("detail/${movie.id}")
                }
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

        composable("favorites") {
            FavoritesScreen(
                viewModel = viewModel,
                onMovieClick = { movie ->
                    navController.navigate("detail/${movie.id}")
                },
                onBackClicked = { navController.popBackStack() }
            )
        }
    }
}











