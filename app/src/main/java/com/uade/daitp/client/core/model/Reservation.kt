package com.uade.daitp.client.core.model

import java.util.*

data class Reservation(
    val id: Int,
    val screeningId: Int,
    val userId: Int,
    val time: Date,
    val screening: ScreeningClient,
    val seats: List<AvailableSeat>?,
    val otp: OTP
)