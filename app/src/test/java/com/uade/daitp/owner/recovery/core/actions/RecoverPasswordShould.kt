package com.uade.daitp.owner.recovery.core.actions

import com.uade.daitp.owner.recovery.core.models.exceptions.InvalidRecoveryPasswordException
import com.uade.daitp.owner.register.core.repository.OwnerRepository
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

internal class RecoverPasswordShould {

    private lateinit var ownerRepository: OwnerRepository
    private lateinit var recoverPassword: RecoverPassword
    private var error: Exception? = null

    @Test
    fun `Recover password calls repository`() {
        givenAnAction()

        whenRecoveringPassword(validCode, validPassword)

        thenPasswordIsRecovered()
    }

    @Test
    fun `Invalid code throws error`() {
        givenAnAction()

        whenRecoveringPassword(invalidCode, validPassword)

        thenErrorIsThrown()
    }

    private fun givenAnAction() {
        ownerRepository = mock()
        whenever(ownerRepository.recoverPassword(invalidCode, validPassword)).thenThrow(
            InvalidRecoveryPasswordException("Invalid code")
        )
        recoverPassword = RecoverPassword(ownerRepository)
    }

    private fun whenRecoveringPassword(code: String, password: String) {
        try {
            recoverPassword(code, password)
        } catch (e: Exception) {
            error = e
        }
    }

    private fun thenPasswordIsRecovered() {
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
