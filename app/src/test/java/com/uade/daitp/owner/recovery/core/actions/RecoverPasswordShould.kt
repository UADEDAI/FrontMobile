package com.uade.daitp.owner.recovery.core.actions

import com.uade.daitp.login.core.repository.LoginRepository
import com.uade.daitp.owner.recovery.core.models.exceptions.InvalidRecoveryPasswordException
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

internal class RecoverPasswordShould {

    private lateinit var ownerRepository: LoginRepository
    private lateinit var recoverPassword: RecoverPassword
    private var error: Exception? = null

    @Test
    fun `Recover password calls repository`() = runTest {
        givenAnAction()

        whenRecoveringPassword(validCode, validPassword)

        thenPasswordIsRecovered()
    }

    @Test
    fun `Invalid code throws error`() = runTest {
        givenAnAction()

        whenRecoveringPassword(invalidCode, validPassword)

        thenErrorIsThrown()
    }

    private suspend fun givenAnAction() {
        ownerRepository = mock()
        whenever(ownerRepository.recoverPassword(invalidCode, validPassword)).thenThrow(
            InvalidRecoveryPasswordException("Invalid code")
        )
        recoverPassword = RecoverPassword(ownerRepository)
    }

    private suspend fun whenRecoveringPassword(code: String, password: String) {
        try {
            recoverPassword(code, password)
        } catch (e: Exception) {
            error = e
        }
    }

    private suspend fun thenPasswordIsRecovered() {
        verify(ownerRepository).recoverPassword(validCode, validPassword)
    }

    private fun thenErrorIsThrown() {
        assert(error is InvalidRecoveryPasswordException)
    }

    private companion object {
        const val invalidCode = "666"
        const val validCode = "1234"
        const val validPassword = "password"
    }
}
