package com.uade.daitp.owner.home.core.actions

import com.uade.daitp.owner.home.core.models.MoviesList
import com.uade.daitp.owner.home.core.repository.MovieRepository

class GetMoviesByRoom(private val movieRepository: MovieRepository) {

    suspend operator fun invoke(roomId: Int): MoviesList {
        return movieRepository.getMoviesByRoom(roomId)
    }
}
