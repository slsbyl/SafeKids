package com.example.movieapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
@OptIn(ExperimentalCoroutinesApi::class)
class ExampleUnitTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()
    class FakeMovieRepository {
        private val movies = mutableListOf<String>()

        fun addMovie(name: String) {
            movies.add(name)
        }

        fun getKidsMovies() = movies.filter { it.contains("Kids") }

        fun searchMovies(query: String) = movies.filter { it.contains(query, ignoreCase = true) }
    }

    class FakeMovieViewModel(private val repository: FakeMovieRepository) {
        fun loadKidsMovies() = repository.getKidsMovies()
        fun searchMovies(query: String) = repository.searchMovies(query)
    }

    @Test
    fun loadKidsMovies_updatesKidsMoviesList() = runTest {
        val repository = FakeMovieRepository()
        repository.addMovie("Kids Movie 1")
        repository.addMovie("Adult Movie 1")
        repository.addMovie("Kids Movie 2")

        val viewModel = FakeMovieViewModel(repository)
        val kidsMovies = viewModel.loadKidsMovies()

        assertEquals(listOf("Kids Movie 1", "Kids Movie 2"), kidsMovies)
    }

    @Test
    fun searchMovies_updatesSearchResults() = runTest {
        val repository = FakeMovieRepository()
        repository.addMovie("Kids Movie 1")
        repository.addMovie("Adult Movie 1")
        repository.addMovie("Action Movie")

        val viewModel = FakeMovieViewModel(repository)
        val searchResults = viewModel.searchMovies("Adult")

        assertEquals(listOf("Adult Movie 1"), searchResults)
    }
}
