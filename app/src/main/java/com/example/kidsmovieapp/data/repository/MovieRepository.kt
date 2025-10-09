package com.example.kidsmovieapp.data.repository

import com.example.kidsmovieapp.data.remote.api.NetworkModule
import com.example.kidsmovieapp.data.remote.dto.MovieDto
import com.example.kidsmovieapp.data.remote.dto.MovieListResponseDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MovieRepository {

    private val api = NetworkModule.api

    //Fetch kids movies for home screen
    suspend fun getKidsMovies(page: Int = 1): List<MovieDto> = withContext(Dispatchers.IO) {
        api.getKidsMovies(page = page).results
    }

    //Fetch movie's names for search screen
    suspend fun searchMovies(query: String): List<MovieDto> = withContext(Dispatchers.IO) {
        api.searchMovies(query = query).results
    }

    //Fetch movie details for detail screen
    suspend fun getMovieDetails(movieId: Int): MovieDto = withContext(Dispatchers.IO) {
        api.getMovieDetails(movieId)
    }
}