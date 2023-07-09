package com.uade.daitp.client.core.repository

import com.uade.daitp.client.core.model.MoviesIntent
import com.uade.daitp.client.core.model.Reservation
import com.uade.daitp.owner.home.core.models.Cinema
import com.uade.daitp.owner.home.core.models.MoviesList

interface ClientRepository {
    suspend fun getReservations(): List<Reservation>
    suspend fun getFilteredMovies(moviesIntent: MoviesIntent): MoviesList
    suspend fun getNearCinemasForMovie(lat: Double, lng: Double, movieId: Int): List<Cinema>
}