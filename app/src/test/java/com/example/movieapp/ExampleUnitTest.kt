package com.example.kidsmovieapp

import com.example.kidsmovieapp.data.remote.dto.MovieDto
import com.example.kidsmovieapp.data.repository.MovieRepository
import com.example.kidsmovieapp.ui.viewmodel.MovieViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*

import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ExampleUnitTest {

    private val mockRepo = mockk<MovieRepository>()
    private lateinit var viewModel: MovieViewModel

    // Create a test dispatcher to replace Dispatchers.Main
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = MovieViewModel(mockRepo)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `loadKidsMovies updates kidsMovies list`() = runTest {
        val fakeMovies = listOf(
            MovieDto(
                id = 1,
                title = "Frozen",
                overview = "Disney animation for kids",
                poster_path = "/frozen.jpg",
                release_date = "2013-11-27",
                vote_average = 8.5,
                genre_ids = listOf(16, 10751),
                genres = null,
                trailerUrl = "https://youtube.com/frozen"
            )
        )

        coEvery { mockRepo.getKidsMovies() } returns fakeMovies

        viewModel.loadKidsMovies()
        advanceUntilIdle() // waits for coroutine

        val result = viewModel.kidsMovies.value
        assertEquals(1, result.size)
        assertEquals("Frozen", result.first().title)
    }

    @Test
    fun `searchMovies updates searchResults`() = runTest {
        val fakeResults = listOf(
            MovieDto(
                id = 2,
                title = "Toy Story",
                overview = "Pixar classic",
                poster_path = "/toy.jpg",
                release_date = "1995-11-22",
                vote_average = 8.2,
                genre_ids = listOf(16, 10751),
                genres = null,
                trailerUrl = "https://youtube.com/toystory"
            )
        )

        coEvery { mockRepo.searchMovies("Toy") } returns fakeResults

        viewModel.searchMovies("Toy")
        advanceUntilIdle()

        val result = viewModel.searchResults.value
        assertEquals(1, result.size)
        assertEquals("Toy Story", result.first().title)
    }
}



