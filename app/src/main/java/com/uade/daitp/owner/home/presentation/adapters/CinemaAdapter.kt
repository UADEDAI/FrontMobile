package com.uade.daitp.owner.home.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.uade.daitp.databinding.ListItemCinemaBinding
import com.uade.daitp.owner.home.core.models.Cinema

class CinemaAdapter(private val cinemas: List<Cinema>) :
    RecyclerView.Adapter<CinemaAdapter.ViewHolder>() {

    class ViewHolder(private val binding: ListItemCinemaBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(cinema: Cinema) {
            binding.itemCinemaTitle.text = cinema.name
            binding.itemCinemaMovies.text = "5 Movies"
            binding.itemCinemaRoom.text = "5 Rooms"
            //TODO
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ListItemCinemaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = cinemas.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(cinemas[position])
    }

}