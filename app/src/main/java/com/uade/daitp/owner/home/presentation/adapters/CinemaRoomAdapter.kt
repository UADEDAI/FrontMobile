package com.uade.daitp.owner.home.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.uade.daitp.databinding.ListItemRoomBinding
import com.uade.daitp.owner.home.core.models.CinemaRoom
import com.uade.daitp.owner.home.presentation.OwnerCinemaViewModel
import com.uade.daitp.presentation.util.setOnClickListenerWithThrottle

class CinemaRoomAdapter(
    private val cinemaRooms: List<CinemaRoom>,
    private val viewModel: OwnerCinemaViewModel
) : RecyclerView.Adapter<CinemaRoomAdapter.ViewHolder>() {

    private val views = mutableListOf<View>()

    class ViewHolder(
        private val binding: ListItemRoomBinding,
        private val viewModel: OwnerCinemaViewModel,
        private val onItemSelected: () -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(cinemaRoom: CinemaRoom) {
            binding.itemRoomTitle.text = cinemaRoom.name
            binding.itemRoomCapacity.text = "15 Movies"
            //TODO

            binding.root.setOnClickListenerWithThrottle {
                viewModel.onCinemaRoomSelected(cinemaRoom)
                onItemSelected()
                it.isActivated = true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ListItemRoomBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        views.add(binding.root)
        return ViewHolder(binding, viewModel, this::onItemSelected)
    }

    override fun getItemCount() = cinemaRooms.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(cinemaRooms[position])
    }

    private fun onItemSelected() {
        views.forEach { view -> view.isActivated = false }
    }
}