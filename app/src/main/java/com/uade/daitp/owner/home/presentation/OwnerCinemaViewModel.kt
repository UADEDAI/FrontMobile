package com.uade.daitp.owner.home.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.uade.daitp.owner.home.core.actions.GetCinema
import com.uade.daitp.owner.home.core.actions.GetCinemaRooms
import com.uade.daitp.owner.home.core.actions.GetMoviesByRoom
import com.uade.daitp.owner.home.core.models.*

class OwnerCinemaViewModel(
    private val getCinema: GetCinema,
    private val getCinemaRooms: GetCinemaRooms,
    private val getMoviesByRoom: GetMoviesByRoom
) : ViewModel() {

    private val _error: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    val error: LiveData<String> get() = _error

    private val _cinema: MutableLiveData<Cinema> by lazy { MutableLiveData<Cinema>() }
    val cinema: LiveData<Cinema> get() = _cinema

    private val _cinemaRooms: MutableLiveData<List<CinemaRoom>> by lazy { MutableLiveData<List<CinemaRoom>>() }
    val cinemaRooms: LiveData<List<CinemaRoom>> get() = _cinemaRooms

    private val _selectedCinemaRoom: MutableLiveData<CinemaRoom> by lazy { MutableLiveData<CinemaRoom>() }
    val selectedCinemaRoom: LiveData<CinemaRoom> get() = _selectedCinemaRoom

    private val _selectedRoomMovies: MutableLiveData<MoviesList> by lazy { MutableLiveData<MoviesList>() }
    val selectedRoomMovies: LiveData<MoviesList> get() = _selectedRoomMovies

    fun getCinemaBy(cinemaId: Int) {
        try {
            val cinema = getCinema(cinemaId)
            _cinema.value = cinema
        } catch (e: Exception) {
            _error.value = e.message
        }
    }

    fun getCinemaRoomsById(cinemaId: Int) {
        try {
            val cinemaRooms = getCinemaRooms(cinemaId)
            _cinemaRooms.value = cinemaRooms
        } catch (e: Exception) {
            _error.value = e.message
        }
    }

    fun onCinemaRoomSelected(cinemaRoom: CinemaRoom) {
        _selectedCinemaRoom.value = cinemaRoom
    }

    fun getRoomMoviesBy(roomId: Int) {
        try {
            val movies = getMoviesByRoom(roomId)
            _selectedRoomMovies.postValue(movies)
        } catch (e: Exception) {
            _selectedRoomMovies.postValue(emptyMovieList())
        }
    }
}
