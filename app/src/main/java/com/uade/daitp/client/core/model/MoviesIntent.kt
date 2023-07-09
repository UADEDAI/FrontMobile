package com.uade.daitp.client.core.model

data class MoviesIntent(
    private val distance: Int,
    private val lat: Double,
    private val lng: Double,
    private val genre: String,
    private val score: Int
)