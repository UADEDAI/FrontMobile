package com.uade.daitp.owner.home.core.actions

import com.uade.daitp.owner.home.core.models.CreateScreeningIntent
import com.uade.daitp.owner.home.core.repository.MovieRepository

class AddScreening(private val movieRepository: MovieRepository) {

    operator fun invoke(screeningIntent: CreateScreeningIntent) {
        movieRepository.addScreening(screeningIntent)
    }
}
