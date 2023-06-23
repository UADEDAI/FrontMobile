package com.uade.daitp.login.core.repository

import com.uade.daitp.login.core.model.LoginIntent
import com.uade.daitp.login.core.model.LoginResponse
import com.uade.daitp.login.core.model.User
import com.uade.daitp.owner.register.core.model.exceptions.*
import retrofit2.http.*

interface LoginService {
    @POST("/auth/login")
    suspend fun loginOwner(@Body intent: LoginIntent): LoginResponse

    @POST("/users")
    suspend fun registerOwner(@Body registerIntent: RegisterIntent): User

    @POST("/auth/validate")
    suspend fun validateOTP(@Body validateIntent: ValidateIntent): LoginResponse

    @POST("/auth/reset")
    suspend fun resetPassword(@Body resetIntent: ResetIntent)

    @POST("/auth/recover")
    suspend fun recoverPassword(@Body recoverIntent: RecoverIntent): User

    @POST("/auth/refresh")
    suspend fun refreshToken(
        @Body refreshIntent: RefreshIntent,
        @Header("Authorization") bearer: String
    ): LoginResponse

    @GET("/users/{id}")
    suspend fun getUser(
        @Path("id") userId: Int,
        @Header("Authorization") bearer: String
    ): User
}