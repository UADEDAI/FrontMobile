package com.uade.daitp.login.core.repository

import com.uade.daitp.login.core.model.UserIntent

interface UserRepository {
    suspend fun logout()
    suspend fun updateUser(userId: Int, userIntent: UserIntent)
    suspend fun deleteUser()
}