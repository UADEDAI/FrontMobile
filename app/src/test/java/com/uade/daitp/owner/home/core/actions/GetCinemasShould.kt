package com.uade.daitp.owner.home.core.actions

import com.uade.daitp.owner.home.core.models.Cinema
import com.uade.daitp.owner.home.core.repository.CinemaRepository
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

internal class GetCinemasShould {

    private lateinit var repository: CinemaRepository
    private lateinit var getCinemas: GetCinemas
    private lateinit var cinemas: List<Cinema>

    @Test
    fun `get cinemas should call repository`() = runTest {
        givenAnAction()

        whenGettingCinemas()

        thenRepositoryIsCalled()
    }

    @Test
    fun `get empty cinema list if there are no cinemas`() = runTest {
        givenAnActionWithEmptyRepository()

        whenGettingCinemas()

        thenGetsEmptyList()
    }

    private suspend fun givenAnAction() {
        repository = mock()
        whenever(repository.getCinemas()).thenReturn(listOf(mockCinema))
        getCinemas = GetCinemas(repository)
    }

    private suspend fun givenAnActionWithEmptyRepository() {
        repository = mock()
        whenever(repository.getCinemas()).thenReturn(listOf())
        getCinemas = GetCinemas(repository)
    }

    private suspend fun whenGettingCinemas() {
        cinemas = getCinemas()
    }

    private suspend fun thenRepositoryIsCalled() {
        verify(repository).getCinemas()
    }

    private fun thenGetsEmptyList() {
        assert(cinemas.isEmpty())
    }

    private companion object {
        val mockCinema = Cinema(
            1,
            0,
            "Hoyts",
            "Av Corrientes",
            1234,
            "Argentina",
            "Buenos Aires",
            "CABA",
            "Almagro",
            "1234",
            "1234",
            1000.0,
            true,
            0,
            0
        )
    }
}