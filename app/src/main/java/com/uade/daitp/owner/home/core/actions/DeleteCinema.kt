package com.uade.daitp.owner.home.core.actions

import com.uade.daitp.owner.home.core.repository.CinemaRepository

class DeleteCinema(private val cinemaRepository: CinemaRepository) {

    suspend operator fun invoke(cinemaId: Int) {
        cinemaRepository.deleteCinema(cinemaId)
    }
}