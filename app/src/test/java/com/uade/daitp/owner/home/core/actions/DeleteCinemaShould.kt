package com.uade.daitp.owner.home.core.actions

import com.uade.daitp.owner.home.core.models.exceptions.CinemaNotFoundException
import com.uade.daitp.owner.home.core.repository.CinemaRepository
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

internal class DeleteCinemaShould {

    private lateinit var repository: CinemaRepository
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
            CinemaNotFoundException("$invalidCinemaId not found")
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
        assert(error is CinemaNotFoundException)
    }

    private companion object {
        const val cinemaId = 1
        const val invalidCinemaId = 2
    }
}