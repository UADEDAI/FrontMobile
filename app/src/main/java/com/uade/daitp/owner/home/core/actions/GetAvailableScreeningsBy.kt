package com.uade.daitp.owner.home.core.actions

import com.uade.daitp.owner.home.core.repository.MovieRepository

class GetAvailableScreeningsBy(private val movieRepository: MovieRepository) {

    suspend operator fun invoke(movieId: Int, roomId: Int): List<String> {
        return movieRepository.getAvailableScreeningsBy(movieId, roomId)
    }
}
