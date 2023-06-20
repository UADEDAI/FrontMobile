package com.uade.daitp.owner.home.infrastructure

import com.uade.daitp.owner.home.core.models.Cinema
import com.uade.daitp.owner.home.core.models.CinemaRoom
import com.uade.daitp.owner.home.core.models.CreateCinemaIntent
import com.uade.daitp.owner.home.core.models.CreateCinemaRoomIntent
import com.uade.daitp.owner.home.core.models.exceptions.CinemaNotFoundException
import com.uade.daitp.owner.home.core.models.exceptions.CinemaRoomNotFoundException
import com.uade.daitp.owner.home.core.models.exceptions.InvalidCinemaNameException
import com.uade.daitp.owner.home.core.models.exceptions.InvalidCinemaRoomNameException
import com.uade.daitp.owner.home.core.repository.CinemaRepository

class InMemoryCinemaRepository : CinemaRepository {
    private val cinemas: MutableList<Cinema> = mutableListOf(
        Cinema(
            0,
            0,
            "Hoyts",
            "Av Corrientes",
            1234,
            "Argentina",
            "CABA",
            "CABA",
            "CABA",
            "1234",
            "1234",
            1000.0,
            true
        )
    )
    private val cinemaRooms: MutableList<CinemaRoom> = mutableListOf(
        CinemaRoom(0, 0, "Main Hall", 20, 20, true)
    )

    override suspend fun createCinema(cinemaIntent: CreateCinemaIntent) {
        if (cinemaIntent.name == "invalid") throw InvalidCinemaNameException("Name already in use")

        cinemas.add(cinemaIntent.toCinema(getNewId()))
    }

    override suspend fun deleteCinema(cinemaId: Int) {
        val deleted = cinemas.removeIf { cinema -> cinema.id == cinemaId }

        if (!deleted) throw CinemaNotFoundException("$cinemaId does not exist")
    }

    override suspend fun getCinemas(): List<Cinema> {
        return cinemas
    }

    override suspend fun getCinema(cinemaId: Int): Cinema {
        val cinema = cinemas.find { cinema: Cinema -> cinema.id == cinemaId }
        cinema?.let { return cinema }
            ?: throw CinemaNotFoundException("$cinemaId does not exist")
    }

    override suspend fun createCinemaRoom(cinemaRoomIntent: CreateCinemaRoomIntent) {
        if (cinemaRoomIntent.name == "invalid") throw InvalidCinemaRoomNameException("Name already in use")

        cinemaRooms.add(cinemaRoomIntent.toCinemaRoom(getNewRoomId()))
    }

    override suspend fun deleteCinemaRoom(id: Int) {
        val deleted = cinemaRooms.removeIf { cinemaRoom -> cinemaRoom.id == id }

        if (!deleted) throw CinemaRoomNotFoundException("$id does not exist")
    }

    override suspend fun getCinemaRooms(cinemaId: Int): List<CinemaRoom> {
        return cinemaRooms.filter { cinemaRoom -> cinemaRoom.cinemaId == cinemaId }
    }

    override suspend fun getCinemaRoom(cinemaRoomId: Int): CinemaRoom {
        val cinemaRoom =
            cinemaRooms.find { cinemaRoom: CinemaRoom -> cinemaRoom.id == cinemaRoomId }
        cinemaRoom?.let { return cinemaRoom }
            ?: throw CinemaRoomNotFoundException("$cinemaRoomId does not exist")
    }

    private fun getNewId(): Int {
        return cinemas.size
    }

    private fun getNewRoomId(): Int {
        return cinemaRooms.size
    }
}