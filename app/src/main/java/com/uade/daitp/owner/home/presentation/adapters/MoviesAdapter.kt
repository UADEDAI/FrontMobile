package com.uade.daitp.owner.home.presentation.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.uade.daitp.databinding.ListItemMovieBinding
import com.uade.daitp.owner.home.core.models.Movie
import com.uade.daitp.owner.home.core.models.MoviesList
import com.uade.daitp.presentation.util.setOnClickListenerWithThrottle
import java.util.concurrent.TimeUnit

@SuppressLint("NotifyDataSetChanged")
class MoviesAdapter(
    private var movies: MoviesList,
    private val multipleSelectionEnabled: Boolean,
    private val showAllMovies: Boolean
) :
    RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {

    private val views = mutableListOf<View>()
    private val visibleMovies = mutableListOf<Movie>()
    private var showingMovies = true

    private val _selectedMovies: MutableLiveData<MutableList<Movie>> by lazy { MutableLiveData<MutableList<Movie>>() }
    val selectedMovies: LiveData<MutableList<Movie>> get() = _selectedMovies

    init {
        _selectedMovies.postValue(mutableListOf())
        visibleMovies.addAll(movies.showing)
        if (showAllMovies) visibleMovies.addAll(movies.comingSoon)
        notifyDataSetChanged()
    }

    class ViewHolder(
        private val binding: ListItemMovieBinding,
        private val multipleSelectionEnabled: Boolean,
        private val resetSelection: () -> Unit,
        private val selectedMovies: MutableLiveData<MutableList<Movie>>
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {
            binding.itemMovieTitle.text = movie.title
            binding.itemMovieDuration.text = formatMinutes(movie.duration)
            Glide.with(itemView.context)
                .load(movie.imageUrl)
                .into(binding.itemMovieThumbnail);

            binding.root.setOnClickListenerWithThrottle(periodInMillis = 500L) {
                val isActivated = it.isActivated
                val selected = selectedMovies.value!!

                if (!multipleSelectionEnabled) {
                    resetSelection()
                    selectedMovies.value!!.clear()
                }
                it.isActivated = !isActivated

                if (it.isActivated) {
                    selected.add(movie)
                } else {
                    selected.remove(movie)
                }
                selectedMovies.postValue(selected)
            }
        }

        private fun formatMinutes(minutes: Int): String {
            val hours = TimeUnit.MINUTES.toHours(minutes.toLong())
            val remainingMinutes = minutes - TimeUnit.HOURS.toMinutes(hours)

            return String.format("%02d Hr %02d Min", hours, remainingMinutes)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ListItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        views.add(binding.root)
        return ViewHolder(binding, multipleSelectionEnabled, this::resetSelection, _selectedMovies)
    }

    override fun getItemCount() = visibleMovies.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(visibleMovies[position])
    }

    fun updateData(data: MoviesList) {
        movies = data
        resetData()
    }

    fun resetSelection() {
        views.forEach { it.isActivated = false }
        _selectedMovies.postValue(mutableListOf())
    }

    fun toggleMoviesType() {
        showingMovies = !showingMovies
        resetData()
    }

    private fun resetData() {
        resetSelection()
        visibleMovies.clear()

        if (showingMovies) {
            visibleMovies.addAll(movies.showing)
            if (showAllMovies)
                visibleMovies.addAll(movies.comingSoon)
        } else {
            visibleMovies.addAll(movies.comingSoon)
            if (showAllMovies)
                visibleMovies.addAll(movies.showing)
        }

        notifyDataSetChanged()
    }

}