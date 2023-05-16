package com.uade.daitp.owner.home.presentation

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.uade.daitp.R
import com.uade.daitp.databinding.FragmentOwnerCinemaBinding
import com.uade.daitp.module.di.ViewModelDI
import com.uade.daitp.presentation.util.setOnClickListenerWithThrottle

class OwnerCinemaFragment : Fragment(R.layout.fragment_owner_cinema) {

    private val viewModel = ViewModelDI.getOwnerCinemaViewModel()
    private lateinit var binding: FragmentOwnerCinemaBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.homeCinemaBack.setOnClickListenerWithThrottle {
            view.findNavController().popBackStack()
        }

        binding.homeRoomEdit.setOnClickListenerWithThrottle {

        }

        binding.homeRoomAdd.setOnClickListenerWithThrottle {

        }

        binding.homeMovieAdd.setOnClickListenerWithThrottle {

        }

        val recyclerView = binding.homeRoomList
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        viewModel.cinema.observe(viewLifecycleOwner) {
            binding.homeCinemaName.text = it.name

//            recyclerView.adapter = CinemaRoomAdapter(it., viewModel)
//
//            binding.homeCinemaEmpty.visibility = if (it.isEmpty()) View.VISIBLE else View.GONE
        }

        viewModel.error.observe(viewLifecycleOwner) {
            Log.e("OwnerCinemaFragment", it)
            Toast.makeText(context, "Cinema not found, try again later", Toast.LENGTH_LONG).show()
        }

        val cinemaId = arguments?.getInt(CINEMA_ID)
        cinemaId?.let { viewModel.getCinemaById(it) }
    }

    companion object {
        const val CINEMA_ID = "cinemaId"
    }
}