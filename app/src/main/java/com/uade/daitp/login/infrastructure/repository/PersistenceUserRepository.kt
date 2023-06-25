package com.uade.daitp.login.infrastructure.repository

import com.uade.daitp.login.core.model.User

interface PersistenceUserRepository {
    fun saveUser(user: User)
    fun getUser(): User
    fun saveToken(token: String)
    fun getToken(): String
    fun getBearerToken(): String
    fun removeUser()
}