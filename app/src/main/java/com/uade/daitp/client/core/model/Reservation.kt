package com.uade.daitp.client.core.model

data class Reservation(
    val id: Int,
    val userId: Int,
    val screeningId: Int,
    val seats: List<String>,
    val active: Boolean
)