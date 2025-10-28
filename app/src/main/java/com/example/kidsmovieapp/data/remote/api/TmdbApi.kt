package com.example.kidsmovieapp.data.remote.api

import com.example.kidsmovieapp.data.remote.dto.MovieDto
import com.example.kidsmovieapp.data.remote.dto.MovieListResponseDto
import com.example.kidsmovieapp.data.remote.dto.VideoResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbApi {

    @GET("discover/movie")
    suspend fun getKidsMovies(
        @Query("certification_country") certificationCountry: String = "US",
        @Query("certification.lte") certification: String = "PG",
        @Query("with_genres") genreId: String? = "16",
        @Query("include_adult") includeAdult: Boolean = false,
        @Query("sort_by") sortBy: String = "popularity.desc",
        @Query("language") language: String = "en-US",
        @Query("vote_average.gte") voteAverage: Double = 6.0,
        @Query("page") page: Int = 1
    ): MovieListResponseDto


    @GET("search/movie")
    suspend fun searchMovies(
        @Query("query") query: String
    ): MovieListResponseDto

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int
    ): MovieDto

    @GET("movie/{movie_id}/videos")
    suspend fun getMovieVideos(
        @Path("movie_id") movieId: Int
    ): VideoResponse

}
