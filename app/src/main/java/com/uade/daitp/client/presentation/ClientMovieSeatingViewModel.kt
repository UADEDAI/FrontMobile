package com.uade.daitp.client.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.uade.daitp.client.core.actions.CreateReservation
import com.uade.daitp.client.core.model.ScreeningClient
import com.uade.daitp.client.infrastructure.repository.LocalReservationRepository

class ClientMovieSeatingViewModel(
    private val createReservation: CreateReservation,
    private val reservationRepository: LocalReservationRepository
) : ViewModel() {

    private val _error: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    val error: LiveData<String> get() = _error

    fun getScreening(): ScreeningClient {
        return reservationRepository.screeningClient
    }

    fun getDate(): Long {
        return reservationRepository.dateInMillis
    }

    fun getTicketsAmount(): Int {
        return reservationRepository.ticketsNumber
    }

}