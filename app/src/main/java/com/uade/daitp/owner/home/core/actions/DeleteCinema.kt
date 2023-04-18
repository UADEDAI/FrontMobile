package com.uade.daitp.owner.home.core.actions

import com.uade.daitp.owner.register.core.repository.OwnerRepository

class DeleteCinema (private val ownerRepository: OwnerRepository) {

    operator fun invoke(cinemaId: Int) {
        ownerRepository.deleteCinema(cinemaId)
    }
}