package com.uade.daitp.owner.home.core.models

import com.uade.daitp.owner.home.core.models.enums.ScreeningFormat
import java.util.*

data class CreateScreeningIntent(
    val roomId: Int,
    val movieId: Int,
    val format: ScreeningFormat,
    val startAt: Date,
    val endAt: Date,
    val availableSeats: List<String>,
    val createdAt: Date,
    val updatedAt: Date
) {
    fun toScreening(id: Int): Screening {
        return Screening(
            id,
            roomId,
            movieId,
            format,
            startAt,
            endAt,
            availableSeats,
            createdAt,
            updatedAt
        )
    }
}
