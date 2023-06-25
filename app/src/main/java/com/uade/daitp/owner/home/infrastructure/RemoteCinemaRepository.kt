package com.uade.daitp.owner.home.infrastructure

import com.uade.daitp.login.infrastructure.repository.PersistenceUserRepository
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

class RemoteCinemaRepository(
    private val cinemaService: CinemaService,
    private val persistenceUserRepository: PersistenceUserRepository
) : CinemaRepository {
    override suspend fun createCinema(cinemaIntent: CreateCinemaIntent) {
        try {
            cinemaService.createCinema(cinemaIntent)
        } catch (e: Exception) {
            throw InvalidCinemaNameException("Name already in use")
        }
    }

    override suspend fun updateCinema(id: Int, cinemaIntent: CreateCinemaIntent) {
        try {
            cinemaService.updateCinema(id, cinemaIntent)
        } catch (e: Exception) {
            throw CinemaNotFoundException("cinema: $id does not exist")
        }
    }

    override suspend fun deleteCinema(cinemaId: Int) {
        try {
            cinemaService.deleteCinema(cinemaId)
        } catch (e: Exception) {
            throw CinemaNotFoundException("$cinemaId does not exist")
        }
    }

    override suspend fun getCinemas(): List<Cinema> {
        return cinemaService.getCinemas(persistenceUserRepository.getUser().id)
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

    override suspend fun updateCinemaRoom(id: Int, cinemaRoomIntent: CreateCinemaRoomIntent) {
        try {
            cinemaService.updateCinemaRoom(id, cinemaRoomIntent)
        } catch (e: Exception) {
            throw CinemaRoomNotFoundException("$id does not exist")
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