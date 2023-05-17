package com.uade.daitp.owner.home.infrastructure

import com.uade.daitp.owner.home.core.models.Cinema
import com.uade.daitp.owner.home.core.models.CinemaRoom
import com.uade.daitp.owner.home.core.models.CreateCinemaIntent
import com.uade.daitp.owner.home.core.models.CreateCinemaRoomIntent
import com.uade.daitp.owner.home.core.models.exceptions.InvalidCinemaNameException
import com.uade.daitp.owner.home.core.models.exceptions.InvalidCinemaNotFoundException
import com.uade.daitp.owner.home.core.models.exceptions.InvalidCinemaRoomNameException
import com.uade.daitp.owner.home.core.models.exceptions.InvalidCinemaRoomNotFoundException
import com.uade.daitp.owner.home.core.repository.CinemaRepository

class InMemoryCinemaRepository : CinemaRepository {
    private val cinemas: MutableList<Cinema> = mutableListOf()
    private val cinemaRooms: MutableList<CinemaRoom> = mutableListOf()

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

    override fun createCinemaRoom(cinemaRoomIntent: CreateCinemaRoomIntent) {
        if (cinemaRoomIntent.name == "invalid") throw InvalidCinemaRoomNameException("Name already in use")

        cinemaRooms.add(cinemaRoomIntent.toCinemaRoom(getNewId()))
    }

    override fun deleteCinemaRoom(id: Int) {
        val deleted = cinemaRooms.removeIf { cinemaRoom -> cinemaRoom.id == id }

        if (!deleted) throw InvalidCinemaRoomNotFoundException("$id does not exist")
    }

    override fun getCinemaRooms(cinemaId: Int): List<CinemaRoom> {
        return cinemaRooms.filter { cinemaRoom -> cinemaRoom.cinemaId == cinemaId }
    }

    private fun getNewId(): Int {
        return cinemas.size
    }
}