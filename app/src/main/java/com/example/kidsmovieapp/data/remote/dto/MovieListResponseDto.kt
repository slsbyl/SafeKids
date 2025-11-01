package com.example.kidsmovieapp.data.remote.dto

data class MovieListResponseDto(
    val results: List<MovieDto>,
    val page: Int,
    val total_pages: Int,
    val total_results: Int
)