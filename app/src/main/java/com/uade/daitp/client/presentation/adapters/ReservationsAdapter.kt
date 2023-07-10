package com.uade.daitp.client.presentation.adapters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.uade.daitp.R
import com.uade.daitp.client.core.model.Reservation
import com.uade.daitp.client.presentation.ClientHomeReservationsFragment.Companion.RESERVATION_ID
import com.uade.daitp.databinding.ListItemReservationBinding
import com.uade.daitp.presentation.util.setOnClickListenerWithThrottle
import java.text.SimpleDateFormat
import java.util.*

class ReservationsAdapter(
    private val reservations: List<Reservation>
) :
    RecyclerView.Adapter<ReservationsAdapter.ViewHolder>() {

    class ViewHolder(
        private val binding: ListItemReservationBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(reservation: Reservation) {
            Glide.with(itemView.context)
                .load(reservation.screening.movie.imageUrl)
                .into(binding.itemReservationThumbnail)
            binding.itemReservationTitle.text = reservation.screening.movie.title
            binding.itemReservationDate.text = toFriendlyDateString(reservation.time)
            binding.itemReservationCinema.text = reservation.screening.room.cinema.name

            binding.root.setOnClickListenerWithThrottle {
                val bundle = Bundle()
                bundle.putInt(RESERVATION_ID, reservation.id)
                itemView.findNavController()
                    .navigate(R.id.action_clientHomeFragment_to_clientMovieReservationFragment, bundle)
            }
        }

        private val dateFormat = SimpleDateFormat("dd MMMM yyyy, HH:mm", Locale.getDefault())

        private fun toFriendlyDateString(date: Date): String {
            return dateFormat.format(date)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ListItemReservationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = reservations.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(reservations[position])
    }
}