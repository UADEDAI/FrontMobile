package com.uade.daitp.owner.home.core.actions

import com.uade.daitp.owner.home.core.models.Movie
import com.uade.daitp.owner.home.core.models.MoviesList
import com.uade.daitp.owner.home.core.models.Pagination
import com.uade.daitp.owner.home.core.repository.MovieRepository
import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.util.*

internal class GetMoviesShould {

    private lateinit var repository: MovieRepository
    private lateinit var getMovies: GetMovies
    private lateinit var moviesList: MoviesList

    @Test
    fun `get movies should call repository`() {
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
        Assertions.assertTrue { moviesList.comingSoon.isEmpty() && moviesList.showing.isEmpty() }
    }

    private fun givenAnActionWithEmptyRepository() {
        repository = mock()
        whenever(repository.getMovies()).thenReturn(mockEmptyMoviesList)
        getMovies = GetMovies(repository)
    }

    private fun givenAnAction() {
        repository = mock()
        whenever(repository.getMovies()).thenReturn(mockMoviesList)
        getMovies = GetMovies(repository)
    }

    private fun whenGettingMovies() {
        moviesList = getMovies()
    }

    private fun thenRepositoryIsCalled() {
        verify(repository).getMovies()
    }

    private companion object {
        val mockEmptyMoviesList = MoviesList(
            listOf(),
            listOf(),
            Pagination(1, 100, 1, 0),
            Pagination(1, 100, 1, 0)
        )
        val mockMoviesList = MoviesList(
            listOf(
                Movie(
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
                ),
            ),
            listOf(
                Movie(
                    1, "Spiderman: Far From Home",
                    "Following the events of Avengers: Endgame (2019), Spider-Man must step up to take on new threats in a world that has changed forever.",
                    120,
                    Calendar.getInstance().time,
                    "Action",
                    "Jon Watts",
                    listOf("Tom Holland, Samuel L. Jackson, Jake Gyllenhaal, Marisa Tomei, Jon Favreau, Zendaya, Jacob Batalon, Tony Revolori, Angourie Rice, Remy Hii, Martin Starr, J. B. Smoove, Cobie Smulders, Numan Acar, Jorge Lendeborg Jr., Hemky Madera, Toni Garrn"),
                    -1.0,
                    "PG-13",
                    "unsplash.com/example",
                    Calendar.getInstance().time,
                    Calendar.getInstance().time
                ),
            ),
            Pagination(1, 100, 1, 1),
            Pagination(1, 100, 1, 1)
        )
    }
}