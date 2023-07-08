package com.uade.daitp.client.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.uade.daitp.R
import com.uade.daitp.databinding.FragmentClientHomeMoviesBinding
import com.uade.daitp.module.di.ViewModelDI
import com.uade.daitp.presentation.util.setOnClickListenerWithThrottle

class ClientHomeReservationsFragment(private val listener: ReservationsListener) :
    Fragment(R.layout.fragment_client_home_movies) {

    interface ReservationsListener {
        fun onBookReservation()
    }

    private val viewModel = ViewModelDI.getHomeReservationsViewModel()
    private lateinit var binding: FragmentClientHomeMoviesBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentClientHomeMoviesBinding.bind(view)

        binding.reservationsEmptyButton.setOnClickListenerWithThrottle {
            listener.onBookReservation()
        }
    }

}