package com.uade.daitp.owner.home.core.actions

import com.uade.daitp.owner.home.core.models.Cinema
import com.uade.daitp.owner.register.core.repository.OwnerRepository

class GetCinema(private val ownerRepository: OwnerRepository) {

    operator fun invoke(cinemaId: Int): Cinema {
        return ownerRepository.getCinema(cinemaId = cinemaId)
    }
}
