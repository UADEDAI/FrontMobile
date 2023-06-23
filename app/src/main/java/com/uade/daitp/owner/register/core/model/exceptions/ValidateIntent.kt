package com.uade.daitp.owner.register.core.model.exceptions

data class ValidateIntent(
    private val userId: Int,
    private val code: String
)