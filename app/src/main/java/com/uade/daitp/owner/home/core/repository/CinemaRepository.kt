package com.uade.daitp.owner.home.core.repository

import com.uade.daitp.owner.home.core.models.Cinema
import com.uade.daitp.owner.home.core.models.CinemaRoom
import com.uade.daitp.owner.home.core.models.CreateCinemaIntent
import com.uade.daitp.owner.home.core.models.CreateCinemaRoomIntent

interface CinemaRepository {
    fun createCinema(cinemaIntent: CreateCinemaIntent)
    fun deleteCinema(cinemaId: Int)
    fun getCinemas(): List<Cinema>
    fun getCinema(cinemaId: Int): Cinema
    fun createCinemaRoom(cinemaRoomIntent: CreateCinemaRoomIntent)
    fun deleteCinemaRoom(id: Int)
    fun getCinemaRooms(cinemaId: Int): List<CinemaRoom>
}