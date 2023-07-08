package com.uade.daitp.client.core.actions

import com.uade.daitp.client.core.model.Reservation
import com.uade.daitp.client.core.repository.ClientRepository

class GetReservations (private val clientRepository: ClientRepository) {

    suspend operator fun invoke(): List<Reservation> {
        return clientRepository.getReservations()
    }

}