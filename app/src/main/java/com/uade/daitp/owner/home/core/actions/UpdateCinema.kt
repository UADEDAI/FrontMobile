package com.uade.daitp.owner.home.core.actions

import com.uade.daitp.owner.home.core.models.CreateCinemaIntent
import com.uade.daitp.owner.home.core.repository.CinemaRepository

class UpdateCinema(private val cinemaRepository: CinemaRepository) {

    suspend operator fun invoke(cinemaId: Int, cinemaIntent: CreateCinemaIntent) {
        cinemaRepository.updateCinema(cinemaId, cinemaIntent)
    }
}
