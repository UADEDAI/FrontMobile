package com.uade.daitp.owner.home.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.uade.daitp.owner.home.core.actions.*
import com.uade.daitp.owner.home.core.models.CinemaRoom
import com.uade.daitp.owner.home.core.models.MoviesList

class OwnerMoviesViewModel(
    private val getCinemaRoom: GetCinemaRoom,
    private val getMoviesByRoom: GetMoviesByRoom,
    private val addMoviesToRoom: AddMovieToRoom,
    private val deleteMoviesFromRoom: DeleteMovieFromRoom,
    getMovies: GetMovies
) : ViewModel() {

    private val _error: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    val error: LiveData<String> get() = _error

    private val _cinemaRoom: MutableLiveData<CinemaRoom> by lazy { MutableLiveData<CinemaRoom>() }
    val cinemaRoom: LiveData<CinemaRoom> get() = _cinemaRoom

    private val _ownerMovies: MutableLiveData<MoviesList> by lazy { MutableLiveData<MoviesList>() }
    val ownerMovies: LiveData<MoviesList> get() = _ownerMovies

    private val _moviesList: MutableLiveData<MoviesList> by lazy { MutableLiveData<MoviesList>() }
    val moviesList: LiveData<MoviesList> get() = _moviesList

    init {
        try {
            _moviesList.value = getMovies()
        } catch (e: Exception) {
            _error.value = e.message
        }
    }

    fun getRoom(roomId: Int) {
        try {
            val room = getCinemaRoom(roomId)
            _cinemaRoom.value = room
        } catch (e: Exception) {
            _error.value = e.message
        }
    }

    fun getMoviesInRoom(roomId: Int) {
        try {
            val movies = getMoviesByRoom(roomId)
            _ownerMovies.value = movies
        } catch (e: Exception) {
            _error.value = e.message
        }
    }

}
