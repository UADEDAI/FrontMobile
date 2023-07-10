package com.uade.daitp.client.presentation.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.uade.daitp.databinding.ListItemMovieBookingBinding
import com.uade.daitp.owner.home.core.models.Movie
import com.uade.daitp.owner.home.core.models.MoviesList
import com.uade.daitp.presentation.util.setOnClickListenerWithThrottle

class SeatsAdapter(
    private val moviesList: MoviesList
) : RecyclerView.Adapter<SeatsAdapter.ViewHolder>() {

    private val _selectedMovie: MutableLiveData<Movie> by lazy { MutableLiveData<Movie>() }
    val selectedMovie: LiveData<Movie> get() = _selectedMovie

    class ViewHolder(
        private val binding: ListItemMovieBookingBinding,
        private val selectedMovie: MutableLiveData<Movie>
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {
            Glide.with(itemView.context)
                .load(movie.imageUrl)
                .into(binding.itemMovieThumbnail)
            binding.itemMovieTitle.text = movie.title
            binding.itemMovieGenre.text = movie.genre
            binding.itemMovieScore.text = movie.score.toString()

            binding.root.setOnClickListenerWithThrottle {
                selectedMovie.postValue(movie)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ListItemMovieBookingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, _selectedMovie)
    }

    override fun getItemCount() = moviesList.showing.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(moviesList.showing[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun refreshMovies(moviesList: MoviesList) {
        this.moviesList.showing.clear()
        this.moviesList.showing.addAll(moviesList.showing)
        notifyDataSetChanged()
    }
}