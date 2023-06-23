package com.uade.daitp.owner.home.core.repository

import com.uade.daitp.owner.home.core.models.Cinema
import com.uade.daitp.owner.home.core.models.CinemaRoom
import com.uade.daitp.owner.home.core.models.CreateCinemaIntent
import com.uade.daitp.owner.home.core.models.CreateCinemaRoomIntent

interface CinemaRepository {
    suspend fun createCinema(cinemaIntent: CreateCinemaIntent)
    suspend fun updateCinema(id: Int, cinemaIntent: CreateCinemaIntent)
    suspend fun deleteCinema(cinemaId: Int)
    suspend fun getCinemas(): List<Cinema>
    suspend fun getCinema(cinemaId: Int): Cinema

    suspend fun createCinemaRoom(cinemaRoomIntent: CreateCinemaRoomIntent)
    suspend fun updateCinemaRoom(id: Int, cinemaRoomIntent: CreateCinemaRoomIntent)
    suspend fun deleteCinemaRoom(id: Int)
    suspend fun getCinemaRooms(cinemaId: Int): List<CinemaRoom>
    suspend fun getCinemaRoom(cinemaRoomId: Int): CinemaRoom
}