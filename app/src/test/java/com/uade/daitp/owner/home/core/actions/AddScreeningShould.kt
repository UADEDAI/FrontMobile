package com.uade.daitp.owner.home.core.actions

import com.uade.daitp.owner.home.core.models.CreateScreeningIntent
import com.uade.daitp.owner.home.core.models.enums.ScreeningFormat
import com.uade.daitp.owner.home.core.models.exceptions.InvalidScreeningTimeException
import com.uade.daitp.owner.home.core.repository.MovieRepository
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.util.*

internal class AddScreeningShould {

    private lateinit var repository: MovieRepository
    private lateinit var addScreening: AddScreening
    private var error: Exception? = null

    @Test
    fun `add screening should call repository`() = runTest {
        givenAnAction()

        whenAddingAScreening(screeningIntent)

        thenRepositoryIsCalled()
    }

    @Test
    fun `invalid screening time throws exception`() = runTest {
        givenAnAction()

        whenAddingAScreening(invalidScreeningIntent)

        thenErrorIsThrown()
    }

    private suspend fun givenAnAction() {
        repository = mock()
        whenever(repository.addScreening(invalidScreeningIntent)).thenThrow(
            InvalidScreeningTimeException("Invalid screening time")
        )
        addScreening = AddScreening(repository)
    }

    private suspend fun whenAddingAScreening(screeningIntent: CreateScreeningIntent) {
        try {
            addScreening(screeningIntent)
        } catch (e: Exception) {
            error = e
        }
    }

    private suspend fun thenRepositoryIsCalled() {
        verify(repository).addScreening(screeningIntent)
    }

    private fun thenErrorIsThrown() {
        assert(error is InvalidScreeningTimeException)
    }

    private companion object {
        val screeningIntent = CreateScreeningIntent(
            0,
            0,
            ScreeningFormat.SUBTITLED,
            Calendar.getInstance().time,
            Calendar.getInstance().time
        )

        val invalidScreeningIntent = CreateScreeningIntent(
            0,
            0,
            ScreeningFormat.SUBTITLED,
            Calendar.getInstance().time,
            Calendar.getInstance().time
        )
    }
}