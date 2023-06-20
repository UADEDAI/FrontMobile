package com.uade.daitp.owner.home.core.repository.service

import com.uade.daitp.owner.home.core.models.*
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

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

    @GET("/screenings/TODO")
    suspend fun getScreeningsBy(@Path("movieId") movieId: Int, @Path("roomId") roomId: Int): List<Screening>

    @GET("/screenings/{id}")
    suspend fun getScreening(@Path("id") screeningId: Int): Screening

    @DELETE("/screenings/{id}")
    suspend fun deleteScreening(@Path("id") screeningId: Int)
}