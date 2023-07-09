package com.uade.daitp.client.infrastructure.repository

import com.uade.daitp.client.core.model.*
import com.uade.daitp.client.core.repository.ClientRepository
import com.uade.daitp.login.core.repository.ClientService
import com.uade.daitp.login.infrastructure.repository.SharedPrefPersistenceUserRepository
import com.uade.daitp.owner.home.core.models.Cinema
import com.uade.daitp.owner.home.core.models.MoviesList
import com.uade.daitp.owner.home.core.models.emptyMovieList
import retrofit2.http.Query

class RemoteClientRepository(
    private val clientService: ClientService,
    private val sharedPrefPersistenceUserRepository: SharedPrefPersistenceUserRepository
) : ClientRepository {

    override suspend fun getReservations(): List<Reservation> {
        return clientService.getReservations()
    }

    override suspend fun getFilteredMovies(moviesIntent: MoviesIntent): MoviesList {
        return clientService.getFilteredMovies(
            moviesIntent.lat,
            moviesIntent.lng,
            moviesIntent.distance,
            moviesIntent.title,
            moviesIntent.genre,
            moviesIntent.score
        )
    }

    override suspend fun getNearCinemasForMovie(
        lat: Double,
        lng: Double,
        distance: Double,
        movieId: Int
    ): List<Cinema> {
        return clientService.getNearestCinemas(lat, lng, distance, movieId)
    }

    override suspend fun getReservation(reservationId: Int): Reservation {
        return clientService.getReservationById(reservationId)
    }

    override suspend fun createReservation(reservationIntent: ReservationIntent) {
        clientService.createReservation(reservationIntent)
    }

    override suspend fun getAvailableSeats(screeningId: Int): List<AvailableSeat> {
        return clientService.getAvailableSeats(screeningId)
    }

    override suspend fun getComments(movieId: Int): List<Comment> {
        return clientService.getComments(movieId)
    }

    override suspend fun createComment(commentIntent: CommentIntent) {
        clientService.createComment(commentIntent.movieId, commentIntent)
    }
}