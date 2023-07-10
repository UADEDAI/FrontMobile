package com.uade.daitp.client.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.uade.daitp.client.core.actions.CreateReservation
import com.uade.daitp.client.core.actions.GetAvailableSeats
import com.uade.daitp.client.core.model.*
import com.uade.daitp.client.infrastructure.repository.LocalReservationRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ClientMovieSeatingViewModel(
    private val getAvailableSeats: GetAvailableSeats,
    private val createReservation: CreateReservation,
    private val reservationRepository: LocalReservationRepository
) : ViewModel() {

    private val _error: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    val error: LiveData<String> get() = _error

    private val _availableSeats: MutableLiveData<List<AvailableSeat>> by lazy { MutableLiveData<List<AvailableSeat>>() }
    val availableSeats: LiveData<List<AvailableSeat>> get() = _availableSeats

    private val _success: MutableLiveData<Reservation> by lazy { MutableLiveData<Reservation>() }
    val success: LiveData<Reservation> get() = _success

    init {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val availableSeats = getAvailableSeats(reservationRepository.screeningClient.id)
                _availableSeats.postValue(availableSeats)
            } catch (e: Exception) {
                _error.postValue(e.message)
            }
        }
    }

    fun getScreening(): ScreeningClient {
        return reservationRepository.screeningClient
    }

    fun getDate(): Long {
        return reservationRepository.dateInMillis
    }

    fun getTicketsAmount(): Int {
        return reservationRepository.ticketsNumber
    }

    fun reserveMovie(selectedSeats: MutableList<SeatViewData>, time: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val reservationIntent = ReservationIntent(
                    -1,
                    reservationRepository.screeningClient.id,
                    selectedSeats.map { seatViewData -> seatViewData.availableSeat!!.id },
                    time
                )
                val reservation = createReservation(reservationIntent)
                _success.postValue(reservation)
            } catch (e: Exception) {
                _error.postValue(e.message)
            }
        }
    }

}