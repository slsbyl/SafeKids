package com.example.kidsmovieapp.screens

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.kidsmovieapp.data.remote.dto.MovieDto
import com.example.kidsmovieapp.data.repository.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.example.kidsmovieapp.data.local.AppDatabase
import com.example.kidsmovieapp.data.local.dao.MovieDao
import com.example.kidsmovieapp.data.local.entity.FavoriteMovie


class MovieViewModel(
    application: Application,
    private val repository: MovieRepository = MovieRepository()
) : AndroidViewModel(application) {


    private val movieDao: MovieDao = AppDatabase.getDatabase(application).movieDao()


    private val _kidsMovies = MutableStateFlow<List<MovieDto>>(emptyList())
    val kidsMovies: StateFlow<List<MovieDto>> = _kidsMovies

    private val _searchResults = MutableStateFlow<List<MovieDto>>(emptyList())
    val searchResults: StateFlow<List<MovieDto>> = _searchResults

    private val _selectedMovie = MutableStateFlow<MovieDto?>(null)
    val selectedMovie: StateFlow<MovieDto?> = _selectedMovie

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private var currentPage = 1
    private var hasMorePages = true

    private val _favoriteMovies = MutableStateFlow<List<MovieDto>>(emptyList())
    val favoriteMovies: StateFlow<List<MovieDto>> = _favoriteMovies


    private val _isFavorite = MutableStateFlow(false)
    val isFavorite: StateFlow<Boolean> = _isFavorite


    init {

        getAllFavoriteMovies()
    }


    fun loadKidsMovies() {

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

    fun checkIsFavorite(movieId: Int) {
        viewModelScope.launch {
            _isFavorite.value = movieDao.isFavorite(movieId)
        }
    }

    fun toggleFavorite(movie: MovieDto) {
        viewModelScope.launch {
            val favMovie = FavoriteMovie(
                id = movie.id ?: return@launch,
                title = movie.title,
                poster_path = movie.poster_path,
                vote_average = movie.vote_average
            )
            if (movieDao.isFavorite(favMovie.id)) {
                movieDao.deleteMovie(favMovie)
                _isFavorite.value = false
            } else {
                movieDao.insertMovie(favMovie)
                _isFavorite.value = true
            }
        }
    }
    private fun getAllFavoriteMovies() {
        viewModelScope.launch {
            movieDao.getAllFavorites().collect { favorites ->
                _favoriteMovies.value = favorites.map { fav ->
                    MovieDto(
                        id = fav.id,
                        title = fav.title ?: "Untitled",
                        poster_path = fav.poster_path,
                        vote_average = fav.vote_average,
                        overview = null,
                        release_date = null,
                        genre_ids = null,
                        genres = null,
                        adult = false,
                        trailerUrl = null
                    )
                }
            }
        }
    }
}