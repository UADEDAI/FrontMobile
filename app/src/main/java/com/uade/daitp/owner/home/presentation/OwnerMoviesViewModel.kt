package com.uade.daitp.owner.home.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.uade.daitp.owner.home.core.actions.*
import com.uade.daitp.owner.home.core.models.*

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

    private var roomId = -1

    init {
        try {
            _moviesList.value = getMovies()
        } catch (e: Exception) {
            _error.value = e.message
        }
    }

    fun getRoom(roomId: Int) {
        this.roomId = roomId
        try {
            val room = getCinemaRoom(roomId)
            _cinemaRoom.value = room
        } catch (e: Exception) {
            _error.value = e.message
        }
    }

    fun getMoviesInRoom(roomId: Int) {
        this.roomId = roomId
        try {
            val movies = getMoviesByRoom(roomId)
            _ownerMovies.postValue(movies)
        } catch (e: Exception) {
            _ownerMovies.postValue(emptyMovieList())
        }
    }

    fun addMovie(movie: Movie) {
        if (movie.isShowing()) {
            if (!_ownerMovies.value!!.showing.contains(movie)) {
                _ownerMovies.value!!.showing.add(movie)
                addMoviesToRoom(roomId, movie)
            }
        } else {
            if (!_ownerMovies.value!!.comingSoon.contains(movie)) {
                _ownerMovies.value!!.comingSoon.add(movie)
                addMoviesToRoom(roomId, movie)
            }
        }
        _ownerMovies.postValue(_ownerMovies.value)
    }

    fun deleteMovie(movie: Movie) {
        if (movie.isShowing()) {
            if (_ownerMovies.value!!.showing.contains(movie)) {
                _ownerMovies.value!!.showing.remove(movie)
                deleteMoviesFromRoom(roomId, movie.id)
            }
        } else {
            if (_ownerMovies.value!!.comingSoon.contains(movie)) {
                _ownerMovies.value!!.comingSoon.remove(movie)
                deleteMoviesFromRoom(roomId, movie.id)
            }
        }
        _ownerMovies.postValue(_ownerMovies.value)
    }
}
