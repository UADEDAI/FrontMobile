package com.uade.daitp.client.infrastructure.repository

import com.uade.daitp.client.core.model.MoviesIntent
import com.uade.daitp.client.core.model.Reservation
import com.uade.daitp.client.core.repository.ClientRepository
import com.uade.daitp.login.core.repository.ClientService
import com.uade.daitp.login.infrastructure.repository.SharedPrefPersistenceUserRepository
import com.uade.daitp.owner.home.core.models.Cinema
import com.uade.daitp.owner.home.core.models.MoviesList
import com.uade.daitp.owner.home.core.models.emptyMovieList

class RemoteClientRepository(
    private val clientService: ClientService,
    private val sharedPrefPersistenceUserRepository: SharedPrefPersistenceUserRepository
) : ClientRepository {

    //TODO everything
    override suspend fun getReservations(): List<Reservation> {
        return emptyList()
    }

    override suspend fun getFilteredMovies(moviesIntent: MoviesIntent): MoviesList {
        return emptyMovieList()
    }

    override suspend fun getNearCinemasForMovie(
        lat: Double,
        lng: Double,
        movieId: Int
    ): List<Cinema> {
        return emptyList()
    }
}