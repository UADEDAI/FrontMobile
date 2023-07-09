package com.uade.daitp.client.core.model

data class CommentIntent(
    val userId: Int,
    val movieId: Int,
    val title: String,
    val body: String,
    val rating: Int
)