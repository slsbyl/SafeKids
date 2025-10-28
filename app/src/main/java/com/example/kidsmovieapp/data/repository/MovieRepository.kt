package com.example.kidsmovieapp.data.repository

import com.example.kidsmovieapp.data.remote.api.NetworkModule
import com.example.kidsmovieapp.data.remote.dto.MovieDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MovieRepository {

    private val api = NetworkModule.api

    suspend fun getKidsMovies(page: Int = 1): List<MovieDto> = withContext(Dispatchers.IO) {
        api.getKidsMovies(
            certificationCountry = "US",
            certification = "PG",
            genreId = "16",
            includeAdult = false,
            sortBy = "popularity.desc",
            language = "en-US",
            voteAverage = 6.0,
            page = page
        ).results
    }


    suspend fun searchMovies(query: String): List<MovieDto> = withContext(Dispatchers.IO) {
        api.searchMovies(query = query).results
    }


    suspend fun getMovieDetails(movieId: Int): MovieDto = withContext(Dispatchers.IO) {
        val movie = api.getMovieDetails(movieId)


        val videos = api.getMovieVideos(movieId).results
        val trailer = videos.firstOrNull {
            it.site.equals("YouTube", ignoreCase = true) && it.type.equals("Trailer", ignoreCase = true)
        }


        movie.copy(
            trailerUrl = trailer?.let { "https://www.youtube.com/watch?v=${it.key}" }
        )
    }
}

