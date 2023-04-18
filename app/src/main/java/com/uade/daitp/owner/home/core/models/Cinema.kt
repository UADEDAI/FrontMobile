package com.uade.daitp.owner.home.core.models

data class Cinema(
    val id: Int,
    val name: String,
    val address: String,
    val addressNumber: Int,
    val country: String,
    val province: String,
    val locality: String,
    val neighbourhood: String,
    val latitude: Long,
    val longitude: Long,
    val price: Double,
    val enabled: Boolean
)