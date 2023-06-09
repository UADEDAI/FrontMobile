package com.uade.daitp.owner.home.presentation

import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.uade.daitp.R
import com.uade.daitp.databinding.FragmentOwnerCinemaBinding
import com.uade.daitp.module.di.ViewModelDI
import com.uade.daitp.owner.home.core.models.emptyMovieList
import com.uade.daitp.owner.home.presentation.adapters.CinemaRoomAdapter
import com.uade.daitp.owner.home.presentation.adapters.MoviesAdapter
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
                .navigate(R.id.action_ownerCinemaFragment_to_ownerMovieManagerFragment, bundle)
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
                val bundle = Bundle()
                bundle.putInt(CINEMA_ID, cinemaId!!)
                bundle.putInt(CINEMA_ROOM_ID, viewModel.selectedCinemaRoom.value!!.id)
                view.findNavController()
                    .navigate(R.id.action_ownerCinemaFragment_to_ownerMovieManagerFragment, bundle)
            }
        }

        viewModel.cinema.observe(viewLifecycleOwner) {
            binding.homeCinemaName.text = it.name
        }

        viewModel.cinemaRooms.observe(viewLifecycleOwner) {
            recyclerView.adapter = CinemaRoomAdapter(it, viewModel)

            binding.homeCinemaEmpty.visibility = if (it.isEmpty()) VISIBLE else GONE
        }

        viewModel.error.observe(viewLifecycleOwner) {
            Toast.makeText(context, "Cinema not found, try again later", Toast.LENGTH_LONG).show()
        }

        viewModel.selectedCinemaRoom.observe(viewLifecycleOwner) {
            binding.homeRoomEdit.visibility = VISIBLE
            binding.homeMoviesTitle.visibility = VISIBLE
            binding.homeMovieAdd.visibility = VISIBLE

            viewModel.getRoomMoviesBy(it.id)
        }

        val moviesView = binding.homeMoviesList
        moviesView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        moviesView.adapter = MoviesAdapter(emptyMovieList(), false)

        viewModel.selectedRoomMovies.observe(viewLifecycleOwner) {
            if (it.showing.isEmpty()) {
                binding.homeMoviesList.visibility = GONE
                binding.homeCinemaEmpty.visibility = VISIBLE
                binding.homeCinemaEmptyText.setText(R.string.cinema_home_empty_movies)
                binding.homeCinemaEmptyButton.setText(R.string.cinema_home_empty_movies_button)
            } else {
                binding.homeCinemaEmpty.visibility = GONE

                getMovieListAdapter().updateData(it)

                getMovieListAdapter().selectedMovies.observe(viewLifecycleOwner) { selectedMovies ->
                    if (selectedMovies.isEmpty()) {
//                        binding.moviesInfo.visibility = View.GONE
//                        binding.moviesAdd.visibility = View.GONE
//                        binding.movieSwitch.visibility = View.VISIBLE
                    } else {
//                        binding.moviesInfo.visibility = View.VISIBLE
//                        binding.moviesAdd.visibility = View.VISIBLE
//                        binding.movieSwitch.visibility = View.GONE
                    }
                }

                binding.homeMoviesList.visibility = VISIBLE
            }
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

    private fun getMovieListAdapter(): MoviesAdapter {
        return (binding.homeMoviesList.adapter as MoviesAdapter)
    }

    companion object {
        const val CINEMA_ID = "cinemaId"
        const val CINEMA_ROOM_ID = "cinemaRoomId"
    }
}