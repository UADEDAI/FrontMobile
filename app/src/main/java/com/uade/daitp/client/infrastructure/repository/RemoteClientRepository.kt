package com.uade.daitp.client.infrastructure.repository

import com.uade.daitp.client.core.model.*
import com.uade.daitp.client.core.repository.ClientRepository
import com.uade.daitp.login.core.repository.ClientService
import com.uade.daitp.login.infrastructure.repository.PersistenceUserRepository
import com.uade.daitp.owner.home.core.models.Cinema
import com.uade.daitp.owner.home.core.models.MoviesList
import java.text.SimpleDateFormat
import java.util.*

class RemoteClientRepository(
    private val clientService: ClientService,
    private val userRepository: PersistenceUserRepository,
    private val reservationRepository: LocalReservationRepository
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

    override suspend fun createReservation(reservationIntent: ReservationIntent): Reservation {
        val user = userRepository.getUser()
        val intent = ReservationIntent(
            user.id,
            reservationIntent.screeningId,
            reservationIntent.seats,
            reservationIntent.time
        )
        return clientService.createReservation(intent)
    }

    override suspend fun getAvailableSeats(screeningId: Int): List<AvailableSeat> {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = reservationRepository.dateInMillis
        return clientService.getAvailableSeats(
            screeningId,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
    }

    override suspend fun getComments(movieId: Int): List<Comment> {
        return clientService.getComments(movieId)
    }

    override suspend fun createComment(commentIntent: CommentIntent) {
        val user = userRepository.getUser()
        commentIntent.userId = user.id
        clientService.createComment(commentIntent.movieId, commentIntent)
    }

    private val timeFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())

    override suspend fun getScreeningsBy(
        cinemaId: Int,
        movieId: Int,
        date: Date
    ): List<ScreeningClient> {
        val dateString: String = timeFormat.format(date)
        return clientService.getScreeningBy(cinemaId, movieId, dateString)
    }
}