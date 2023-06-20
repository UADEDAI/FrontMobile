package com.uade.daitp.owner.home.core.actions

import com.uade.daitp.owner.home.core.models.CreateCinemaIntent
import com.uade.daitp.owner.home.core.repository.CinemaRepository

class AddCinema(private val cinemaRepository: CinemaRepository) {

    suspend operator fun invoke(cinemaIntent: CreateCinemaIntent) {
        cinemaRepository.createCinema(cinemaIntent)
    }
}
