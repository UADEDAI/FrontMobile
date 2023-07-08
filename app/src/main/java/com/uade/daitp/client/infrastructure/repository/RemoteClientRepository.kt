package com.uade.daitp.client.infrastructure.repository

import com.uade.daitp.client.core.model.Reservation
import com.uade.daitp.client.core.repository.ClientRepository

class RemoteClientRepository: ClientRepository {
    override suspend fun getReservations(): List<Reservation> {
        return emptyList()
    }
}