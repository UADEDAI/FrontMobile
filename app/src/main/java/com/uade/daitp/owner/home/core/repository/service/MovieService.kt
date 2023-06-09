package com.uade.daitp.owner.home.core.repository.service

import com.uade.daitp.owner.home.core.models.*
import retrofit2.http.*

interface MovieService {
    @GET("/movies")
    suspend fun getMovies(): MoviesList

    @GET("/movies/{id}")
    suspend fun getMovie(@Path("id") movieId: Int): Movie

    @GET("/rooms/{id}/movies")
    suspend fun getMoviesByRoom(@Path("id") roomId: Int): MoviesList

    @POST("/rooms/{id}/movies")
    suspend fun addMovieToRoom(@Path("id") roomId: Int, @Body movie: AddMovieInRoomIntent)

    @DELETE("/rooms/{roomId}/movies/{movieId}")
    suspend fun deleteMovieFromRoom(@Path("roomId") roomId: Int, @Path("movieId") movieId: Int)


    @POST("/screenings")
    suspend fun addScreening(@Body intent: CreateScreeningIntent)

    @GET("/screenings")
    suspend fun getScreenings(): List<Screening>

    @GET("/rooms/{roomId}/movie-screenings/{movieId}")
    suspend fun getScreeningsBy(
        @Path("roomId") roomId: Int,
        @Path("movieId") movieId: Int,
    ): List<Screening>

    @GET("/rooms/{id}/screenings/time-available")
    suspend fun getAvailableScreeningsBy(
        @Path("id") roomId: Int,
        @Query("duration") duration: Int
    ): List<String>

    @GET("/screenings/{id}")
    suspend fun getScreening(@Path("id") screeningId: Int): Screening

    @DELETE("/screenings/{id}")
    suspend fun deleteScreening(@Path("id") screeningId: Int)
}