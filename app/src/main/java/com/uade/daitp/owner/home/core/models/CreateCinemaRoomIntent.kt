package com.uade.daitp.owner.home.core.models

data class CreateCinemaRoomIntent(
    val cinemaId: Int,
    val name: String,
    val numRows: Int,
    val seats: Int,
    val enabled: Boolean
)