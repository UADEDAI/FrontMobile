package com.uade.daitp.owner.home.core.repository.service

import com.uade.daitp.owner.home.core.models.Cinema
import com.uade.daitp.owner.home.core.models.CinemaRoom
import com.uade.daitp.owner.home.core.models.CreateCinemaIntent
import com.uade.daitp.owner.home.core.models.CreateCinemaRoomIntent
import retrofit2.http.*

interface CinemaService {
    @POST("/cinemas")
    suspend fun createCinema(@Body cinemaIntent: CreateCinemaIntent)

    @PUT("/cinemas/{id}")
    suspend fun updateCinema(
        @Path("id") cinemaId: Int,
        @Body cinemaIntent: CreateCinemaIntent
    ): Cinema

    @DELETE("/cinemas/{id}")
    suspend fun deleteCinema(@Path("id") cinemaId: Int)

    @GET("/users/{id}/cinemas")
    suspend fun getCinemas(@Path("id") userId: Int): List<Cinema>

    @GET("/cinemas/{id}")
    suspend fun getCinema(@Path("id") cinemaId: Int): Cinema


    @POST("/rooms")
    suspend fun createCinemaRoom(@Body cinemaRoomIntent: CreateCinemaRoomIntent)

    @PUT("/rooms/{id}")
    suspend fun updateCinemaRoom(
        @Path("id") cinemaRoomId: Int,
        @Body cinemaRoomIntent: CreateCinemaRoomIntent
    ): CinemaRoom

    @DELETE("/rooms/{id}")
    suspend fun deleteCinemaRoom(@Path("id") cinemaRoomId: Int)

    @GET("/cinemas/{id}/rooms")
    suspend fun getCinemaRooms(@Path("id") cinemaRoomId: Int): List<CinemaRoom>

    @GET("/rooms/{id}")
    suspend fun getCinemaRoom(@Path("id") cinemaRoomId: Int): CinemaRoom
}