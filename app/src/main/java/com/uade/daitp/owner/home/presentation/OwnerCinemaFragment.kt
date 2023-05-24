package com.uade.daitp.owner.home.presentation

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.uade.daitp.R
import com.uade.daitp.databinding.FragmentOwnerCinemaBinding
import com.uade.daitp.module.di.ViewModelDI
import com.uade.daitp.owner.home.presentation.adapters.CinemaRoomAdapter
import com.uade.daitp.presentation.util.setOnClickListenerWithThrottle

class OwnerCinemaFragment : Fragment(R.layout.fragment_owner_cinema) {

    private val viewModel: OwnerCinemaViewModel = ViewModelDI.getOwnerCinemaViewModel()
    private lateinit var binding: FragmentOwnerCinemaBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentOwnerCinemaBinding.bind(view)

        val cinemaId = arguments?.getInt(CINEMA_ID)
        cinemaId?.let { id ->
            viewModel.getCinemaBy(id)
            viewModel.getCinemaRoomsById(id)
        }

        val recyclerView = binding.homeRoomList
        recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        binding.homeCinemaBack.setOnClickListenerWithThrottle {
            view.findNavController().popBackStack()
        }

        binding.homeRoomEdit.setOnClickListenerWithThrottle {
            val bundle = Bundle()
            bundle.putInt(CINEMA_ID, cinemaId!!)
            bundle.putInt(CINEMA_ROOM_ID, viewModel.selectedCinemaRoom.value!!.id)
            view.findNavController()
                .navigate(R.id.action_ownerCinemaFragment_to_ownerCinemaRoomFormFragment, bundle)
        }

        binding.homeRoomAdd.setOnClickListenerWithThrottle {
            val bundle = Bundle()
            bundle.putInt(CINEMA_ID, cinemaId!!)
            view.findNavController()
                .navigate(R.id.action_ownerCinemaFragment_to_ownerCinemaRoomFormFragment, bundle)
        }

        binding.homeMovieAdd.setOnClickListenerWithThrottle {
            val bundle = Bundle()
            bundle.putInt(CINEMA_ID, cinemaId!!)
            bundle.putInt(CINEMA_ROOM_ID, viewModel.selectedCinemaRoom.value!!.id)
            view.findNavController()
                .navigate(R.id.action_ownerCinemaFragment_to_ownerCinemaRoomFormFragment, bundle)
        }

        binding.homeCinemaEmptyButton.setOnClickListenerWithThrottle {
            if (!viewModel.selectedCinemaRoom.isInitialized) {
                val bundle = Bundle()
                bundle.putInt(CINEMA_ID, cinemaId!!)
                view.findNavController()
                    .navigate(
                        R.id.action_ownerCinemaFragment_to_ownerCinemaRoomFormFragment,
                        bundle
                    )
            } else {

            }
        }

        viewModel.cinema.observe(viewLifecycleOwner) {
            binding.homeCinemaName.text = it.name
        }

        viewModel.cinemaRooms.observe(viewLifecycleOwner) {
            recyclerView.adapter = CinemaRoomAdapter(it, viewModel)

            binding.homeCinemaEmpty.visibility = if (it.isEmpty()) View.VISIBLE else View.GONE
        }

        viewModel.error.observe(viewLifecycleOwner) {
            Toast.makeText(context, "Cinema not found, try again later", Toast.LENGTH_LONG).show()
        }

        viewModel.selectedCinemaRoom.observe(viewLifecycleOwner) {
            binding.homeRoomEdit.visibility = View.VISIBLE
            binding.homeMoviesTitle.visibility = View.VISIBLE
            binding.homeMovieAdd.visibility = View.VISIBLE

            viewModel.getRoomMoviesBy(it.id)
        }
    }

    override fun onResume() {
        super.onResume()

        val cinemaId = arguments?.getInt(CINEMA_ID)
        cinemaId?.let { id ->
            viewModel.getCinemaBy(id)
            viewModel.getCinemaRoomsById(id)
        }
    }

    companion object {
        const val CINEMA_ID = "cinemaId"
        const val CINEMA_ROOM_ID = "cinemaRoomId"
    }
}