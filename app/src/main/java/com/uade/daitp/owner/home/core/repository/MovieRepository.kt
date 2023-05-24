package com.uade.daitp.owner.home.core.repository

import com.uade.daitp.owner.home.core.models.*

interface MovieRepository {
    fun getMovies(): MoviesList
    fun getMovie(movieId: Int): Movie
}