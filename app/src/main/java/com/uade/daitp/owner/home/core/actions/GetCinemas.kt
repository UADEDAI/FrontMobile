package com.uade.daitp.owner.home.core.actions

import com.uade.daitp.owner.home.core.models.Cinema
import com.uade.daitp.owner.register.core.repository.OwnerRepository

class GetCinemas(private val ownerRepository: OwnerRepository) {

    operator fun invoke(): List<Cinema> {
        return ownerRepository.getCinemas()
    }
}
