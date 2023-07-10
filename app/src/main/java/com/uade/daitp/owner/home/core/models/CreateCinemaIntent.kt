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
    val price: Double,
    val enabled: Boolean
)
