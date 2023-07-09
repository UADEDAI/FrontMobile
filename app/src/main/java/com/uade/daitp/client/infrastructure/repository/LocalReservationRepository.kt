package com.uade.daitp.client.infrastructure.repository

import com.uade.daitp.client.core.model.ScreeningClient

class LocalReservationRepository {

    lateinit var screeningClient: ScreeningClient
    var dateInMillis: Long = 0
    var ticketsNumber: Int = 0

    fun saveReservationRepository(
        ticketsNumber: Int,
        dateInMillis: Long,
        screeningClient: ScreeningClient
    ) {
        this.ticketsNumber = ticketsNumber
        this.dateInMillis = dateInMillis
        this.screeningClient = screeningClient
    }
}