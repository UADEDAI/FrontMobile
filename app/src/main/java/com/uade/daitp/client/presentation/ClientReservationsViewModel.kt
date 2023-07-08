package com.uade.daitp.client.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.uade.daitp.owner.home.core.actions.GetMovie
import com.uade.daitp.owner.home.core.models.Movie
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ClientReservationsViewModel(
//    private val getMovie: GetMovie
) : ViewModel() {

    private val _error: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    val error: LiveData<String> get() = _error

    private val _movie: MutableLiveData<Movie> by lazy { MutableLiveData<Movie>() }
    val movie: LiveData<Movie> get() = _movie

    fun getMovieBy(movieId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
//                val movie = getMovie(movieId)
//                _movie.postValue(movie)
            } catch (e: Exception) {
                _error.postValue(e.message)
            }
        }
    }
}
