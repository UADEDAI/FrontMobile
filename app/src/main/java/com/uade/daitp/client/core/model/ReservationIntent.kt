package com.uade.daitp.client.core.model

data class ReservationIntent(
    val userId: Int,
    val screeningId: Int,
    val seats: List<Int>,
    val time: String
)