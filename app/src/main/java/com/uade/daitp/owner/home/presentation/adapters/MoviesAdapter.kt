package com.uade.daitp.owner.home.presentation.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.uade.daitp.databinding.ListItemMovieBinding
import com.uade.daitp.owner.home.core.models.Movie
import com.uade.daitp.presentation.util.setOnClickListenerWithThrottle
import java.util.concurrent.TimeUnit

class MoviesAdapter(private val movies: List<Movie>) :
    RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {

    private val views = mutableListOf<View>()

    class ViewHolder(
        private val binding: ListItemMovieBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {
            binding.itemMovieTitle.text = movie.title
            binding.itemMovieDuration.text = formatMinutes(movie.duration)
//            Glide.with(itemView.context)
//                .load(movie.imageUrl)
//                .into(binding.itemMovieThumbnail);

            Log.d("TESTYTEST", movie.imageUrl)

            binding.root.setOnClickListenerWithThrottle {
                it.isActivated = !it.isActivated
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
        return ViewHolder(binding)
    }

    override fun getItemCount() = movies.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(movies[position])
    }

}