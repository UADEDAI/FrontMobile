package com.uade.daitp.owner.home.core.repository

import com.uade.daitp.owner.home.core.models.*

interface CinemaRepository {
    fun createCinema(cinemaIntent: CreateCinemaIntent)
    fun deleteCinema(cinemaId: Int)
    fun getCinemas(): List<Cinema>
    fun getCinema(cinemaId: Int): Cinema

    fun createCinemaRoom(cinemaRoomIntent: CreateCinemaRoomIntent)
    fun deleteCinemaRoom(id: Int)
    fun getCinemaRooms(cinemaId: Int): List<CinemaRoom>
    fun getCinemaRoom(cinemaRoomId: Int): CinemaRoom
}