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
import com.uade.daitp.owner.home.core.repository.service.CinemaService

class RemoteCinemaRepository(private val cinemaService: CinemaService) : CinemaRepository {
    override suspend fun createCinema(cinemaIntent: CreateCinemaIntent) {
        if (cinemaIntent.name == "invalid") throw InvalidCinemaNameException("Name already in use")

        cinemaService.createCinema(cinemaIntent)
    }

    override suspend fun deleteCinema(cinemaId: Int) {
        try {
            cinemaService.deleteCinema(cinemaId)
        } catch (e: Exception) {
            throw CinemaNotFoundException("$cinemaId does not exist")
        }
    }

    override suspend fun getCinemas(): List<Cinema> {
        return cinemaService.getCinemas()
    }

    override suspend fun getCinema(cinemaId: Int): Cinema {
        try {
            return cinemaService.getCinema(cinemaId)
        } catch (e: Exception) {
            throw CinemaNotFoundException("$cinemaId does not exist")
        }
    }

    override suspend fun createCinemaRoom(cinemaRoomIntent: CreateCinemaRoomIntent) {
        try {
            cinemaService.createCinemaRoom(cinemaRoomIntent)
        } catch (e: Exception) {
            throw InvalidCinemaRoomNameException("Name already in use")
        }
    }

    override suspend fun deleteCinemaRoom(id: Int) {
        try {
            cinemaService.deleteCinemaRoom(id)
        } catch (e: Exception) {
            throw CinemaRoomNotFoundException("$id does not exist")
        }
    }

    override suspend fun getCinemaRooms(cinemaId: Int): List<CinemaRoom> {
        return cinemaService.getCinemaRooms(cinemaId)
    }

    override suspend fun getCinemaRoom(cinemaRoomId: Int): CinemaRoom {
        try {
            return cinemaService.getCinemaRoom(cinemaRoomId)
        } catch (e: Exception) {
            throw CinemaRoomNotFoundException("$cinemaRoomId does not exist")
        }
    }
}