package com.uade.daitp.owner.home.core.actions

import com.uade.daitp.owner.home.core.repository.MovieRepository

class DeleteScreening(private val movieRepository: MovieRepository) {

    operator fun invoke(screeningId: Int) {
        movieRepository.deleteScreening(screeningId)
    }
}