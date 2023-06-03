package com.uade.daitp.owner.home.core.repository

import com.uade.daitp.owner.home.core.models.*

interface MovieRepository {
    fun getMovies(): MoviesList
    fun getMovie(movieId: Int): Movie
    fun getMoviesByRoom(roomId: Int): MoviesList
    fun addMovieToRoom(roomId: Int, movie: Movie)
    fun deleteMovieFromRoom(roomId: Int, movieId: Int)

    fun addScreening(intent: CreateScreeningIntent)
    fun getScreenings(): List<Screening>
    fun getScreening(screeningId: Int): Screening
    fun deleteScreening(screeningId: Int)
}