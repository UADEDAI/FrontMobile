package com.uade.daitp.owner.home.core.actions

import com.uade.daitp.owner.home.core.repository.CinemaRepository

class DeleteCinemaRoom(private val cinemaRepository: CinemaRepository) {

    operator fun invoke(id: Int) {
        cinemaRepository.deleteCinemaRoom(id)
    }
}