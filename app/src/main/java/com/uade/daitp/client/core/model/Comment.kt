package com.uade.daitp.client.core.model

data class Comment(
    val id: Int,
    val userId: Int,
    val movieId: Int,
    val title: String,
    val body: String,
    val rating: Double,
    val user: Author?
)