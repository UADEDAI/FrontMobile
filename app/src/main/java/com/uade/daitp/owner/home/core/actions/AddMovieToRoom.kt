package com.uade.daitp.owner.home.core.actions

import com.uade.daitp.owner.home.core.models.Movie
import com.uade.daitp.owner.home.core.repository.MovieRepository

class AddMovieToRoom(private val movieRepository: MovieRepository) {

    suspend operator fun invoke(roomId: Int, movie: Movie) {
        movieRepository.addMovieToRoom(roomId, movie)
    }
}
