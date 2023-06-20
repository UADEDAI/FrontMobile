package com.uade.daitp.owner.home.core.actions

import com.uade.daitp.owner.home.core.models.CreateCinemaRoomIntent
import com.uade.daitp.owner.home.core.models.exceptions.InvalidCinemaRoomNameException
import com.uade.daitp.owner.home.core.repository.CinemaRepository
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

internal class AddCinemaRoomShould {
    private lateinit var repository: CinemaRepository
    private lateinit var addCinemaRoom: AddCinemaRoom
    private var error: Exception? = null

    @Test
    fun `add cinema room should call repository`() = runTest {
        givenAnAction()

        whenAddingACinema(cinemaRoomIntent)

        thenRepositoryIsCalled()
    }

    @Test
    fun `used cinema room name throws exception`() = runTest {
        givenAnAction()

        whenAddingACinema(invalidCinemaRoomIntent)

        thenErrorIsThrown()
    }

    private suspend fun givenAnAction() {
        repository = mock()
        whenever(repository.createCinemaRoom(invalidCinemaRoomIntent)).thenThrow(
            InvalidCinemaRoomNameException("Name already in use")
        )
        addCinemaRoom = AddCinemaRoom(repository)
    }

    private suspend fun whenAddingACinema(cinemaRoomIntent: CreateCinemaRoomIntent) {
        try {
            addCinemaRoom.invoke(cinemaRoomIntent)
        } catch (e: Exception) {
            error = e
        }
    }

    private suspend fun thenRepositoryIsCalled() {
        verify(repository).createCinemaRoom(cinemaRoomIntent)
    }

    private fun thenErrorIsThrown() {
        assert(error is InvalidCinemaRoomNameException)
    }

    private companion object {
        val cinemaRoomIntent = CreateCinemaRoomIntent(
            7,
            "Sala 1",
            20,
            30,
            true
        )
        val invalidCinemaRoomIntent = CreateCinemaRoomIntent(
            7,
            "INVALID",
            20,
            30,
            true
        )
    }
}