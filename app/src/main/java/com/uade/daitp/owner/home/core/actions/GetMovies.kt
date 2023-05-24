package com.uade.daitp.owner.home.core.actions

import com.uade.daitp.owner.home.core.models.MoviesList
import com.uade.daitp.owner.home.core.repository.MovieRepository

class GetMovies(private val movieRepository: MovieRepository) {

    operator fun invoke(): MoviesList {
        return movieRepository.getMovies()
    }
}
