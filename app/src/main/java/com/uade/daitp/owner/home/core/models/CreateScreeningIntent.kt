package com.uade.daitp.owner.home.core.models

import com.uade.daitp.owner.home.core.models.enums.ScreeningFormat

data class CreateScreeningIntent(
    val roomId: Int,
    val movieId: Int,
    val format: ScreeningFormat,
    val startAt: String,
    val endAt: String,
)
