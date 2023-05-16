package com.uade.daitp.owner.home.core.actions

import com.uade.daitp.owner.home.core.models.CreateCinemaIntent
import com.uade.daitp.owner.home.core.repository.CinemaRepository

class AddCinemas(private val cinemaRepository: CinemaRepository) {

    operator fun invoke(cinemaIntent: CreateCinemaIntent) {
        cinemaRepository.createCinema(cinemaIntent)
    }
}
