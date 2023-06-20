package com.uade.daitp.owner.home.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.uade.daitp.R
import com.uade.daitp.databinding.FragmentOwnerMovieDetailBinding
import com.uade.daitp.module.di.ViewModelDI
import com.uade.daitp.owner.home.presentation.OwnerMovieManagerFragment.Companion.MOVIE_ID
import com.uade.daitp.presentation.util.setOnClickListenerWithThrottle
import java.util.*

class OwnerMovieDetailFragment : Fragment(R.layout.fragment_owner_movie_detail) {

    private val viewModel: OwnerMovieDetailViewModel = ViewModelDI.getOwnerMovieDetailViewModel()
    private lateinit var binding: FragmentOwnerMovieDetailBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentOwnerMovieDetailBinding.bind(view)

        val movieId = arguments?.getInt(MOVIE_ID)
        movieId?.let { id ->
            viewModel.getMovieBy(id)
        }

        viewModel.movie.observe(viewLifecycleOwner) {
            binding.movieName.text = it.title
            Glide.with(requireContext())
                .load(it.imageUrl)
                .into(binding.movieImage);
            binding.movieDescription.text = it.description
            binding.movieDuration.text = getDuration(it.duration)
            binding.movieYear.text = getYear(it.releaseDate)
            binding.movieCategory.text = it.genre
            binding.movieDirector.text = it.director
            binding.movieCast.text = it.cast
        }

        binding.movieBack.setOnClickListenerWithThrottle {
            view.findNavController().popBackStack()
        }
    }

    private fun getYear(releaseDate: Date): String {
        val date = Calendar.getInstance()
        date.time = releaseDate
        return date.get(Calendar.YEAR).toString()
    }

    private fun getDuration(duration: Int): String {
        return "${(duration / 60).toString().padStart(2, '0')}:${
            (duration % 60).toString().padStart(2, '0')
        }"
    }

}