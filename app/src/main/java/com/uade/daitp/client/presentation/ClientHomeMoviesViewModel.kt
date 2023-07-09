package com.uade.daitp.client.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.uade.daitp.client.core.actions.GetFilteredMovies
import com.uade.daitp.client.core.actions.GetNearCinemaForMovie
import com.uade.daitp.client.core.model.MoviesIntent
import com.uade.daitp.owner.home.core.models.Cinema
import com.uade.daitp.owner.home.core.models.Movie
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

    private val _selectedMovie: MutableLiveData<Movie> by lazy { MutableLiveData<Movie>() }
    val selectedMovie: LiveData<Movie> get() = _selectedMovie

    private var latitude: Double = 0.0
    private var longitude: Double = 0.0
    private var distance: Double = defaultDistance
    private var title: String? = null
    private var genre: String? = null
    private var score: Double? = null

    fun setLocation(latitude: Double, longitude: Double) {
        this.latitude = latitude
        this.longitude = longitude

        refreshData()
    }

    fun search(title: String?) {
        if (title.isNullOrEmpty()) {
            this.title = null
        } else {
            this.title = title
        }

        refreshData()
    }

    fun setFilters(distance: Double, genre: String?, score: Double?) {
        this.distance = distance

        if (genre.isNullOrEmpty()) {
            this.genre = null
        } else {
            this.genre = genre
        }

        if (score == null || score == 0.0) {
            this.score = null
        } else {
            this.score = score
        }

        refreshData()
    }

    fun refreshData() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val moviesIntent = MoviesIntent(distance, latitude, longitude, title, genre, score)
                val movies = getFilteredMovies(moviesIntent)
                _filteredMovies.postValue(movies)
            } catch (e: Exception) {
                _error.postValue(e.message)
            }
        }
    }

    fun getCinemas(movie: Movie) {
        _selectedMovie.value = movie
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val cinemas = getNearCinemasForMovie(latitude, longitude, distance, movie.id)
                _nearCinemas.postValue(cinemas)
            } catch (e: Exception) {
                _error.postValue(e.message)
            }
        }
    }

    fun clearFilters() {
        title = null
        distance = defaultDistance
        genre = null
        score = null

        refreshData()
    }

    private companion object {
        const val defaultDistance = 10000.0
    }
}
