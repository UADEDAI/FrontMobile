package com.uade.daitp.owner.home.core.models

data class CinemaRoom(
    val id: Int,
    val cinemaId: Int,
    val name: String,
    val numRows: Int,
    val seats: Int,
    val enabled: Boolean,
    val movies: List<Movie>?
)