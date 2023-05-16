package com.uade.daitp.owner.home.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.uade.daitp.owner.home.core.actions.GetCinema
import com.uade.daitp.owner.home.core.models.Cinema

class OwnerCinemaViewModel(
    private val getCinema: GetCinema
) : ViewModel() {

    private val _error: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    val error: LiveData<String> get() = _error

    private val _cinema: MutableLiveData<Cinema> by lazy { MutableLiveData<Cinema>() }
    val cinema: LiveData<Cinema> get() = _cinema

    fun getCinemaById(cinemaId: Int) {
        try {
            val cinema = getCinema(cinemaId)
            _cinema.value = cinema
        } catch (e: Exception) {
            _error.value = e.message
        }
    }
}
