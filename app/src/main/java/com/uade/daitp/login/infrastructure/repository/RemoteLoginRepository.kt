package com.uade.daitp.login.infrastructure.repository

import android.util.Log
import com.auth0.android.jwt.JWT
import com.uade.daitp.login.core.model.LoginIntent
import com.uade.daitp.login.core.model.User
import com.uade.daitp.login.core.repository.LoginRepository
import com.uade.daitp.login.core.repository.LoginService
import com.uade.daitp.owner.register.core.model.exceptions.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf

class RemoteLoginRepository(
    private val loginService: LoginService,
    private val userRepository: UserRepository
) : LoginRepository {

    private var flow = flowOf("")

    override fun getLoggedInOwner(): User {
        return userRepository.getUser()
    }

    override suspend fun loginOwner(userName: String, password: String) {
        val loginIntent = LoginIntent(userName, password)
        val loginResponse = loginService.loginOwner(loginIntent)
        userRepository.saveToken(loginResponse.access_token)

        val jwt = JWT(loginResponse.access_token)
        val userId = jwt.getClaim("id").asInt()!!
        val user = loginService.getUser(userId, createToken(loginResponse.access_token))
        userRepository.saveUser(user)

        refreshToken()
    }

    override suspend fun registerOwner(
        email: String,
        password: String,
        username: String,
        company: String
    ) {
        val registerIntent = RegisterIntent(username, email, password, company, owner)
        val user = loginService.registerOwner(registerIntent)

        userRepository.saveUser(user)
    }

    override suspend fun validateOTP(code: String) {
        val user = getLoggedInOwner()
        val validateIntent = ValidateIntent(user.id, code)

        val token = loginService.validateOTP(validateIntent)
        userRepository.saveToken(token.access_token)

        refreshToken()
    }

    override suspend fun resetPassword(email: String) {
        val resetIntent = ResetIntent(email)
        loginService.resetPassword(resetIntent)
    }

    override suspend fun recoverPassword(code: String, password: String) {
        val recoverIntent = RecoverIntent(password, code)
        val user = loginService.recoverPassword(recoverIntent)
        userRepository.saveUser(user)
    }

    override suspend fun refreshToken() {
        flow = flow {
            try {
                while (true) {
                    val refreshIntent = RefreshIntent(userRepository.getUser().email)
                    val newToken = loginService.refreshToken(refreshIntent, userRepository.getBearerToken())
                    userRepository.saveToken(newToken)
                    Log.d("RemoteLoginRepository", "NEW TOKEN $newToken")
                    emit(newToken)
                    delay(refreshInterval)
                }
            } catch (e: Exception) {
                Log.d("RemoteLoginRepository", "Refresh ended")
            }
        }

        flow.collect()
    }

    private fun createToken(token: String): String {
        return "Bearer $token"
    }

    private companion object {
        const val owner = "owner"
        const val refreshInterval = 3000L
    }
}