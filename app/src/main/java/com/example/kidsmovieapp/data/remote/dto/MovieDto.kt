package com.example.kidsmovieapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class MovieDto(
    val id: Int,
    val title: String,
    val overview: String?,
    val poster_path: String?,
    val release_date: String?,
    @SerializedName("vote_average") val vote_average: Double?
)
