package com.uade.daitp.login.core.actions

import com.uade.daitp.login.core.repository.LoginRepository

class LoginOwner(private val loginRepository: LoginRepository) {

    suspend operator fun invoke(userName: String, password: String) {
        loginRepository.loginOwner(userName, password)
    }
}