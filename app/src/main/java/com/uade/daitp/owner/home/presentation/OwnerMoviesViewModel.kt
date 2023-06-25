package com.uade.daitp.owner.home.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.uade.daitp.owner.home.core.actions.*
import com.uade.daitp.owner.home.core.models.CinemaRoom
import com.uade.daitp.owner.home.core.models.Movie
import com.uade.daitp.owner.home.core.models.MoviesList
import com.uade.daitp.owner.home.core.models.emptyMovieList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val movies = getMovies()
                _moviesList.postValue(movies)
            } catch (e: Exception) {
                Log.e("OwnerMoviesViewModel", "get movies error", e)
                _error.postValue(e.message)
            }
        }
    }

    fun getRoom(roomId: Int) {
        this.roomId = roomId
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val room = getCinemaRoom(roomId)
                _cinemaRoom.postValue(room)
            } catch (e: Exception) {
                _error.postValue(e.message)
            }
        }
    }

    fun getMoviesInRoom(roomId: Int) {
        this.roomId = roomId
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val movies = getMoviesByRoom(roomId)
                _ownerMovies.postValue(movies)
            } catch (e: Exception) {
                _ownerMovies.postValue(emptyMovieList())
            }
        }
    }

    fun addMovie(movie: Movie) {
        if (movie.isShowing()) {
            if (!_ownerMovies.value!!.showing.contains(movie)) {
                _ownerMovies.value!!.showing.add(movie)
                CoroutineScope(Dispatchers.IO).launch {
                    addMoviesToRoom(roomId, movie)
                }
            }
        } else {
            if (!_ownerMovies.value!!.comingSoon.contains(movie)) {
                _ownerMovies.value!!.comingSoon.add(movie)
                CoroutineScope(Dispatchers.IO).launch {
                    addMoviesToRoom(roomId, movie)
                }
            }
        }
        _ownerMovies.postValue(_ownerMovies.value)
    }

    fun deleteMovie(movie: Movie) {
        if (movie.isShowing()) {
            if (_ownerMovies.value!!.showing.contains(movie)) {
                _ownerMovies.value!!.showing.remove(movie)
                CoroutineScope(Dispatchers.IO).launch {
                    deleteMoviesFromRoom(roomId, movie.id)
                }
            }
        } else {
            if (_ownerMovies.value!!.comingSoon.contains(movie)) {
                _ownerMovies.value!!.comingSoon.remove(movie)
                CoroutineScope(Dispatchers.IO).launch {
                    deleteMoviesFromRoom(roomId, movie.id)
                }
            }
        }
        _ownerMovies.postValue(_ownerMovies.value)
    }
}
