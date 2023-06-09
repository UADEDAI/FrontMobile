package com.uade.daitp.owner.home.core.models

data class CreateCinemaIntent(
    val userId: Int,
    val name: String,
    val address: String,
    val addressNumber: Int,
    val country: String,
    val province: String,
    val locality: String,
    val neighbourhood: String,
    val latitude: String,
    val longitude: String,
    val price: Double,
    val enabled: Boolean
) {
    fun toCinema(id: Int): Cinema {
        return Cinema(
            id,
            userId,
            name,
            address,
            addressNumber,
            country,
            province,
            locality,
            neighbourhood,
            latitude,
            longitude,
            price,
            enabled
        )
    }
}
