package com.uade.daitp.client.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.uade.daitp.R
import com.uade.daitp.databinding.FragmentClientHomeReservationsBinding
import com.uade.daitp.databinding.FragmentClientMovieScreeningsBinding
import com.uade.daitp.presentation.util.setOnClickListenerWithThrottle

class ClientScreeningListFragment : Fragment(R.layout.fragment_client_movie_screenings) {

    private lateinit var binding: FragmentClientMovieScreeningsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentClientMovieScreeningsBinding.bind(view)

        val movieId = arguments?.getInt(ClientHomeMoviesListFragment.MOVIE_ID)
        val cinemaId = arguments?.getInt(ClientHomeMoviesListFragment.CINEMA_ID)

        binding.homeCinemaAdd.setOnClickListenerWithThrottle {
            view.findNavController()
//                .navigate(R.id.action_clientHomeFragment_to_clientMoviePagerFragment)
        }
    }

}