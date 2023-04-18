package com.uade.daitp.login.core.actions

import com.uade.daitp.login.core.model.exceptions.InvalidUserException
import com.uade.daitp.login.core.repository.LoginRepository
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

internal class LoginOwnerShould {
    private lateinit var loginRepository: LoginRepository
    private lateinit var login: LoginOwner
    private var error: Exception? = null

    @Test
    fun `login should log in a user`() {
        givenARepository()
        givenAnAction()

        whenLoggingIn(validUsername, validPassword)

        thenLoggedInAUser()
    }

    @Test
    fun `login should throw error for invalid user`() {
        givenARepository()
        givenAnAction()

        whenLoggingIn(invalidUsername, invalidPassword)

        thenThrowsError()
    }

    private fun givenARepository() {
        loginRepository = mock()
        whenever(loginRepository.loginOwner(validUsername, validPassword)).thenAnswer { true }
        whenever(loginRepository.loginOwner(invalidUsername, invalidPassword)).thenThrow(
            InvalidUserException("Invalid credentials")
        )
    }

    private fun givenAnAction() {
        login = LoginOwner(loginRepository)
    }

    private fun whenLoggingIn(userName: String, password: String) {
        try {
            login(userName, password)
        } catch (e: Exception) {
            error = e
        }
    }

    private fun thenThrowsError() {
        assert(error is InvalidUserException)
    }

    private fun thenLoggedInAUser() {
        verify(loginRepository).loginOwner(validUsername, validPassword)
    }

    private companion object {
        const val validUsername = "validUsername"
        const val validPassword = "validPassword"
        const val invalidUsername = "invalid"
        const val invalidPassword = "invalid"
    }
}