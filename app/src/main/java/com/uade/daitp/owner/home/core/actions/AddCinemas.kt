package com.uade.daitp.owner.home.core.actions

import com.uade.daitp.owner.home.core.models.CreateCinemaIntent
import com.uade.daitp.owner.register.core.repository.OwnerRepository

class AddCinemas(private val ownerRepository: OwnerRepository) {

    operator fun invoke(cinemaIntent: CreateCinemaIntent) {
        ownerRepository.createCinema(cinemaIntent)
    }
}
