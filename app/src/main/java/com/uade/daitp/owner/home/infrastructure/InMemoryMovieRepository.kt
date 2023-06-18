package com.uade.daitp.owner.home.infrastructure

import com.uade.daitp.owner.home.core.models.*
import com.uade.daitp.owner.home.core.models.enums.ScreeningFormat
import com.uade.daitp.owner.home.core.models.exceptions.MovieNotFoundException
import com.uade.daitp.owner.home.core.models.exceptions.ScreeningNotFoundException
import com.uade.daitp.owner.home.core.repository.MovieRepository
import java.util.*

class InMemoryMovieRepository : MovieRepository {
    private val moviesList = MoviesList(
        mutableListOf(
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
                "https://m.media-amazon.com/images/M/MV5BMDdmMTBiNTYtMDIzNi00NGVlLWIzMDYtZTk3MTQ3NGQxZGEwXkEyXkFqcGdeQXVyMzMwOTU5MDk@._V1_.jpg",
                Calendar.getInstance().time,
                Calendar.getInstance().time
            ),
        ),
        mutableListOf(
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
                "https://static.wikia.nocookie.net/spiderman/images/f/fc/Spider_Man_Far_From_Home_-_P%C3%B3ster_EEUU.png/revision/latest?cb=20190522155952&path-prefix=es",
                Calendar.getInstance().time,
                Calendar.getInstance().time
            ),
        ),
        Pagination(1, 100, 1, 1),
        Pagination(1, 100, 1, 1)
    )
    private val moviesByRoom = mutableMapOf<Int, MoviesList>()

    private val screenings = mutableListOf(
        Screening(
            0, 1, 0, ScreeningFormat.SUBTITLED,
            Calendar.getInstance().time,
            GregorianCalendar(2023, 7, 20, 5, 3).time,
            listOf(),
            Calendar.getInstance().time,
            Calendar.getInstance().time
        )
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

    override fun getMoviesByRoom(roomId: Int): MoviesList {
        val movies = moviesByRoom[roomId]
        movies?.let { return it } ?: throw MovieNotFoundException("No movies in room")
    }

    override fun addMovieToRoom(roomId: Int, movie: Movie) {
        if (moviesByRoom[roomId] == null) {
            moviesByRoom[roomId] = MoviesList(
                mutableListOf(),
                mutableListOf(),
                Pagination(1, 100, 1, 1),
                Pagination(1, 100, 1, 1)
            )
        }

        moviesByRoom[roomId]?.let { moviesList ->
            movie.score?.let {
                moviesList.showing.add(movie)
            } ?: {
                moviesList.comingSoon.add(movie)
            }
        }
    }

    override fun deleteMovieFromRoom(roomId: Int, movieId: Int) {
        val moviesList = moviesByRoom[roomId]
        moviesList?.comingSoon?.removeIf { it.id == movieId }
        moviesList?.showing?.removeIf { it.id == movieId }
    }

    override fun addScreening(intent: CreateScreeningIntent) {
        screenings.add(intent.toScreening(getScreeningId()))
    }

    override fun getScreenings(): List<Screening> {
        return screenings
    }

    override fun getScreeningsBy(movieId: Int, roomId: Int): List<Screening> {
        return screenings.filter { screening -> screening.movieId == movieId && screening.roomId == roomId }
    }

    override fun getScreening(screeningId: Int): Screening {
        val screening = screenings.find { screening -> screening.id == screeningId }
        screening?.let {
            return it
        } ?: throw ScreeningNotFoundException("Screening with id: $screeningId not found")
    }

    override fun deleteScreening(screeningId: Int) {
        val deleted = screenings.removeIf { screening -> screening.id == screeningId }

        if (!deleted) throw ScreeningNotFoundException("$screeningId does not exist")
    }

    private fun getScreeningId(): Int {
        return screenings.size
    }

}