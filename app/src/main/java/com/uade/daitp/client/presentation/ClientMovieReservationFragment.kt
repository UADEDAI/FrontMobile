package com.uade.daitp.client.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.uade.daitp.R
import com.uade.daitp.client.presentation.ClientHomeReservationsFragment.Companion.RESERVATION_ID
import com.uade.daitp.databinding.FragmentClientMovieReservationBinding
import com.uade.daitp.module.di.ViewModelDI
import com.uade.daitp.presentation.util.errorDialog
import com.uade.daitp.presentation.util.setOnClickListenerWithThrottle
import java.text.SimpleDateFormat
import java.util.*

class ClientMovieReservationFragment : Fragment(R.layout.fragment_client_movie_reservation) {

    private val viewModel = ViewModelDI.getReservationViewModel()
    private lateinit var binding: FragmentClientMovieReservationBinding

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentClientMovieReservationBinding.bind(view)

        val reservationId = arguments?.getInt(RESERVATION_ID)
        reservationId?.let { id ->
            viewModel.getReservationBy(id)
        }

        binding.reservationBack.setOnClickListenerWithThrottle {
            view.findNavController().popBackStack()
        }

        viewModel.reservation.observe(viewLifecycleOwner) { reservation ->
            Glide.with(requireContext())
                .load(reservation.screening.movie.imageUrl)
                .into(binding.reservationImage)
            binding.movieCinema.text = reservation.screening.room.cinema.name
            binding.movieRoom.text = reservation.screening.room.name
            binding.movieDate.text = toFriendlyDateString(reservation.screening.startAt)
            binding.movieFormat.text = when (reservation.screening.format) {
                "subtitled" ->
                    getString(R.string.subtitled)
                "dubbed" ->
                    getString(R.string.dubbed)
                else ->
                    getString(R.string.original)
            }
            val seats = reservation.seats
            var seatString = ""
            seats?.forEach { seat ->
                seatString += "${getString(R.string.row)}${seat.row}C${seat.number}; "
            }
            binding.movieSeats.text = seatString
            binding.moviePrice.text = "\$${reservation.screening.room.cinema.price}"
            binding.movieCode.text = reservation.otp.code
        }

        viewModel.error.observe(viewLifecycleOwner) {
            errorDialog()
        }
    }

    private val dateFormat = SimpleDateFormat("dd MMMM yyyy, HH:mm", Locale.getDefault())

    private fun toFriendlyDateString(date: Date): String {
        return dateFormat.format(date)
    }

}