package com.uade.daitp.owner.home.infrastructure

import com.uade.daitp.login.infrastructure.repository.UserRepository
import com.uade.daitp.owner.home.core.models.*
import com.uade.daitp.owner.home.core.models.enums.ScreeningFormat
import com.uade.daitp.owner.home.core.models.exceptions.MovieNotFoundException
import com.uade.daitp.owner.home.core.models.exceptions.ScreeningNotFoundException
import com.uade.daitp.owner.home.core.repository.MovieRepository
import com.uade.daitp.owner.home.core.repository.service.MovieService
import java.util.*

class RemoteMovieRepository(
    private val movieService: MovieService
) : MovieRepository {
    override suspend fun getMovies(): MoviesList {
        return movieService.getMovies()
    }

    override suspend fun getMovie(movieId: Int): Movie {
        try {
            return movieService.getMovie(movieId)
        } catch (e: Exception) {
            throw MovieNotFoundException("No movie with id: $movieId")
        }
    }

    override suspend fun getMoviesByRoom(roomId: Int): MoviesList {
        try {
            return movieService.getMoviesByRoom(roomId)
        } catch (e: Exception) {
            throw MovieNotFoundException("No movies in room")
        }
    }

    override suspend fun addMovieToRoom(roomId: Int, movie: Movie) {
        movieService.addMovieToRoom(roomId, AddMovieInRoomIntent(movieId = movie.id))
    }

    override suspend fun deleteMovieFromRoom(roomId: Int, movieId: Int) {
        movieService.deleteMovieFromRoom(roomId, movieId)
    }

    override suspend fun addScreening(intent: CreateScreeningIntent) {
        movieService.addScreening(intent)
    }

    override suspend fun getScreenings(): List<Screening> {
        return movieService.getScreenings()
    }

    override suspend fun getScreeningsBy(movieId: Int, roomId: Int): List<Screening> {
        try {
            return movieService.getScreeningsBy(roomId)
                .map { time ->
                    Screening(
                        -1,
                        roomId,
                        movieId,
                        ScreeningFormat.ORIGINAL,
                        getScreeningDate(time),
                        getScreeningDate(time)
                    )
                }
        } catch (e: Exception) {
            throw ScreeningNotFoundException("Screenings for movie: $movieId in room: $roomId not found")
        }
    }

    private fun getScreeningDate(time: String): Date {
        val hourAndMinute = time.split(":")
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hourAndMinute[0].toInt())
        calendar.set(Calendar.MINUTE, hourAndMinute[1].toInt())
        return calendar.time
    }

    override suspend fun getScreening(screeningId: Int): Screening {
        try {
            return movieService.getScreening(screeningId)
        } catch (e: Exception) {
            throw ScreeningNotFoundException("Screening with id: $screeningId not found")
        }
    }

    override suspend fun deleteScreening(screeningId: Int) {
        movieService.deleteScreening(screeningId)
    }
}