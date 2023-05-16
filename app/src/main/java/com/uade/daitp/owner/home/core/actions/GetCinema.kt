package com.uade.daitp.owner.home.core.actions

import com.uade.daitp.owner.home.core.models.Cinema
import com.uade.daitp.owner.home.infrastructure.InMemoryCinemaRepository

class GetCinema(private val ownerRepository: InMemoryCinemaRepository) {

    operator fun invoke(cinemaId: Int): Cinema {
        return ownerRepository.getCinema(cinemaId = cinemaId)
    }
}
