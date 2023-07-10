package com.uade.daitp.client.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.uade.daitp.R
import com.uade.daitp.client.core.model.AvailableSeat
import com.uade.daitp.client.core.model.ScreeningClient
import com.uade.daitp.client.core.model.SeatViewData
import com.uade.daitp.client.presentation.adapters.SeatsAdapter
import com.uade.daitp.databinding.FragmentClientMovieSeatingBinding
import com.uade.daitp.module.di.ViewModelDI
import com.uade.daitp.presentation.util.errorDialog
import com.uade.daitp.presentation.util.setOnClickListenerWithThrottle
import java.text.SimpleDateFormat
import java.util.*

class ClientMovieSeatingFragment : Fragment(R.layout.fragment_client_movie_seating) {

    private val viewModel = ViewModelDI.getClientSeatingViewModel()
    private lateinit var binding: FragmentClientMovieSeatingBinding

    private val dateFormat = SimpleDateFormat("dd, MMMM", Locale.getDefault())
    private val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
    private val apiFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentClientMovieSeatingBinding.bind(view)

        val calendar = Calendar.getInstance()
        calendar.timeInMillis = viewModel.getDate()

        binding.seatingDate.text = dateFormat.format(calendar.time)
        binding.seatingTime.text = timeFormat.format(calendar.time)

        binding.seatingBack.setOnClickListenerWithThrottle {
            view.findNavController().popBackStack()
        }

        val screening = viewModel.getScreening()

        binding.seatingCinema.text = screening.room.cinema.name
        binding.seatingMovie.text = screening.movie.title

        val recyclerView = binding.seatsGrid
        recyclerView.layoutManager =
            GridLayoutManager(context, screening.room.numRows, GridLayoutManager.HORIZONTAL, false)
        viewModel.availableSeats.observe(viewLifecycleOwner) {
            val seats = createSeats(it, screening)
            recyclerView.adapter = SeatsAdapter(seats, viewModel.getTicketsAmount())
        }

        viewModel.error.observe(viewLifecycleOwner) {
            errorDialog()
        }

        binding.button.setOnClickListener {
            val selectedSeats = (recyclerView.adapter as SeatsAdapter).selectedSeats.value
            if (selectedSeats == null || selectedSeats.size < viewModel.getTicketsAmount()) {
                errorDialog(getString(R.string.missing_seats))
                return@setOnClickListener
            }
            viewModel.reserveMovie(selectedSeats, apiFormat.format(calendar.time))
        }

        viewModel.success.observe(viewLifecycleOwner) {
            val bundle = Bundle()
            bundle.putInt(ClientHomeReservationsFragment.RESERVATION_ID, it.id)
            view.findNavController()
                .navigate(R.id.action_clientMovieSeatingFragment_to_clientMovieReservationFragment, bundle)
        }
    }

    private fun createSeats(
        availableSeats: List<AvailableSeat>,
        screening: ScreeningClient
    ): List<SeatViewData> {
        val list = mutableListOf<SeatViewData>()
        for (column in 1..screening.room.seats) {
            for (row in 1..screening.room.numRows) {
                list.add(
                    SeatViewData(
                        row,
                        column,
                        availableSeats.find { it.row == row && it.number == column })
                )
            }
        }
        return list
    }
}