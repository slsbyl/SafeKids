package com.example.kidsmoviesapp

import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {
    @GET("discover/movie")
    suspend fun getKidsMovies(
        @Query("api_key") apiKey: String,
        @Query("with_genres") genres: String = "16,10751" // Animation + Family
    ): MovieResponse
}

data class MovieResponse(
    val results: List<Movie>
)
