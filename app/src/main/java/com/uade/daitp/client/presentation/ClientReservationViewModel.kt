package com.uade.daitp.client.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.uade.daitp.client.core.actions.GetReservationById
import com.uade.daitp.client.core.model.Reservation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ClientReservationViewModel(
    private val getReservationById: GetReservationById
) : ViewModel() {

    private val _error: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    val error: LiveData<String> get() = _error

    private val _reservation: MutableLiveData<Reservation> by lazy { MutableLiveData<Reservation>() }
    val reservation: LiveData<Reservation> get() = _reservation

    fun getReservationBy(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val reservation = getReservationById(id)
                _reservation.postValue(reservation)
            } catch (e: Exception) {
                _error.postValue(e.message)
            }
        }
    }
}
