package com.uade.daitp.owner.home.core.actions

import com.uade.daitp.owner.home.core.models.CreateCinemaIntent
import com.uade.daitp.owner.home.core.models.exceptions.InvalidCinemaNameException
import com.uade.daitp.owner.home.core.repository.CinemaRepository
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

internal class AddCinemaShould {

    private lateinit var repository: CinemaRepository
    private lateinit var addCinema: AddCinema
    private var error: Exception? = null

    @Test
    fun `add cinema should call repository`() = runTest {
        givenAnAction()

        whenAddingACinema(cinemaIntent)

        thenRepositoryIsCalled()
    }

    @Test
    fun `used cinema name throws exception`() = runTest {
        givenAnAction()

        whenAddingACinema(invalidCinemaIntent)

        thenErrorIsThrown()
    }

    private suspend fun givenAnAction() {
        repository = mock()
        whenever(repository.createCinema(invalidCinemaIntent)).thenThrow(
            InvalidCinemaNameException("Name already in use")
        )
        addCinema = AddCinema(repository)
    }

    private suspend fun whenAddingACinema(cinemaIntent: CreateCinemaIntent) {
        try {
            addCinema.invoke(cinemaIntent)
        } catch (e: Exception) {
            error = e
        }
    }

    private suspend fun thenRepositoryIsCalled() {
        verify(repository).createCinema(cinemaIntent)
    }

    private fun thenErrorIsThrown() {
        assert(error is InvalidCinemaNameException)
    }

    private companion object {
        val cinemaIntent = CreateCinemaIntent(
            0,
            "Hoyts",
            "Av Corrientes",
            1234,
            "Argentina",
            "Buenos Aires",
            "CABA",
            "Almagro",
            1000.0,
            true
        )
        val invalidCinemaIntent =  CreateCinemaIntent(
            0,
            "INVALID",
            "Av Corrientes",
            1234,
            "Argentina",
            "Buenos Aires",
            "CABA",
            "Almagro",
            1000.0,
            true
        )
    }
}