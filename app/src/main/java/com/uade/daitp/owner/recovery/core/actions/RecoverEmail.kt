package com.uade.daitp.owner.recovery.core.actions

import com.uade.daitp.login.core.repository.LoginRepository

class RecoverEmail(private val loginRepository: LoginRepository) {

    suspend operator fun invoke(email: String) {
        loginRepository.resetPassword(email)
    }
}