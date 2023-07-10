package com.uade.daitp.client.core.model

data class SeatViewData(
    val row: Int,
    val number: Int,
    val availableSeat: AvailableSeat?,
    var selected: Boolean = false
)