package com.uade.daitp.login.core.actions

import com.uade.daitp.login.core.repository.UserRepository

class LogoutUser(private val userRepository: UserRepository) {

    suspend operator fun invoke() {
        userRepository.logout()
    }

}