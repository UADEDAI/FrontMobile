package com.uade.daitp.login.core.actions

import com.uade.daitp.login.core.repository.LoginRepository

class LoginClient(private val loginRepository: LoginRepository) {

    suspend operator fun invoke(idToken: String): Boolean {
        return loginRepository.loginClient(idToken)
    }
}