package com.uade.daitp.login.core.repository

import com.uade.daitp.login.core.model.User
import com.uade.daitp.login.core.model.UserIntent
import retrofit2.http.*

interface UserService {
    @PUT("/users/{id}")
    suspend fun updateUser(
        @Path("id") userId: Int,
        @Body userIntent: UserIntent
    ): User

    @DELETE("/users/{id}")
    suspend fun deleteUser(@Path("id") userId: Int)
}