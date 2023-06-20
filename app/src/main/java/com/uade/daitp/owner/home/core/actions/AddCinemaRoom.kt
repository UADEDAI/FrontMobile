package com.uade.daitp.owner.home.core.actions

import com.uade.daitp.owner.home.core.models.CreateCinemaRoomIntent
import com.uade.daitp.owner.home.core.repository.CinemaRepository

class AddCinemaRoom(private val cinemaRepository: CinemaRepository) {

    suspend operator fun invoke(cinemaRoomIntent: CreateCinemaRoomIntent) {
        cinemaRepository.createCinemaRoom(cinemaRoomIntent)
    }
}
