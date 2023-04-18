package com.uade.daitp.owner.home.core.actions

import com.uade.daitp.owner.home.core.models.Cinema
import com.uade.daitp.owner.register.core.repository.OwnerRepository
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

internal class GetCinemasShould {

    private lateinit var repository: OwnerRepository
    private lateinit var getCinemas: GetCinemas
    private lateinit var cinemas: List<Cinema>

    @Test
    fun `get cinemas should call repository`() {
        givenAnAction()

        whenGettingCinemas()

        thenRepositoryIsCalled()
    }

    @Test
    fun `get empty cinema list if there are no cinemas`() {
        givenAnActionWithEmptyRepository()

        whenGettingCinemas()

        thenGetsEmptyList()
    }

    private fun givenAnAction() {
        repository = mock()
        whenever(repository.getCinemas()).thenReturn(listOf(mockCinema))
        getCinemas = GetCinemas(repository)
    }

    private fun givenAnActionWithEmptyRepository() {
        repository = mock()
        whenever(repository.getCinemas()).thenReturn(listOf())
        getCinemas = GetCinemas(repository)
    }

    private fun whenGettingCinemas() {
        cinemas = getCinemas()
    }

    private fun thenRepositoryIsCalled() {
        verify(repository).getCinemas()
    }

    private fun thenGetsEmptyList() {
        assert(cinemas.isEmpty())
    }

    private companion object {
        val mockCinema = Cinema(
            1,
            "Hoyts",
            "Av Corrientes",
            1234,
            "Argentina",
            "Buenos Aires",
            "CABA",
            "Almagro",
            1234,
            1234,
            1000.0,
            true
        )
    }
}