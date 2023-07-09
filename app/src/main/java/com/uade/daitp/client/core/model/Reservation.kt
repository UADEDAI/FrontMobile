package com.uade.daitp.client.core.model

import com.uade.daitp.owner.home.core.models.Screening
import java.util.*

data class Reservation(
    val id: Int,
    val screeningId: Int,
    val userId: Int,
    val time: Date,
    val screening: Screening
)