package com.uade.daitp.owner.home.core.repository

import com.uade.daitp.owner.home.core.models.CreateScreeningIntent
import com.uade.daitp.owner.home.core.models.Movie
import com.uade.daitp.owner.home.core.models.MoviesList
import com.uade.daitp.owner.home.core.models.Screening

interface MovieRepository {
    suspend fun getMovies(): MoviesList
    suspend fun getMovie(movieId: Int): Movie
    suspend fun getMoviesByRoom(roomId: Int): MoviesList
    suspend fun addMovieToRoom(roomId: Int, movie: Movie)
    suspend fun deleteMovieFromRoom(roomId: Int, movieId: Int)

    suspend fun addScreening(intent: CreateScreeningIntent)
    suspend fun getScreenings(): List<Screening>
    suspend fun getScreeningsBy(movieId: Int, roomId: Int): List<Screening>
    suspend fun getScreening(screeningId: Int): Screening
    suspend fun deleteScreening(screeningId: Int)
}