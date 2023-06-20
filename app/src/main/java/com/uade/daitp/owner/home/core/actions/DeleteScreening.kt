package com.uade.daitp.owner.home.core.actions

import com.uade.daitp.owner.home.core.repository.MovieRepository

class DeleteScreening(private val movieRepository: MovieRepository) {

    suspend operator fun invoke(screeningId: Int) {
        movieRepository.deleteScreening(screeningId)
    }
}