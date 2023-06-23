package com.uade.daitp.login.core.actions

import com.uade.daitp.login.core.model.User
import com.uade.daitp.login.core.repository.LoginRepository

class GetUser(private val loginRepository: LoginRepository) {

    operator fun invoke(): User {
        return loginRepository.getLoggedInOwner()
    }
}