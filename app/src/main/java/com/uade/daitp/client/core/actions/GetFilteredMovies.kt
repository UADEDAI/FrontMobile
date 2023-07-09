package com.uade.daitp.client.core.actions

import com.uade.daitp.client.core.model.MoviesIntent
import com.uade.daitp.client.core.repository.ClientRepository
import com.uade.daitp.owner.home.core.models.MoviesList

class GetFilteredMovies(private val clientRepository: ClientRepository) {

    suspend operator fun invoke(moviesIntent: MoviesIntent): MoviesList {
        return clientRepository.getFilteredMovies(moviesIntent)
    }
}