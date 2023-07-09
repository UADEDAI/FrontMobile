package com.uade.daitp.client.core.actions

import com.uade.daitp.client.core.model.Reservation
import com.uade.daitp.client.core.repository.ClientRepository

class GetReservationById (private val clientRepository: ClientRepository) {

    suspend operator fun invoke(reservationId: Int): Reservation {
        return clientRepository.getReservation(reservationId)
    }

}