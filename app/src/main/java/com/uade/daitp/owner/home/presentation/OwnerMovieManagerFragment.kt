package com.uade.daitp.owner.home.presentation

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.uade.daitp.R
import com.uade.daitp.databinding.FragmentOwnerMovieManagerBinding
import com.uade.daitp.module.di.ViewModelDI
import com.uade.daitp.owner.home.core.models.emptyMovieList
import com.uade.daitp.owner.home.presentation.adapters.MoviesAdapter
import com.uade.daitp.presentation.util.setOnClickListenerWithThrottle

class OwnerMovieManagerFragment : Fragment(R.layout.fragment_owner_movie_manager) {

    private val viewModel = ViewModelDI.getOwnerMoviesViewModel()
    private lateinit var binding: FragmentOwnerMovieManagerBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentOwnerMovieManagerBinding.bind(view)

        val roomId = arguments?.getInt(OwnerCinemaFragment.CINEMA_ROOM_ID)
        roomId?.let { id ->
            viewModel.getRoom(id)
            viewModel.getMoviesInRoom(id)
        }

        viewModel.error.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), "Invalid Data", Toast.LENGTH_LONG).show()
        }

        viewModel.cinemaRoom.observe(viewLifecycleOwner) {
            binding.moviesRoomName.text = it.name
        }

        val ownListView = binding.moviesOwnList
        ownListView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        ownListView.adapter =
            MoviesAdapter(emptyMovieList(), multipleSelectionEnabled = false, showAllMovies = false)

        viewModel.ownerMovies.observe(viewLifecycleOwner) {
            getOwnerListAdapter().updateData(it)

            getOwnerListAdapter().selectedMovies.observe(viewLifecycleOwner) { selectedMovies ->
                if (selectedMovies.isEmpty()) {
                    binding.moviesDeleteCancel.visibility = View.GONE
                    binding.moviesDeleteConfirm.visibility = View.GONE
                    binding.moviesDelete.visibility = View.GONE
                } else {
                    binding.moviesDeleteCancel.visibility = View.GONE
                    binding.moviesDeleteConfirm.visibility = View.GONE
                    binding.moviesDelete.visibility = View.VISIBLE
                }
            }
        }

        binding.moviesDeleteCancel.setOnClickListenerWithThrottle {
            binding.moviesDeleteCancel.visibility = View.GONE
            binding.moviesDeleteConfirm.visibility = View.GONE
            binding.moviesDelete.visibility = View.VISIBLE
        }

        binding.moviesDeleteConfirm.setOnClickListenerWithThrottle {
            viewModel.deleteMovie(getOwnerListAdapter().selectedMovies.value!![0])
        }

        binding.moviesDelete.setOnClickListenerWithThrottle {
            binding.moviesDeleteCancel.visibility = View.VISIBLE
            binding.moviesDeleteConfirm.visibility = View.VISIBLE
            binding.moviesDelete.visibility = View.GONE
        }

        val moviesView = binding.moviesAvailableList
        moviesView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        moviesView.adapter =
            MoviesAdapter(emptyMovieList(), multipleSelectionEnabled = false, showAllMovies = false)

        viewModel.moviesList.observe(viewLifecycleOwner) {
            getAvailableListAdapter().updateData(it)

            getAvailableListAdapter().selectedMovies.observe(viewLifecycleOwner) { selectedMovies ->
                if (selectedMovies.isEmpty()) {
                    binding.moviesInfo.visibility = View.GONE
                    binding.moviesAdd.visibility = View.GONE
                    binding.movieSwitch.visibility = View.VISIBLE
                } else {
                    binding.moviesInfo.visibility = View.VISIBLE
                    binding.moviesAdd.visibility = View.VISIBLE
                    binding.movieSwitch.visibility = View.GONE
                }
            }
        }

        binding.moviesInfo.setOnClickListenerWithThrottle {
            val selectedMovie = getAvailableListAdapter().selectedMovies.value!![0]

            val bundle = Bundle()
            bundle.putInt(MOVIE_ID, selectedMovie.id)
            view.findNavController()
                .navigate(R.id.action_ownerMovieManagerFragment_to_ownerMovieDetailFragment, bundle)
        }

        binding.moviesAdd.setOnClickListenerWithThrottle {
            viewModel.addMovie(getAvailableListAdapter().selectedMovies.value!![0])
            getAvailableListAdapter().resetSelection()
        }

        binding.movieSwitch.setOnClickListenerWithThrottle {
            getOwnerListAdapter().toggleMoviesType()
            getAvailableListAdapter().toggleMoviesType()
        }

        binding.moviesConfirmButton.setOnClickListenerWithThrottle {
            view.findNavController().popBackStack()
        }

        binding.moviesDiscardButton.setOnClickListenerWithThrottle {
            val adapter = binding.moviesAvailableList.adapter as MoviesAdapter
            adapter.resetSelection()
        }

        binding.moviesBack.setOnClickListenerWithThrottle {
            view.findNavController().popBackStack()
        }
    }

    private fun getOwnerListAdapter(): MoviesAdapter {
        return (binding.moviesOwnList.adapter as MoviesAdapter)
    }

    private fun getAvailableListAdapter(): MoviesAdapter {
        return (binding.moviesAvailableList.adapter as MoviesAdapter)
    }

    companion object {
        const val MOVIE_ID = "movieId"
    }

}
