package com.uade.daitp.login.core.repository

import com.uade.daitp.login.core.model.User

interface LoginRepository {
    fun getLoggedInOwner(): User
    suspend fun loginOwner(userName: String, password: String)
    suspend fun registerOwner(email: String, password: String, username: String, company: String)
    suspend fun validateOTP(code: String)
    suspend fun resetPassword(email: String)
    suspend fun recoverPassword(code: String, password: String)
    suspend fun refreshToken()
}