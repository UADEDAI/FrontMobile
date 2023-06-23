package com.uade.daitp.owner.register.core.actions

import com.uade.daitp.login.core.repository.LoginRepository

class ValidateOwner(private val loginRepository: LoginRepository) {

    suspend operator fun invoke(code: String) {
        loginRepository.validateOTP(code)
    }
}
