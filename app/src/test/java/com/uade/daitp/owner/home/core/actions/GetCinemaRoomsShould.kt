package com.uade.daitp.owner.home.core.actions

import com.uade.daitp.owner.home.core.models.CinemaRoom
import com.uade.daitp.owner.home.core.repository.CinemaRepository
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

internal class GetCinemaRoomsShould {
    private lateinit var repository: CinemaRepository
    private lateinit var getCinemaRooms: GetCinemaRooms
    private lateinit var cinemaRooms: List<CinemaRoom>

    @Test
    fun `get cinemaRooms should call repository`() = runTest {
        givenAnAction()

        whenGettingCinemas()

        thenRepositoryIsCalled()
    }

    @Test
    fun `get empty cinema list if there are no cinema rooms`() = runTest  {
        givenAnActionWithEmptyRepository()

        whenGettingCinemas()

        thenGetsEmptyList()
    }

    private fun givenAnAction() {
        repository = mock()
        whenever(repository.getCinemaRooms(mockCinemaRoom.cinemaId)).thenReturn(
            listOf(
                mockCinemaRoom
            )
        )
        getCinemaRooms = GetCinemaRooms(repository)
    }

    private suspend fun givenAnActionWithEmptyRepository() {
        repository = mock()
        whenever(repository.getCinemas()).thenReturn(listOf())
        getCinemaRooms = GetCinemaRooms(repository)
    }

    private fun whenGettingCinemas() {
        cinemaRooms = getCinemaRooms(mockCinemaRoom.cinemaId)
    }

    private fun thenRepositoryIsCalled() {
        verify(repository).getCinemaRooms(mockCinemaRoom.cinemaId)
    }

    private fun thenGetsEmptyList() {
        assert(cinemaRooms.isEmpty())
    }

    private companion object {
        val mockCinemaRoom = CinemaRoom(
            1,
            7,
            "Sala 1",
            30,
            20,
            true
        )
    }
}