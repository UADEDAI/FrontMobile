package com.uade.daitp.client.core.model

import com.uade.daitp.owner.home.core.models.Cinema

data class CinemaRoomClient(
    val id: Int,
    val cinemaId: Int,
    val name: String,
    val numRows: Int,
    val seats: Int,
    val enabled: Boolean,
    val cinema: Cinema
)