package com.example.kidsmovieapp.data.remote.dto


data class MovieDto(
    val id: Int,
    val title: String,
    val overview: String?,
    val poster_path: String?,
    val release_date: String?
)