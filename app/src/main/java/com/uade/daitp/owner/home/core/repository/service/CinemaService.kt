package com.uade.daitp.owner.home.core.repository.service

import com.uade.daitp.owner.home.core.models.Cinema
import retrofit2.http.GET

interface CinemaService {
    @GET("/cinemas")
    suspend fun getCinemas(): List<Cinema>

}