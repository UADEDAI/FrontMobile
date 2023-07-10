package com.uade.daitp.login.core.model

data class User(
    val id: Int,
    val username: String?,
    val email: String,
    val company: String?,
    val role: String,
    val avatar: String,
)