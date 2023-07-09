package com.uade.daitp.client.core.model

data class ReservationIntent(
    private val userId: Int,
    private val screeningId: Int,
    private val seats: List<Int>,
    private val time: String
)