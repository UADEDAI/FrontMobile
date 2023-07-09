package com.uade.daitp.client.core.repository

import com.uade.daitp.client.core.model.*
import com.uade.daitp.owner.home.core.models.Cinema
import com.uade.daitp.owner.home.core.models.MoviesList
import java.util.*

interface ClientRepository {
    suspend fun getReservations(): List<Reservation>
    suspend fun getFilteredMovies(moviesIntent: MoviesIntent): MoviesList
    suspend fun getNearCinemasForMovie(lat: Double, lng: Double, distance: Double, movieId: Int): List<Cinema>
    suspend fun getReservation(reservationId: Int): Reservation
    suspend fun createReservation(reservationIntent: ReservationIntent): Reservation
    suspend fun getAvailableSeats(screeningId: Int): List<AvailableSeat>
    suspend fun getComments(movieId: Int): List<Comment>
    suspend fun createComment(commentIntent: CommentIntent)
    suspend fun getScreeningsBy(cinemaId: Int, movieId: Int, date: Date): List<ScreeningClient>
}