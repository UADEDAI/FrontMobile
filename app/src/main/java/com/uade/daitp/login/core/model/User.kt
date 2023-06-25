package com.uade.daitp.login.core.model

data class User(
    val id: Int,
    val name: String,
    val email: String,
    val company: String,
    val role: String
)