package com.uade.daitp.client.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.uade.daitp.client.core.actions.GetFilteredMovies
import com.uade.daitp.client.core.actions.GetNearCinemaForMovie
import com.uade.daitp.owner.home.core.models.Cinema
import com.uade.daitp.owner.home.core.models.MoviesList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ClientHomeMoviesViewModel(
    private val getFilteredMovies: GetFilteredMovies,
    private val getNearCinemasForMovie: GetNearCinemaForMovie
) : ViewModel() {

    private val _error: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    val error: LiveData<String> get() = _error

    private val _filteredMovies: MutableLiveData<MoviesList> by lazy { MutableLiveData<MoviesList>() }
    val filteredMovies: LiveData<MoviesList> get() = _filteredMovies

    private val _nearCinemas: MutableLiveData<List<Cinema>> by lazy { MutableLiveData<List<Cinema>>() }
    val nearCinemas: LiveData<List<Cinema>> get() = _nearCinemas

    fun refreshData() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
//                val reservations = getReservations()
//                _reservations.postValue(reservations)
            } catch (e: Exception) {
                _error.postValue(e.message)
            }
        }
    }
}
