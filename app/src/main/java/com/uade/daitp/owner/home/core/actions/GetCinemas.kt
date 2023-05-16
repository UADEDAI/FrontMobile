package com.uade.daitp.owner.home.core.actions

import com.uade.daitp.owner.home.core.models.Cinema
import com.uade.daitp.owner.home.core.repository.CinemaRepository

class GetCinemas(private val ownerRepository: CinemaRepository) {

    operator fun invoke(): List<Cinema> {
        return ownerRepository.getCinemas()
    }
}
