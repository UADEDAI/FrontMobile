package com.uade.daitp.login.core.repository

import com.uade.daitp.client.core.model.*
import com.uade.daitp.owner.home.core.models.Cinema
import com.uade.daitp.owner.home.core.models.MoviesList
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ClientService {
    @GET("/reservations")
    suspend fun getReservations(): List<Reservation>

    @GET("/reservations/{id}")
    suspend fun getReservationById(@Path("id") reservationId: Int): Reservation

    @POST("/reservations")
    suspend fun createReservation(@Body reservationIntent: ReservationIntent): Reservation

    @GET("/screenings/{id}/available-seats")
    suspend fun getAvailableSeats(@Path("id") screeningId: Int): List<AvailableSeat>

    @GET("/cinemas/nearest")
    suspend fun getNearestCinemas(
        @Query("lat") latitude: Double,
        @Query("lng") longitude: Double,
        @Query("distance") distance: Double,
        @Query("movie") movieId: Int
    ): List<Cinema>

    @GET("/movies/clients")
    suspend fun getFilteredMovies(
        @Query("lat") latitude: Double,
        @Query("lng") longitude: Double,
        @Query("distance") distance: Double,
        @Query("title") title: String? = null,
        @Query("genre") genre: String? = null,
        @Query("score") score: Double? = null
    ): MoviesList

    @GET("/movies/{id}/comments")
    suspend fun getComments(
        @Path("id") movieId: Int
    ): List<Comment>

    @POST("/movies/{id}/comments")
    suspend fun createComment(@Path("id") movieId: Int, @Body commentIntent: CommentIntent): Comment
}