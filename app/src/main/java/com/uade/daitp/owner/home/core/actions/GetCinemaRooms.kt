package com.uade.daitp.owner.home.core.actions

import com.uade.daitp.owner.home.core.models.CinemaRoom
import com.uade.daitp.owner.home.core.repository.CinemaRepository

class GetCinemaRooms(private val cinemaRepository: CinemaRepository) {

    operator fun invoke(cinemaId: Int): List<CinemaRoom> {
        return cinemaRepository.getCinemaRooms(cinemaId)
    }
}
