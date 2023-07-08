package com.uade.daitp.login.infrastructure.repository

import android.util.Log
import com.auth0.android.jwt.JWT
import com.uade.daitp.login.core.model.LoginGoogleIntent
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
import java.util.*

class RemoteLoginRepository(
    private val loginService: LoginService,
    private val persistenceUserRepository: PersistenceUserRepository
) : LoginRepository {

    private var flow = flowOf("")

    override fun getLoggedInOwner(): User {
        return persistenceUserRepository.getUser()
    }

    override suspend fun loginRemember() {
        val savedToken = persistenceUserRepository.getToken()
        val jwt = JWT(savedToken)
        if (jwt.isExpired(0)) throw SessionExpiredException("Access token expired, need to re login")

        val userId = jwt.getClaim("id").asInt()!!
        val user = loginService.getUser(userId, createToken(savedToken))
        persistenceUserRepository.saveUser(user)

        startRefreshTokenInterval()
    }

    override suspend fun loginOwner(userName: String, password: String) {
        val loginIntent = LoginIntent(userName, password)
        val loginResponse = loginService.loginOwner(loginIntent)
        persistenceUserRepository.saveToken(loginResponse.access_token)

        val jwt = JWT(loginResponse.access_token)
        val userId = jwt.getClaim("id").asInt()!!
        val user = loginService.getUser(userId, createToken(loginResponse.access_token))
        persistenceUserRepository.saveUser(user)

        startRefreshTokenInterval()
    }

    override suspend fun loginClient(idToken: String): Boolean {
        val loginResponse = loginService.loginClient(LoginGoogleIntent(idToken))
        persistenceUserRepository.saveToken(loginResponse.access_token)

        val jwt = JWT(loginResponse.access_token)
        val userId = jwt.getClaim("id").asInt()!!
        val user = loginService.getUser(userId, createToken(loginResponse.access_token))
        persistenceUserRepository.saveUser(user)

        startRefreshTokenInterval()

        return user.username == null
    }

    override suspend fun registerOwner(
        email: String,
        password: String,
        username: String,
        company: String
    ) {
        val registerIntent = RegisterIntent(username, email, password, company, owner)
        val user = loginService.registerOwner(registerIntent)

        persistenceUserRepository.saveUser(user)
    }

    override suspend fun validateOTP(code: String) {
        val user = getLoggedInOwner()
        val validateIntent = ValidateIntent(user.id, code)

        val token = loginService.validateOTP(validateIntent)
        persistenceUserRepository.saveToken(token.access_token)

        startRefreshTokenInterval()
    }

    override suspend fun resetPassword(email: String) {
        val resetIntent = ResetIntent(email)
        loginService.resetPassword(resetIntent)
    }

    override suspend fun recoverPassword(code: String, password: String) {
        val recoverIntent = RecoverIntent(password, code)
        val user = loginService.recoverPassword(recoverIntent)
        persistenceUserRepository.saveUser(user)
    }

    private fun startRefreshTokenInterval() {
        CoroutineScope(Dispatchers.Default).launch {
            flow = flow {
                try {
                    while (true) {
                        if (isAboutToExpire(persistenceUserRepository.getToken())) {
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
        val refreshIntent = RefreshIntent(persistenceUserRepository.getUser().email)
        val newToken =
            loginService.refreshToken(refreshIntent, persistenceUserRepository.getBearerToken())
        persistenceUserRepository.saveToken(newToken.access_token)
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