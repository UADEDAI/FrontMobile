package com.uade.daitp.owner.home.core.actions

import com.uade.daitp.owner.home.core.models.exceptions.InvalidCinemaNotFoundException
import com.uade.daitp.owner.register.core.repository.OwnerRepository
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

internal class DeleteCinemaShould {

    private lateinit var repository: OwnerRepository
    private lateinit var deleteCinema: DeleteCinema
    private var error: Exception? = null

    @Test
    fun `delete cinema should call delete to repository`() {
        givenAnAction()

        whenDeletingACinema(cinemaId)

        thenRepositoryIsCalled()
    }

    @Test
    fun `non existent cinema Id throws error`() {
        givenAnAction()

        whenDeletingACinema(invalidCinemaId)

        thenErrorIsThrown()
    }

    private fun givenAnAction() {
        repository = mock()
        whenever(repository.deleteCinema(invalidCinemaId)).thenThrow(
            InvalidCinemaNotFoundException("$invalidCinemaId not found")
        )
        deleteCinema = DeleteCinema(repository)
    }

    private fun whenDeletingACinema(cinemaId: Int) {
        try {
            deleteCinema.invoke(cinemaId)
        } catch (e: Exception) {
            error = e
        }
    }

    private fun thenRepositoryIsCalled() {
        verify(repository).deleteCinema(cinemaId)
    }

    private fun thenErrorIsThrown() {
        assert(error is InvalidCinemaNotFoundException)
    }

    private companion object {
        const val cinemaId = 1
        const val invalidCinemaId = 2
    }
}