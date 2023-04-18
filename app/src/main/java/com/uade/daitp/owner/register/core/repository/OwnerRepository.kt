package com.uade.daitp.owner.register.core.repository

import com.uade.daitp.owner.home.core.models.Cinema
import com.uade.daitp.owner.home.core.models.CreateCinemaIntent

interface OwnerRepository {
    fun registerOwner(email: String, password: String, username: String, company: String)
    fun recoverEmail(email: String)
    fun recoverPassword(code: String, password: String)
    fun createCinema(cinemaIntent: CreateCinemaIntent)
    fun deleteCinema(cinemaId: Int)
    fun getCinemas(): List<Cinema>
}