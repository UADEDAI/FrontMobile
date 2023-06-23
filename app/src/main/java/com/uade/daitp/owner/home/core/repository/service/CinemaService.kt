package com.uade.daitp.owner.home.core.repository.service

import com.uade.daitp.owner.home.core.models.Cinema
import com.uade.daitp.owner.home.core.models.CinemaRoom
import com.uade.daitp.owner.home.core.models.CreateCinemaIntent
import com.uade.daitp.owner.home.core.models.CreateCinemaRoomIntent
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface CinemaService {
    @POST("/cinemas")
    suspend fun createCinema(@Body cinemaIntent: CreateCinemaIntent)

    @DELETE("/cinemas/{id}")
    suspend fun deleteCinema(@Path("id") cinemaId: Int)

    @GET("/users/{id}/cinemas")
    suspend fun getCinemas(@Path("id") userId: Int): List<Cinema>

    @GET("/cinemas/{id}")
    suspend fun getCinema(@Path("id") cinemaId: Int): Cinema


    @POST("/rooms")
    suspend fun createCinemaRoom(@Body cinemaRoomIntent: CreateCinemaRoomIntent)

    @DELETE("/rooms/{id}")
    suspend fun deleteCinemaRoom(id: Int)

    @GET("/cinemas/{id}/rooms")
    suspend fun getCinemaRooms(@Path("id") cinemaId: Int): List<CinemaRoom>

    @GET("/rooms/{id}")
    suspend fun getCinemaRoom(@Path("id") cinemaRoomId: Int): CinemaRoom
}