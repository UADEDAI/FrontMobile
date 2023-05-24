package com.uade.daitp.owner.home.core.actions

import com.uade.daitp.owner.home.core.models.Movie
import com.uade.daitp.owner.home.core.models.exceptions.InvalidMovieNotFoundException
import com.uade.daitp.owner.home.core.repository.MovieRepository
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertTrue
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.util.*

internal class GetMovieShould {
    private lateinit var repository: MovieRepository
    private lateinit var getMovie: GetMovie
    private lateinit var movie: Movie
    private lateinit var error: Exception

    @Test
    fun `get movie should call repository`() {
        givenAnAction()

        whenGettingMovies()

        thenRepositoryIsCalled()
    }

    @Test
    fun `get empty list if there are no movies`() {
        givenAnActionWithEmptyRepository()

        whenGettingMovies()

        thenGetsEmptyList()
    }

    private fun thenGetsEmptyList() {
        assertTrue { error is InvalidMovieNotFoundException }
    }

    private fun givenAnActionWithEmptyRepository() {
        repository = mock()
        whenever(repository.getMovie(movieId)).thenThrow(InvalidMovieNotFoundException("invalid movie id"))
        getMovie = GetMovie(repository)
    }

    private fun givenAnAction() {
        repository = mock()
        whenever(repository.getMovie(movieId)).thenReturn(mockMovie)
        getMovie = GetMovie(repository)
    }

    private fun whenGettingMovies() {
        try {
            movie = getMovie(movieId)
        } catch (e: Exception) {
            error = e
        }
    }

    private fun thenRepositoryIsCalled() {
        verify(repository).getMovie(movieId)
    }

    private companion object {
        const val movieId = 0
        val mockMovie = Movie(
            0, "The Batman",
            "The film sees Batman , who has been fighting crime in Gotham City for two years, uncover corruption while pursuing the Riddler (Dano), a serial killer who targets Gotham's corrupt elite.",
            120,
            Calendar.getInstance().time,
            "Action",
            "Matt Reeves",
            listOf("Robert Pattinson, Zoë Kravitz, Paul Dano, Jeffrey Wright, John Turturro, Peter Sarsgaard, Barry Keoghan, Jayme Lawson, Andy Serkis, Colin Farrell"),
            8.0,
            "PG-13",
            "unsplash.com/example",
            Calendar.getInstance().time,
            Calendar.getInstance().time
        )
    }
}