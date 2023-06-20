package com.uade.daitp.owner.home.core.actions

import com.uade.daitp.owner.home.core.models.CinemaRoom
import com.uade.daitp.owner.home.core.repository.CinemaRepository

class GetCinemaRoom(private val ownerRepository: CinemaRepository) {

    suspend operator fun invoke(cinemaRoomId: Int): CinemaRoom {
        return ownerRepository.getCinemaRoom(cinemaRoomId = cinemaRoomId)
    }
}
