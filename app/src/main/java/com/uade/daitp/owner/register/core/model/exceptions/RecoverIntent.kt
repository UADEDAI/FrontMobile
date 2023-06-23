package com.uade.daitp.owner.register.core.model.exceptions

data class RecoverIntent(private val password: String, private val code: String)