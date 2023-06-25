package com.uade.daitp.owner.home.presentation.adapters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.uade.daitp.R
import com.uade.daitp.databinding.ListItemCinemaBinding
import com.uade.daitp.owner.home.core.models.Cinema
import com.uade.daitp.owner.home.presentation.OwnerCinemaFormFragment
import com.uade.daitp.owner.home.presentation.OwnerCinemaFragment
import com.uade.daitp.owner.home.presentation.OwnerCinemasListViewModel
import com.uade.daitp.presentation.util.setOnClickListenerWithThrottle


class CinemaAdapter(private val cinemas: List<Cinema>, private val viewModel: OwnerCinemasListViewModel) :
    RecyclerView.Adapter<CinemaAdapter.ViewHolder>() {

    class ViewHolder(
        private val binding: ListItemCinemaBinding,
        private val viewModel: OwnerCinemasListViewModel
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(cinema: Cinema) {
            binding.itemCinemaTitle.text = cinema.name
            binding.itemCinemaLocation.text = cinema.getAddress()
            binding.itemCinemaMovies.text =
                itemView.resources.getString(R.string.cinema_movies_amount, cinema.moviesAmount)
            binding.itemCinemaRoom.text =
                itemView.resources.getString(R.string.cinema_rooms_amount, cinema.roomsAmount)

            binding.itemCinemaDeleteButton.setOnClickListenerWithThrottle {
                MaterialAlertDialogBuilder(itemView.context)
                    .setMessage(itemView.resources.getString(R.string.cinema_dialog_text))
                    .setNegativeButton(itemView.resources.getString(R.string.decline)) { _, _ -> }
                    .setPositiveButton(itemView.resources.getString(R.string.accept)) { _, _ ->
                        viewModel.delete(cinema)
                    }
                    .show()
            }

            binding.itemCinemaEditButton.setOnClickListenerWithThrottle {
                val bundle = Bundle()
                bundle.putInt(OwnerCinemaFormFragment.CINEMA_ID, cinema.id)
                itemView.findNavController()
                    .navigate(R.id.action_ownerHomeFragment_to_ownerCinemaFormFragment, bundle)
            }

            binding.root.setOnClickListenerWithThrottle {
                val bundle = Bundle()
                bundle.putInt(OwnerCinemaFragment.CINEMA_ID, cinema.id)
                itemView.findNavController()
                    .navigate(R.id.action_ownerHomeFragment_to_ownerCinemaFragment, bundle)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ListItemCinemaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, viewModel)
    }

    override fun getItemCount() = cinemas.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(cinemas[position])
    }

}