package com.uade.daitp.owner.register.core.actions

import com.uade.daitp.login.core.repository.LoginRepository

class RegisterOwner(private val loginRepository: LoginRepository) {

    suspend operator fun invoke(email: String, password: String, username: String, company: String) {
        loginRepository.registerOwner(email, password, username, company)
    }
}
