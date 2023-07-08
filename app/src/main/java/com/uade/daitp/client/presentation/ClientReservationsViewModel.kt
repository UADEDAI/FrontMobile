package com.uade.daitp.client.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.uade.daitp.client.core.actions.GetReservations
import com.uade.daitp.client.core.model.Reservation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ClientReservationsViewModel(
    private val getReservations: GetReservations
) : ViewModel() {

    private val _error: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    val error: LiveData<String> get() = _error

    private val _reservations: MutableLiveData<List<Reservation>> by lazy { MutableLiveData<List<Reservation>>() }
    val reservations: LiveData<List<Reservation>> get() = _reservations

    fun refreshData() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val reservations = getReservations()
                _reservations.postValue(reservations)
            } catch (e: Exception) {
                _error.postValue(e.message)
            }
        }
    }
}
