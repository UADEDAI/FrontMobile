package com.uade.daitp.module.infrastructure

import com.uade.daitp.owner.home.core.models.Cinema
import com.uade.daitp.owner.home.core.models.CreateCinemaIntent
import com.uade.daitp.owner.home.core.models.exceptions.InvalidCinemaNameException
import com.uade.daitp.owner.home.core.models.exceptions.InvalidCinemaNotFoundException
import com.uade.daitp.owner.recovery.core.models.exceptions.InvalidRecoveryEmailException
import com.uade.daitp.owner.recovery.core.models.exceptions.InvalidRecoveryPasswordException
import com.uade.daitp.owner.register.core.model.exceptions.InvalidRegisterDataError
import com.uade.daitp.owner.register.core.repository.OwnerRepository

class InMemoryOwnerRepository : OwnerRepository {
    private val cinemas: MutableList<Cinema> = mutableListOf()

    override fun registerOwner(email: String, password: String, username: String, company: String) {
        if (email != "test@test.com") throw InvalidRegisterDataError("Email already used")
    }

    override fun recoverEmail(email: String) {
        if (email != "test@test.com") throw InvalidRecoveryEmailException("Invalid email")
    }

    override fun recoverPassword(code: String, password: String) {
        if (code != "1234") throw InvalidRecoveryPasswordException("Invalid code")
    }

    override fun createCinema(cinemaIntent: CreateCinemaIntent) {
        if (cinemaIntent.name == "invalid") throw InvalidCinemaNameException("Name already in use")

        cinemas.add(cinemaIntent.toCinema(getNewId()))
    }

    override fun deleteCinema(cinemaId: Int) {
        val deleted = cinemas.removeIf { cinema -> cinema.id == cinemaId }

        if (!deleted) throw InvalidCinemaNotFoundException("$cinemaId does not exist")
    }

    override fun getCinemas(): List<Cinema> {
        return cinemas
    }

    override fun getCinema(cinemaId: Int): Cinema {
        val cinema = cinemas.find { cinema: Cinema -> cinema.id == cinemaId }
        cinema?.let { return cinema }
            ?: throw InvalidCinemaNotFoundException("$cinemaId does not exist")
    }

    private fun getNewId(): Int {
        return cinemas.size
    }
}
