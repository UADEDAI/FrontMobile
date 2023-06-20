package com.uade.daitp.owner.home.core.actions

import com.uade.daitp.owner.home.core.models.Movie
import com.uade.daitp.owner.home.core.repository.MovieRepository

class GetMovie(private val movieRepository: MovieRepository) {

    suspend operator fun invoke(movieId: Int): Movie {
        return movieRepository.getMovie(movieId)
    }
}
