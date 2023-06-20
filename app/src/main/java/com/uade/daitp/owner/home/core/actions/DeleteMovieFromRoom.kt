package com.uade.daitp.owner.home.core.actions

import com.uade.daitp.owner.home.core.repository.MovieRepository

class DeleteMovieFromRoom(private val movieRepository: MovieRepository) {

    suspend operator fun invoke(roomId: Int, movieId: Int) {
        movieRepository.deleteMovieFromRoom(roomId, movieId)
    }
}
