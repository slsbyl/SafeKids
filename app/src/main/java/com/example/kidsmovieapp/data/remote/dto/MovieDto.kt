package com.example.kidsmovieapp.data.remote.dto

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieDto(
    val id: Int,
    val title: String,
    val overview: String?,
    val poster_path: String?,
    val release_date: String?,
    @SerializedName("vote_average") val vote_average: Double?,
    val genre_ids: List<Int>?,
    val genres: List<GenreDto>?
) : Parcelable

@Parcelize
data class GenreDto(
    val id: Int,
    val name: String
) : Parcelable