package com.uade.daitp.owner.home.core.models

import com.uade.daitp.owner.home.core.models.enums.ScreeningFormat
import java.util.Date

data class Screening(
    val id: Int,
    val roomId: Int,
    val movieId: Int,
    val format: ScreeningFormat,
    val startAt: Date,
    val endAt: Date,
    val availableSeats: List<String>,
    val createdAt: Date,
    val updatedAt: Date
)