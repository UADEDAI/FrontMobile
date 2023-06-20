package com.uade.daitp.owner.home.core.actions

import com.uade.daitp.owner.home.core.models.Screening
import com.uade.daitp.owner.home.core.repository.MovieRepository

class GetScreenings(private val movieRepository: MovieRepository) {

    suspend operator fun invoke(): List<Screening> {
        return movieRepository.getScreenings()
    }
}
