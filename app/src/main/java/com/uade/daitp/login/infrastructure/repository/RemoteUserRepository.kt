package com.uade.daitp.login.infrastructure.repository

import com.uade.daitp.login.core.model.UserIntent
import com.uade.daitp.login.core.repository.UserRepository
import com.uade.daitp.login.core.repository.UserService

class RemoteUserRepository(
    private val userService: UserService,
    private val persistedUserRepository: PersistenceUserRepository
) : UserRepository {
    override suspend fun logout() {
        persistedUserRepository.removeUser()
    }

    override suspend fun updateUser(userId: Int, userIntent: UserIntent) {
        val user = userService.updateUser(userId, userIntent)
        persistedUserRepository.saveUser(user)
    }

    override suspend fun deleteUser() {
        userService.deleteUser(persistedUserRepository.getUser().id)
        persistedUserRepository.removeUser()
    }
}