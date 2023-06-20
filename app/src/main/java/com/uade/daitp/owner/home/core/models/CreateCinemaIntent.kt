package com.uade.daitp.owner.home.core.models

data class CreateCinemaIntent(
    val userId: Int,
    val name: String,
    val street: String,
    val streetNum: Int,
    val country: String,
    val state: String,
    val city: String,
    val neighbourhood: String,
    val latitude: Double,
    val longitude: Double,
    val price: Double,
    val enabled: Boolean
) {
    fun toCinema(id: Int): Cinema {
        return Cinema(
            id,
            userId,
            name,
            street,
            streetNum,
            country,
            state,
            city,
            neighbourhood,
            latitude.toString(),
            longitude.toString(),
            price,
            enabled,
            0,
            0
        )
    }
}
