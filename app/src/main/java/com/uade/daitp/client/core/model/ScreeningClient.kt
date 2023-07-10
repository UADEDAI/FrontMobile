package com.uade.daitp.client.core.model

import com.uade.daitp.owner.home.core.models.Movie
import java.util.*

data class ScreeningClient(
    val id: Int,
    val roomId: Int,
    val movieId: Int,
    val format: String,
    val startAt: Date,
    val endAt: Date,
    val movie: Movie,
    val room: CinemaRoomClient,
    val availableSeats: Int
)