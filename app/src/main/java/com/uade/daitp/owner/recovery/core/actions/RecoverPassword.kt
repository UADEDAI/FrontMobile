package com.uade.daitp.owner.recovery.core.actions

import com.uade.daitp.login.core.repository.LoginRepository

class RecoverPassword(private val loginRepository: LoginRepository) {

    suspend operator fun invoke(code: String, password: String) {
        loginRepository.recoverPassword(code, password)
    }
}
