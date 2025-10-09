package com.example.kidsmovieapp.data.remote.api

import com.example.kidsmovieapp.data.remote.dto.MovieDto
import com.example.kidsmovieapp.data.remote.dto.MovieListResponseDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import com.example.kidsmovieapp.BuildConfig

interface TmdbApi {
    // Kids movies (G-rated)
    @GET("discover/movie")
    suspend fun getKidsMovies(
        @Query("api_key") apiKey: String = BuildConfig.TMDB_API_KEY,
        @Query("certification_country") certificationCountry: String = "US",
        @Query("certification.lte") certification: String = "G",
        @Query("with_genres") genreId: String? = "16,10751",
        @Query("include_adult") includeAdult: Boolean = false,
        @Query("sort_by") sortBy: String = "popularity.desc",
        @Query("page") page: Int = 1
    ): MovieListResponseDto

    // Search by name
    @GET("search/movie")
    suspend fun searchMovies(
        @Query("api_key") apiKey: String = BuildConfig.TMDB_API_KEY,
        @Query("query") query: String
    ): MovieListResponseDto

    // Get movie details
    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = BuildConfig.TMDB_API_KEY
    ): MovieDto
}
