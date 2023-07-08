package com.uade.daitp.client.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.uade.daitp.R
import com.uade.daitp.client.presentation.adapters.ReservationsAdapter
import com.uade.daitp.databinding.FragmentClientHomeReservationsBinding
import com.uade.daitp.module.di.ViewModelDI
import com.uade.daitp.presentation.util.setOnClickListenerWithThrottle

class ClientHomeReservationsFragment(private val listener: ReservationsListener) :
    Fragment(R.layout.fragment_client_home_reservations) {

    private val viewModel = ViewModelDI.getHomeReservationsViewModel()
    private lateinit var binding: FragmentClientHomeReservationsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentClientHomeReservationsBinding.bind(view)

        binding.reservationsEmptyButton.setOnClickListenerWithThrottle {
            listener.onBookReservation()
        }

        val recyclerView = binding.reservationsList
        recyclerView.layoutManager = LinearLayoutManager(context)
        viewModel.reservations.observe(viewLifecycleOwner) {
            recyclerView.adapter = ReservationsAdapter(it)

            binding.reservationsEmpty.visibility = if (it.isEmpty()) View.VISIBLE else View.GONE
        }
    }

    override fun onResume() {
        super.onResume()

        viewModel.refreshData()
    }

    interface ReservationsListener {
        fun onBookReservation()
    }

    companion object {
        const val RESERVATION_ID = "reservation_id"
    }
}