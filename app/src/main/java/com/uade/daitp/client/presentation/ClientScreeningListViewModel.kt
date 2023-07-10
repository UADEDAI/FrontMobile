package com.uade.daitp.client.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.uade.daitp.client.core.actions.GetScreeningsClientBy
import com.uade.daitp.client.core.model.ScreeningClient
import com.uade.daitp.client.infrastructure.repository.LocalReservationRepository
import com.uade.daitp.owner.home.core.actions.GetCinema
import com.uade.daitp.owner.home.core.models.Cinema
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class ClientScreeningListViewModel(
    private val getScreeningsClientBy: GetScreeningsClientBy,
    private val getCinema: GetCinema,
    private val reservationRepository: LocalReservationRepository
) : ViewModel() {

    private val _error: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    val error: LiveData<String> get() = _error

    private val _screenings: MutableLiveData<List<ScreeningClient>> by lazy { MutableLiveData<List<ScreeningClient>>() }
    val screenings: LiveData<List<ScreeningClient>> get() = _screenings

    private val _cinema: MutableLiveData<Cinema> by lazy { MutableLiveData<Cinema>() }
    val cinema: LiveData<Cinema> get() = _cinema

    private var movieId: Int = 0
    private var cinemaId: Int = 0

    fun saveData(movieId: Int, cinemaId: Int) {
        this.movieId = movieId
        this.cinemaId = cinemaId
    }

    fun getScreenings(date: Date) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val screenings = getScreeningsClientBy(cinemaId, movieId, date)
                _screenings.postValue(screenings)
            } catch (e: Exception) {
                _error.postValue(e.message)
            }
        }
    }

    fun getCinemaBy(cinemaId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val cinema = getCinema(cinemaId)
                _cinema.postValue(cinema)
            } catch (e: Exception) {
                _error.postValue(e.message)
            }
        }
    }

    fun getTickets(ticketsNumber: Int, dateInMillis: Long, screeningClient: ScreeningClient) {
        reservationRepository.saveReservationRepository(ticketsNumber, dateInMillis, screeningClient)
    }

}