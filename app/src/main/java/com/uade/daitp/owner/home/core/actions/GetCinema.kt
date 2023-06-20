package com.uade.daitp.owner.home.core.actions

import com.uade.daitp.owner.home.core.models.Cinema
import com.uade.daitp.owner.home.core.repository.CinemaRepository

class GetCinema(private val ownerRepository: CinemaRepository) {

    suspend operator fun invoke(cinemaId: Int): Cinema {
        return ownerRepository.getCinema(cinemaId = cinemaId)
    }
}
