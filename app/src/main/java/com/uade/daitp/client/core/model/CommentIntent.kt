package com.uade.daitp.client.core.model

data class CommentIntent(
    var userId: Int,
    val movieId: Int,
    val title: String,
    val body: String,
    val rating: Double
)