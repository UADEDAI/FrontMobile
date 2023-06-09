package com.uade.daitp.owner.home.core.actions

import com.uade.daitp.owner.home.core.models.exceptions.ScreeningNotFoundException
import com.uade.daitp.owner.home.core.repository.MovieRepository
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

internal class DeleteScreeningShould {

    private lateinit var repository: MovieRepository
    private lateinit var deleteScreening: DeleteScreening
    private var error: Exception? = null

    @Test
    fun `delete screening should call delete to repository`() = runTest {
        givenAnAction()

        whenDeletingAScreening(screeningId)

        thenRepositoryIsCalled()
    }

    @Test
    fun `non existent cinema Id throws error`() = runTest {
        givenAnAction()

        whenDeletingAScreening(invalidScreeningId)

        thenErrorIsThrown()
    }

    private suspend fun givenAnAction() {
        repository = mock()
        whenever(repository.deleteScreening(invalidScreeningId)).thenThrow(
            ScreeningNotFoundException("$invalidScreeningId not found")
        )
        deleteScreening = DeleteScreening(repository)
    }

    private suspend fun whenDeletingAScreening(screeningId: Int) {
        try {
            deleteScreening.invoke(screeningId)
        } catch (e: Exception) {
            error = e
        }
    }

    private suspend fun thenRepositoryIsCalled() {
        verify(repository).deleteScreening(screeningId)
    }

    private fun thenErrorIsThrown() {
        assert(error is ScreeningNotFoundException)
    }

    private companion object {
        const val screeningId = 1
        const val invalidScreeningId = 2
    }
}