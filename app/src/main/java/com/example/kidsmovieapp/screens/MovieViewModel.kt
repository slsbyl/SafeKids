package com.example.kidsmovieapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kidsmovieapp.data.remote.dto.MovieDto
import com.example.kidsmovieapp.data.repository.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MovieViewModel(
    private val repository: MovieRepository = MovieRepository()
) : ViewModel() {

    private val _kidsMovies = MutableStateFlow<List<MovieDto>>(emptyList())
    val kidsMovies: StateFlow<List<MovieDto>> = _kidsMovies

    private val _searchResults = MutableStateFlow<List<MovieDto>>(emptyList())
    val searchResults: StateFlow<List<MovieDto>> = _searchResults

    private val _selectedMovie = MutableStateFlow<MovieDto?>(null)
    val selectedMovie: StateFlow<MovieDto?> = _selectedMovie

    // NEW: Expose loading state for progress indicator
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private var currentPage = 1
    private var hasMorePages = true

    fun loadKidsMovies() {
        // Avoid double loading
        if (_isLoading.value || !hasMorePages) return

        _isLoading.value = true
        viewModelScope.launch {
            try {
                val newMovies = repository.getKidsMovies(page = currentPage)
                if (newMovies.isEmpty()) {
                    hasMorePages = false
                } else {
                    _kidsMovies.value = _kidsMovies.value + newMovies
                    currentPage++
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun loadMoreKidsMovies() {
        loadKidsMovies()
    }

    fun refreshKidsMovies() {
        currentPage = 1
        hasMorePages = true
        _kidsMovies.value = emptyList()
        loadKidsMovies()
    }

    fun searchMovies(query: String) {
        viewModelScope.launch {
            try {
                _searchResults.value = repository.searchMovies(query)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

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