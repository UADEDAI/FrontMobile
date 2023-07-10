package com.uade.daitp.client.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.uade.daitp.R
import com.uade.daitp.client.presentation.adapters.ClientMovieAdapter
import com.uade.daitp.client.presentation.adapters.SeatsAdapter
import com.uade.daitp.databinding.FragmentClientMovieSeatingBinding
import com.uade.daitp.module.di.ViewModelDI
import com.uade.daitp.owner.home.core.models.emptyMovieList

class ClientMovieSeatingFragment : Fragment(R.layout.fragment_client_movie_seating) {

    private val viewModel = ViewModelDI.getClientSeatingViewModel()
    private lateinit var binding: FragmentClientMovieSeatingBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentClientMovieSeatingBinding.bind(view)

        val screening = viewModel.getScreening()

        val recyclerView = binding.seatsGrid
        recyclerView.layoutManager = GridLayoutManager(context, screening.room.seats)
        recyclerView.adapter = SeatsAdapter(emptyMovieList())
    }

}