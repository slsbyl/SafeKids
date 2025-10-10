package com.example.kidsmovieapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kidsmovieapp.data.remote.dto.MovieDto
import com.example.kidsmovieapp.data.repository.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MovieViewModel : ViewModel() {

    private val repository = MovieRepository()

    private val _kidsMovies = MutableStateFlow<List<MovieDto>>(emptyList())
    val kidsMovies: StateFlow<List<MovieDto>> = _kidsMovies

    private val _searchResults = MutableStateFlow<List<MovieDto>>(emptyList())
    val searchResults: StateFlow<List<MovieDto>> = _searchResults

    private val _selectedMovie = MutableStateFlow<MovieDto?>(null)
    val selectedMovie: StateFlow<MovieDto?> = _selectedMovie

    // Fetch kids movies
    fun loadKidsMovies() {
        viewModelScope.launch {
            try {
                _kidsMovies.value = repository.getKidsMovies()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // Search by name
    fun searchMovies(query: String) {
        viewModelScope.launch {
            try {
                _searchResults.value = repository.searchMovies(query)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // Get movie details
    fun getMovieDetails(movieId: Int) {
        viewModelScope.launch {
            try {
                _selectedMovie.value = repository.getMovieDetails(movieId)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


    fun selectMovie(movie: MovieDto) {
        _selectedMovie.value = movie
    }


}

