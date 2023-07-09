package com.uade.daitp.client.core.actions

import com.uade.daitp.client.core.repository.ClientRepository
import com.uade.daitp.owner.home.core.models.Cinema

class GetNearCinemaForMovie(private val clientRepository: ClientRepository) {

    suspend operator fun invoke(lat: Double, lng: Double, movieId: Int): List<Cinema> {
        return clientRepository.getNearCinemasForMovie(lat, lng, movieId)
    }
}