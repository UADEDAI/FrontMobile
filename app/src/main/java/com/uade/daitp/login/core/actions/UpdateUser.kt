package com.uade.daitp.login.core.actions

import com.uade.daitp.login.core.model.UserIntent
import com.uade.daitp.login.core.repository.UserRepository

class UpdateUser(private val userRepository: UserRepository) {

    suspend operator fun invoke(userId: Int, userIntent: UserIntent) {
        userRepository.updateUser(userId, userIntent)
    }
}