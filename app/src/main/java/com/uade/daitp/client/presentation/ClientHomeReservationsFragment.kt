package com.uade.daitp.client.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.uade.daitp.R
import com.uade.daitp.databinding.FragmentClientHomeMoviesBinding

class ClientHomeReservationsFragment : Fragment(R.layout.fragment_client_home_movies) {

    private lateinit var binding: FragmentClientHomeMoviesBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentClientHomeMoviesBinding.bind(view)

    }

}