package com.uade.daitp.owner.home.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.uade.daitp.owner.home.core.actions.DeleteCinema
import com.uade.daitp.owner.home.core.actions.GetCinemas
import com.uade.daitp.owner.home.core.models.Cinema

class OwnerHomeViewModel(private val getCinemas: GetCinemas, private val deleteCinema: DeleteCinema) : ViewModel() {

    private val _cinemas: MutableLiveData<List<Cinema>> by lazy { MutableLiveData<List<Cinema>>() }
    val cinemas: LiveData<List<Cinema>> get() = _cinemas

    fun refresh() {
        _cinemas.value = getCinemas()
    }

    fun delete(cinema: Cinema) {
        deleteCinema(cinemaId = cinema.id)
        refresh()
    }

}