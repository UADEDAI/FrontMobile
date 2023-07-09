package com.uade.daitp.client.core.actions

import com.uade.daitp.client.core.model.Reservation
import com.uade.daitp.client.core.model.ReservationIntent
import com.uade.daitp.client.core.repository.ClientRepository

class CreateReservation(private val clientRepository: ClientRepository) {

    suspend operator fun invoke(reservationIntent: ReservationIntent): Reservation {
        return clientRepository.createReservation(reservationIntent)
    }
}
