package com.uade.daitp.owner.home.core.actions

import com.uade.daitp.owner.home.core.models.CreateCinemaRoomIntent
import com.uade.daitp.owner.home.core.repository.CinemaRepository

class UpdateCinemaRoom(private val cinemaRepository: CinemaRepository) {

    suspend operator fun invoke(cinemaId: Int, cinemaIntent: CreateCinemaRoomIntent) {
        cinemaRepository.updateCinemaRoom(cinemaId, cinemaIntent)
    }
}
