package com.uade.daitp.client.core.actions

import com.uade.daitp.client.core.model.AvailableSeat
import com.uade.daitp.client.core.repository.ClientRepository

class GetAvailableSeats(private val clientRepository: ClientRepository) {

    suspend operator fun invoke(screeningId: Int): List<AvailableSeat> {
        return clientRepository.getAvailableSeats(screeningId)
    }
}
