package com.uade.daitp.owner.register.core.model.exceptions

data class RegisterIntent(
    private val username: String,
    private val email: String,
    private val password: String,
    private val company: String,
    private val role: String
)