package com.uade.daitp.owner.recovery.core.actions

import com.uade.daitp.owner.recovery.core.models.exceptions.InvalidRecoveryEmailException
import com.uade.daitp.owner.register.core.repository.OwnerRepository
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

internal class RecoverEmailShould {

    private lateinit var recoverRepository: OwnerRepository
    private lateinit var recoverEmail: RecoverEmail
    private var error: Exception? = null

    @Test
    fun `recover email should call service`() {
        givenAnAction()

        whenRecoveringEmail(testEmail)

        thenRecoveryServiceIsCalled()
    }

    @Test
    fun `recover email throw error if invalid email`() {
        givenAnAction()

        whenRecoveringEmail(invalidEmail)

        thenErrorIsThrown()
    }

    private fun givenAnAction() {
        recoverRepository = mock()
        whenever(recoverRepository.recoverEmail(invalidEmail)).thenThrow(
            InvalidRecoveryEmailException("Invalid email")
        )
        recoverEmail = RecoverEmail(recoverRepository)
    }

    private fun whenRecoveringEmail(email: String) {
        try {
            recoverEmail(email)
        } catch (e: Exception) {
            error = e
        }
    }

    private fun thenRecoveryServiceIsCalled() {
        verify(recoverRepository).recoverEmail(testEmail)
    }

    private fun thenErrorIsThrown() {
        assert(error is InvalidRecoveryEmailException)
    }

    private companion object {
        const val testEmail = "test@test.com"
        const val invalidEmail = "non@existent.com"
    }
}