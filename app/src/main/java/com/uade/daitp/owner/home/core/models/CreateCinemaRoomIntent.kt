package com.uade.daitp.owner.home.core.models

data class CreateCinemaRoomIntent(
    val cinemaId: Int,
    val name: String,
    val rows: Int,
    val seats: Int,
    val enabled: Boolean
) {
    fun toCinemaRoom(id: Int): CinemaRoom {
        return CinemaRoom(
            id,
            cinemaId,
            name,
            rows,
            seats,
            enabled
        )
    }
}
