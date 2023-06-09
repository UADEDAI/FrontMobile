package com.uade.daitp.owner.home.core.models

data class Cinema(
    val id: Int,
    val userId: Int,
    val name: String,
    val street: String,
    val streetNum: Int,
    val country: String,
    val city: String,
    val state: String,
    val neighbourhood: String,
    val latitude: String,
    val longitude: String,
    val price: Double,
    val enabled: Boolean
)