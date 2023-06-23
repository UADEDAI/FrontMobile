package com.uade.daitp.login.infrastructure.repository

import android.util.Log
import com.auth0.android.jwt.JWT
import com.uade.daitp.login.core.model.LoginIntent
import com.uade.daitp.login.core.model.User
import com.uade.daitp.login.core.repository.LoginRepository
import com.uade.daitp.login.core.repository.LoginService
import com.uade.daitp.owner.home.core.models.exceptions.SessionExpiredException
import com.uade.daitp.owner.register.core.model.exceptions.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import java.util.Calendar

class RemoteLoginRepository(
    private val loginService: LoginService,
    private val userRepository: UserRepository
) : LoginRepository {

    private var flow = flowOf("")

    override fun getLoggedInOwner(): User {
        return userRepository.getUser()
    }

    override suspend fun loginRemember() {
        val savedToken = userRepository.getToken()
        val jwt = JWT(savedToken)
        if (jwt.isExpired(0)) throw SessionExpiredException("Access token expired, need to re login")

        val userId = jwt.getClaim("id").asInt()!!
        val user = loginService.getUser(userId, createToken(savedToken))
        userRepository.saveUser(user)

        refreshToken()
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

    private fun refreshToken() {
        CoroutineScope(Dispatchers.Default).launch {
            flow = flow {
                try {
                    while (true) {
                        if (isAboutToExpire(userRepository.getToken())) {
                            val accessToken = requestNewAccessToken()
                            emit(accessToken)
                        } else {
                            emit("")
                        }
                        delay(refreshInterval)
                    }
                } catch (e: Exception) {
                    Log.d("RemoteLoginRepository", "Refresh ended")
                }
            }

            flow.collect()
        }
    }

    private fun isAboutToExpire(token: String): Boolean {
        val jwt = JWT(token)
        val expirationTime = Calendar.getInstance()
        expirationTime.time = jwt.expiresAt!!

        val fiveMinAfter = Calendar.getInstance()
        fiveMinAfter.add(Calendar.MINUTE, 5)
        return fiveMinAfter.time.after(expirationTime.time)
    }

    private suspend fun requestNewAccessToken(): String {
        val refreshIntent = RefreshIntent(userRepository.getUser().email)
        val newToken = loginService.refreshToken(refreshIntent, userRepository.getBearerToken())
        userRepository.saveToken(newToken.access_token)
        Log.d("RemoteLoginRepository", "NEW TOKEN ${newToken.access_token}")
        return newToken.access_token
    }

    private fun createToken(token: String): String {
        return "Bearer $token"
    }

    private companion object {
        const val owner = "owner"
        const val refreshInterval = 60 * 1000L
    }
}