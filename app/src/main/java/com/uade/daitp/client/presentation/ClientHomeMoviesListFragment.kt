package com.uade.daitp.client.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.uade.daitp.R
import com.uade.daitp.databinding.FragmentClientHomeReservationsBinding
import com.uade.daitp.presentation.util.setOnClickListenerWithThrottle

class ClientHomeMoviesListFragment : Fragment(R.layout.fragment_client_home_reservations) {

    private lateinit var binding: FragmentClientHomeReservationsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentClientHomeReservationsBinding.bind(view)

        binding.homeCinemaAdd.setOnClickListenerWithThrottle {
            view.findNavController()
                .navigate(R.id.action_clientHomeFragment_to_clientMoviePagerFragment)
        }
    }

}