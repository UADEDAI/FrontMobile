package com.uade.daitp.owner.recovery.core.actions

import com.uade.daitp.login.core.repository.LoginRepository
import com.uade.daitp.owner.recovery.core.models.exceptions.InvalidRecoveryEmailException
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

internal class RecoverEmailShould {

    private lateinit var recoverRepository: LoginRepository
    private lateinit var recoverEmail: RecoverEmail
    private var error: Exception? = null

    @Test
    fun `recover email should call service`() = runTest {
        givenAnAction()

        whenRecoveringEmail(testEmail)

        thenRecoveryServiceIsCalled()
    }

    @Test
    fun `recover email throw error if invalid email`() = runTest {
        givenAnAction()

        whenRecoveringEmail(invalidEmail)

        thenErrorIsThrown()
    }

    private suspend fun givenAnAction() {
        recoverRepository = mock()
        whenever(recoverRepository.resetPassword(invalidEmail)).thenThrow(
            InvalidRecoveryEmailException("Invalid email")
        )
        recoverEmail = RecoverEmail(recoverRepository)
    }

    private suspend fun whenRecoveringEmail(email: String) {
        try {
            recoverEmail(email)
        } catch (e: Exception) {
            error = e
        }
    }

    private suspend fun thenRecoveryServiceIsCalled() {
        verify(recoverRepository).resetPassword(testEmail)
    }

    private fun thenErrorIsThrown() {
        assert(error is InvalidRecoveryEmailException)
    }

    private companion object {
        const val testEmail = "test@test.com"
        const val invalidEmail = "non@existent.com"
    }
}