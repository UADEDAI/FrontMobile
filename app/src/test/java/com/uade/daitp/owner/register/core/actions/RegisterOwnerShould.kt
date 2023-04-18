package com.uade.daitp.owner.register.core.actions

import com.uade.daitp.owner.register.core.model.exceptions.InvalidRegisterDataError
import com.uade.daitp.owner.register.core.repository.OwnerRepository
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

internal class RegisterOwnerShould {
    private lateinit var ownerRepository: OwnerRepository
    private lateinit var registerOwner: RegisterOwner
    private var error: Exception? = null

    @Test
    fun `register should register an owner`() {
        givenARepository()
        givenARegisterAction()

        whenRegisteringAnOwner(validEmail, validPassword, validUsername, validCompany)

        thenUserIsRegistered()
    }

    @Test
    fun `used email should throw error`() {
        givenARepository()
        givenARegisterAction()

        whenRegisteringAnOwner(invalidEmail, validPassword, validUsername, validCompany)

        thenErrorIsThrown()
    }

    private fun givenARepository() {
        ownerRepository = mock()
        whenever(
            ownerRepository.registerOwner(
                validEmail,
                validPassword,
                validUsername,
                validCompany
            )
        ).thenAnswer { true }
        whenever(
            ownerRepository.registerOwner(
                invalidEmail,
                validPassword,
                validUsername,
                validCompany
            )
        ).thenThrow(
            InvalidRegisterDataError("Email is already used")
        )
    }

    private fun givenARegisterAction() {
        registerOwner = RegisterOwner(ownerRepository)
    }

    private fun whenRegisteringAnOwner(
        email: String, password: String, username: String, company: String
    ) {
        try {
            registerOwner(email, password, username, company)
        } catch (e: Exception) {
            error = e
        }
    }

    private fun thenUserIsRegistered() {
        verify(ownerRepository).registerOwner(
            validEmail, validPassword, validUsername, validCompany
        )
    }

    private fun thenErrorIsThrown() {
        assert(error is InvalidRegisterDataError)
    }

    private companion object {
        const val validEmail = "email@email.com"
        const val validPassword = "password"
        const val validUsername = "username"
        const val validCompany = "company"
        const val invalidEmail = "no@email.com"
    }
}