package com.uade.daitp.owner.home.core.actions

import com.uade.daitp.owner.home.core.models.Screening
import com.uade.daitp.owner.home.core.repository.MovieRepository

class GetScreeningsBy(private val movieRepository: MovieRepository) {

    operator fun invoke(movieId: Int, roomId: Int): List<Screening> {
        return movieRepository.getScreeningsBy(movieId, roomId)
    }
}
