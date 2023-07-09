package com.uade.daitp.client.core.actions

import com.uade.daitp.client.core.model.ReservationIntent
import com.uade.daitp.client.core.repository.ClientRepository

class CreateReservation(private val clientRepository: ClientRepository) {

    suspend operator fun invoke(reservationIntent: ReservationIntent) {
        clientRepository.createReservation(reservationIntent)
    }
}
