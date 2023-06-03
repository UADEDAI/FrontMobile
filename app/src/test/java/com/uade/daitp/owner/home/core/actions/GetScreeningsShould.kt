package com.uade.daitp.owner.home.core.actions

import com.uade.daitp.owner.home.core.models.Screening
import com.uade.daitp.owner.home.core.models.enums.ScreeningFormat
import com.uade.daitp.owner.home.core.repository.MovieRepository
import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.util.*

internal class GetScreeningsShould {

    private lateinit var repository: MovieRepository
    private lateinit var getScreenings: GetScreenings
    private lateinit var screeningsList: List<Screening>

    @Test
    fun `get screenings should call repository`() {
        givenAnAction()

        whenGettingScreenings()

        thenRepositoryIsCalled()
    }

    @Test
    fun `get empty list if there are no screenings`() {
        givenAnActionWithEmptyRepository()

        whenGettingScreenings()

        thenGetsEmptyList()
    }

    private fun givenAnActionWithEmptyRepository() {
        repository = mock()
        whenever(repository.getScreenings()).thenReturn(emptyScreeningList)
        getScreenings = GetScreenings(repository)
    }

    private fun givenAnAction() {
        repository = mock()
        whenever(repository.getScreenings()).thenReturn(screeningList)
        getScreenings = GetScreenings(repository)
    }

    private fun whenGettingScreenings() {
        screeningsList = getScreenings()
    }

    private fun thenRepositoryIsCalled() {
        verify(repository).getScreenings()
    }

    private fun thenGetsEmptyList() {
        Assertions.assertTrue { screeningsList.isEmpty() }
    }

    private companion object {
        val emptyScreeningList = listOf<Screening>()

        val screeningList = listOf(
            Screening(
                0,
                0,
                0,
                ScreeningFormat.SUBTITLED,
                Calendar.getInstance().time,
                Calendar.getInstance().time,
                listOf(),
                Calendar.getInstance().time,
                Calendar.getInstance().time
            )
        )
    }
}