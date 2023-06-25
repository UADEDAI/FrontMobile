package com.uade.daitp.owner.home.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.uade.daitp.owner.home.core.actions.*
import com.uade.daitp.owner.home.core.models.*
import com.uade.daitp.owner.home.core.models.enums.ScreeningFormat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.sql.Time
import java.text.SimpleDateFormat
import java.util.*

class OwnerCinemaViewModel(
    private val getCinema: GetCinema,
    private val getCinemaRooms: GetCinemaRooms,
    private val getMoviesByRoom: GetMoviesByRoom,
    private val getScreeningsBy: GetScreeningsBy,
    private val getAvailableScreeningsBy: GetAvailableScreeningsBy,
    private val addScreening: AddScreening,
    private val deleteScreening: DeleteScreening
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

    private val _selectedMovieScreenings: MutableLiveData<List<Screening>> by lazy { MutableLiveData<List<Screening>>() }
    val selectedMovieScreenings: LiveData<List<Screening>> get() = _selectedMovieScreenings

    private val _selectedRoomAvailableScreenings: MutableLiveData<List<String>> by lazy { MutableLiveData<List<String>>() }
    val selectedRoomAvailableScreenings: LiveData<List<String>> get() = _selectedRoomAvailableScreenings

    private lateinit var selectedMovie: Movie

    fun getCinemaBy(cinemaId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val cinema = getCinema(cinemaId)
                _cinema.postValue(cinema)
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }

    fun getCinemaRoomsById(cinemaId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val cinemaRooms = getCinemaRooms(cinemaId)
                _cinemaRooms.postValue(cinemaRooms)
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }

    fun onCinemaRoomSelected(cinemaRoom: CinemaRoom) {
        _selectedCinemaRoom.postValue(cinemaRoom)
    }

    fun getRoomMoviesBy(roomId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val movies = getMoviesByRoom(roomId)
                _selectedRoomMovies.postValue(movies)
            } catch (e: Exception) {
                _selectedRoomMovies.postValue(emptyMovieList())
            }
        }
    }

    fun getScreenings(movie: Movie) {
        selectedMovie = movie
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val availableScreenings =
                    getAvailableScreeningsBy(movie.id, _selectedCinemaRoom.value!!.id)
                _selectedRoomAvailableScreenings.postValue(availableScreenings)
            } catch (e: Exception) {
                _selectedRoomAvailableScreenings.postValue(emptyList())
            }
            try {
                val screenings = getScreeningsBy(movie.id, _selectedCinemaRoom.value!!.id)
                _selectedMovieScreenings.postValue(screenings)
            } catch (e: Exception) {
                _selectedMovieScreenings.postValue(emptyList())
            }
        }
    }

    fun addScreeningFrom(movie: Movie, startingTime: String, format: ScreeningFormat) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                addScreening(
                    CreateScreeningIntent(
                        _selectedCinemaRoom.value!!.id,
                        movie.id,
                        format,
                        toDate(startingTime, 0),
                        toDate(startingTime, movie.duration)
                    )
                )

                refreshScreenings()
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }

    fun isSelectedAShowingMovie(movie: Movie): Boolean {
        return movie.isShowing()
    }

    fun deleteScreeningFromMovie(screening: Screening) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                deleteScreening(screening.id)

                refreshScreenings()
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }

    private val timeFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())

    private fun toDate(time: String, additionalMinutes: Int): String {
        val hourAndMinute = time.split(":")
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hourAndMinute[0].toInt())
        calendar.set(Calendar.MINUTE, hourAndMinute[1].toInt())

        calendar.add(Calendar.MINUTE, additionalMinutes)
        return timeFormat.format(calendar.time)
    }

    private fun refreshScreenings() {
        CoroutineScope(Dispatchers.Default).launch {
            delay(300)

            getScreenings(selectedMovie)
        }
    }
}
