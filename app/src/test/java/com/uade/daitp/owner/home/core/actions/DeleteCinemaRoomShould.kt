package com.uade.daitp.owner.home.core.actions

import com.uade.daitp.owner.home.core.models.exceptions.CinemaRoomNotFoundException
import com.uade.daitp.owner.home.core.repository.CinemaRepository
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

internal class DeleteCinemaRoomShould {
    private lateinit var repository: CinemaRepository
    private lateinit var deleteCinemaRoom: DeleteCinemaRoom
    private var error: Exception? = null

    @Test
    fun `delete cinema room should call delete to repository`() {
        givenAnAction()

        whenDeletingACinemaRoom(cinemaRoomId)

        thenRepositoryIsCalled()
    }

    @Test
    fun `non existent cinema room Id throws error`() {
        givenAnAction()

        whenDeletingACinemaRoom(invalidCinemaRoomId)

        thenErrorIsThrown()
    }

    private fun givenAnAction() {
        repository = mock()
        whenever(repository.deleteCinemaRoom(invalidCinemaRoomId)).thenThrow(
            CinemaRoomNotFoundException("$invalidCinemaRoomId not found")
        )
        deleteCinemaRoom = DeleteCinemaRoom(repository)
    }

    private fun whenDeletingACinemaRoom(cinemaId: Int) {
        try {
            deleteCinemaRoom.invoke(cinemaId)
        } catch (e: Exception) {
            error = e
        }
    }

    private fun thenRepositoryIsCalled() {
        verify(repository).deleteCinemaRoom(cinemaRoomId)
    }

    private fun thenErrorIsThrown() {
        assert(error is CinemaRoomNotFoundException)
    }

    private companion object {
        const val cinemaRoomId = 1
        const val invalidCinemaRoomId = 2
    }
}