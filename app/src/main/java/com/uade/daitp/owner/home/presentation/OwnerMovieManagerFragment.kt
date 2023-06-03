package com.uade.daitp.owner.home.presentation

import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.uade.daitp.R
import com.uade.daitp.databinding.FragmentOwnerCinemaFormBinding
import com.uade.daitp.databinding.FragmentOwnerMovieManagerBinding
import com.uade.daitp.module.di.ViewModelDI
import com.uade.daitp.owner.home.core.models.CreateCinemaIntent
import com.uade.daitp.owner.home.core.models.Movie
import com.uade.daitp.owner.home.core.models.MoviesList
import com.uade.daitp.owner.home.presentation.adapters.CinemaRoomAdapter
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

        viewModel.ownerMovies.observe(viewLifecycleOwner) {
            val recyclerView = binding.moviesOwnList
            recyclerView.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            recyclerView.adapter = MoviesAdapter(parseAllMovies(it))
        }

        viewModel.moviesList.observe(viewLifecycleOwner) {
            val recyclerView = binding.moviesAvailableList
            recyclerView.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            recyclerView.adapter = MoviesAdapter(parseAllMovies(it))
        }

        binding.moviesBack.setOnClickListenerWithThrottle {
            view.findNavController().popBackStack()
        }
    }

    private fun parseAllMovies(moviesList: MoviesList): List<Movie> {
        val movies = moviesList.showing
        movies.addAll(moviesList.comingSoon)
        return movies
    }

}
