package com.uade.daitp.owner.home.infrastructure

import com.uade.daitp.owner.home.core.models.Movie
import com.uade.daitp.owner.home.core.models.MoviesList
import com.uade.daitp.owner.home.core.models.Pagination
import com.uade.daitp.owner.home.core.repository.MovieRepository
import java.util.*

class InMemoryMovieRepository : MovieRepository {
    private val moviesList = MoviesList(
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

    override fun getMovies(): MoviesList {
        return moviesList
    }

    override fun getMovie(movieId: Int): Movie {
        val list = mutableListOf<Movie>()
        list.addAll(moviesList.showing)
        list.addAll(moviesList.comingSoon)
        return list.first { movie: Movie -> movie.id == movieId }
    }

}