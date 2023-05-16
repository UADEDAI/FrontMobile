package com.uade.daitp.owner.home.core.models

data class CinemaRoom(
    val id: Int,
    val cinemaId: Int,
    val name: String,
    val rows: Int,
    val seats: Int,
    val enabled: Boolean
)