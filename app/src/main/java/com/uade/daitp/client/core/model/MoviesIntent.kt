package com.uade.daitp.client.core.model

data class MoviesIntent(
    val distance: Double,
    val lat: Double,
    val lng: Double,
    val title: String? = null,
    val genre: String? = null,
    val score: Double? = null
)