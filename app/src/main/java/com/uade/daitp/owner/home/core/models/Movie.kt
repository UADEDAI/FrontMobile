package com.uade.daitp.owner.home.core.models

import java.util.*

data class Movie(
    val id: Int,
    val title: String,
    val description: String,
    val duration: Int,
    val releaseDate: Date,
    val genre: String,
    val director: String,
    val cast: List<String>,
    val score: Double,
    val certificate: String,
    val imageUrl: String,
    val createdAt: Date,
    val updatedAt: Date,
)