package com.uade.daitp.owner.home.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.uade.daitp.owner.home.core.actions.DeleteCinema
import com.uade.daitp.owner.home.core.actions.GetCinemas
import com.uade.daitp.owner.home.core.models.Cinema
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class OwnerCinemasListViewModel(
    private val getCinemas: GetCinemas,
    private val deleteCinema: DeleteCinema
) : ViewModel() {

    private val _cinemas: MutableLiveData<List<Cinema>> by lazy { MutableLiveData<List<Cinema>>() }
    val cinemas: LiveData<List<Cinema>> get() = _cinemas

    fun refresh() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                _cinemas.postValue(getCinemas())
            } catch (e: Exception) {
                Log.e("OwnerHomeViewModel", "refresh error", e)
            }
        }
    }

    fun delete(cinema: Cinema) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                deleteCinema(cinemaId = cinema.id)
            } catch (e: Exception) {
                Log.e("OwnerHomeViewModel", "delete error", e)
            }
            refresh()
        }
    }

}