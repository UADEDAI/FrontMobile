package com.uade.daitp.client.core.repository

import com.uade.daitp.client.core.model.Reservation

interface ClientRepository {
    suspend fun getReservations(): List<Reservation>
}